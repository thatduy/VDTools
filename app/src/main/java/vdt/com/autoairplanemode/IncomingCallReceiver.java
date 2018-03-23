package vdt.com.autoairplanemode;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import vdt.com.autoairplanemode.Receiver.EndCallReceiver;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ASUS on 16-Oct-17.
 */

public class IncomingCallReceiver extends BroadcastReceiver {
    public static String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static String ACTION_PHONE_STATE = "android.intent.action.PHONE_STATE";
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("TAG", "onReceive");
        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        // if phone is idle after ringing
        if (intent.getAction().equals(ACTION_PHONE_STATE)){
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(1122334455);
                Toast.makeText(context, "Ended call", Toast.LENGTH_LONG).show();
            }
        }
        if (intent.getAction().equals(ACTION_OUTGOING_CALL)){
            String prefname="my_data";
            SharedPreferences pre=context.getSharedPreferences
                    (prefname,MODE_PRIVATE);

            //off before 10s
            if(pre.getBoolean("checkedCall", false) && !intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER).contains("*")){
                Toast.makeText(context, "Call will be ended in "+ pre.getInt("minutes", 8) + " minutes, expand notification to disable", Toast.LENGTH_LONG).show();
                setNormalMode(context, pre.getInt("minutes", 8)*60*1000);
                makeNoti(context);
            }
        }



    }
    public void makeNoti(Context context){
        NotificationManager manager;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Auto end call is working...");
        builder.setContentText("Tap here to disable...");
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.vdt));
        builder.setOngoing(true);
        Intent stopSelf = new Intent(context, ChatHeadService.class);
        stopSelf.setAction("ACTION_STOP_CALL");
        PendingIntent pStopSelf = PendingIntent.getService(context, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pStopSelf);
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1122334455, builder.build());
    }
    void setNormalMode(Context context,long time){
        AlarmManager mAlarmManger = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(context, EndCallReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 12345435, intent, 0);
        //set that timer as a RTC Wakeup to alarm manager object
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManger.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
        }

    }
}
