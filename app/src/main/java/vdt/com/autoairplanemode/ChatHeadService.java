package vdt.com.autoairplanemode;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Method;

import de.hdodenhof.circleimageview.CircleImageView;
import vdt.com.autoairplanemode.Receiver.EndCallReceiver;

import static android.content.ContentValues.TAG;

public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private CircleImageView chatHead;
    private GestureDetector gestureDetector;
    NotificationManager manager;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            if ("ACTION_STOP_SERVICE".equals(intent.getAction())) {
                Log.d(TAG, "called to cancel service");
                showToast("Thanks, love you too <3 bye bye");
                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(1122);
                stopSelf();

            } else {
                if("ACTION_STOP_CALL".equals(intent.getAction())){
                    cancelAlarm(12345435);
                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(1122334455);
                } else{
                    makeNoti();
                }

            }
        } else {
            makeNoti();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void cancelAlarm(int requestCode){
        AlarmManager mAlarmManger = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, EndCallReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mAlarmManger.cancel(pendingIntent);
        showToast("Stopped auto end call");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new CircleImageView(this);
        chatHead.setBorderWidth(2);
        chatHead.setBorderColor(ContextCompat.getColor(this, R.color.white));
        chatHead.setImageResource(R.drawable.vdt);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = 0;
        params.y = 0;
        windowManager.addView(chatHead, params);
        //showMenu(chatHead);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    //openRecentApps();
                    Intent intent = new Intent(ChatHeadService.this, MyDialog.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    return true;
                } else {
                    // your code for move and drag
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(chatHead, params);
                            return true;
                    }
                }

                return false;
            }
        });


    }


    public void makeNoti(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Thật Duy nè");
        builder.setContentText("Press if you love me.....");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.drawable.vdt));
        builder.setOngoing(true);
        Intent stopSelf = new Intent(this, ChatHeadService.class);
        stopSelf.setAction("ACTION_STOP_SERVICE");
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pStopSelf);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1122, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }

    public void showMenu(View v ) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.actions);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_save:
                        showToast("hi");
                        return true;
                    case R.id.group_delete:
                        showToast("hello");
                        return true;
                    default:
                        return false;
                }
            }
        });
        //popup.inflate(R.menu.actions);
        popup.show();
    }
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}

class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}