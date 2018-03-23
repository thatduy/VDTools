package vdt.com.autoairplanemode;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import vdt.com.autoairplanemode.Receiver.AdminReceiver;
import vdt.com.autoairplanemode.util.OnSwipeTouchListener;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 20-Oct-17.
 */

public class MyDialog extends Activity {
    @BindView(R.id.ic_apple)
    CircleImageView icApple;
    @BindView(R.id.ic_low_volume)
    CircleImageView icLowVolume;
    @BindView(R.id.ic_high_volume)
    CircleImageView icHighVolume;
    @BindView(R.id.ic_camera)
    CircleImageView icCamera;
    @BindView(R.id.ic_window)
    CircleImageView icWindow;
    @BindView(R.id.ic_lock)
    CircleImageView icLock;
    @BindView(R.id.ic_bell)
    CircleImageView icBell;
    @BindView(R.id.ic_setting)
    CircleImageView icSetting;
    @BindView(R.id.ic_wifi)
    CircleImageView icWifi;
    public static Toast toast = null;
    @BindView(R.id.panel)
    LinearLayout panel;
    @BindView(R.id.panel1)
    LinearLayout panel1;
    @BindView(R.id.panel2)
    LinearLayout panel2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_dialog);
        ButterKnife.bind(this);
        icLowVolume.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showToast2("mute");
                MuteAudio();
                return false;
            }
        });
        icHighVolume.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showToast2("unmute");
                UnMuteAudio();
                return false;
            }
        });
        // showDialog();
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        icWifi.setImageResource(wifiManager.isWifiEnabled() ? R.drawable.ic_wifi : R.drawable.ic_wifi_off);
        icBell.setImageResource(am.getRingerMode() != 1 ? R.drawable.ic_bell : R.drawable.ic_vibrate);

        panel1.setOnTouchListener(new OnSwipeTouchListener(MyDialog.this) {
            public void onSwipeTop() {
                changeToPanel2(panel1, panel2);
            }

            public void onSwipeRight() {
                changeToPanel2(panel1, panel2);
            }

            public void onSwipeLeft() {
                changeToPanel2(panel1, panel2);
            }

            public void onSwipeBottom() {
                changeToPanel2(panel1, panel2);
            }

        });
        panel2.setOnTouchListener(new OnSwipeTouchListener(MyDialog.this) {
            public void onSwipeTop() {
                changeToPanel2(panel2, panel1);
            }

            public void onSwipeRight() {
                changeToPanel2(panel2, panel1);
            }

            public void onSwipeLeft() {
                changeToPanel2(panel2, panel1);
            }

            public void onSwipeBottom() {
                changeToPanel2(panel2, panel1);
            }

        });
    }
    public void trimCache(Context context) {
        PackageManager  pm = getPackageManager();
        // Get all methods on the PackageManager
        Method[] methods = pm.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().equals("freeStorage")) {
                // Found the method I want to use
                try {
                    long desiredFreeStorage = 8 * 1024 * 1024 * 1024; // Request for 8GB of free space
                    m.invoke(pm, desiredFreeStorage , null);
                } catch (Exception e) {
                    // Method invocation failed. Could be a permission problem
                }
                break;
            }
        }

    }
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){

                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    //Log.i("TAG_CACHE", "File /data/data/APP_PACKAGE/" + s +" DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                Log.i("TAG_CACHE", "File /data/data/APP_PACKAGE/" + children[i] +" DELETED");
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
    private void changeToPanel2(View from, View to){
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(500);
        from.startAnimation(animation1);
        from.setVisibility(View.GONE);
        to.setAlpha(0.0f);
        to.setVisibility(View.VISIBLE);
        AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
        animation2.setDuration(500);
        to.startAnimation(animation2);
        to.setAlpha(1.0f);

    }
    private void openRecentApps() {
        Toast.makeText(this, "Hello I'm VoDuyThat", Toast.LENGTH_LONG).show();
        try {
            Class serviceManagerClass = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClass.getMethod("getService", String.class);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerClass, "statusbar");
            Class statusBarClass = Class.forName(retbinder.getInterfaceDescriptor());
            Object statusBarObject = statusBarClass.getClasses()[0].getMethod("asInterface", IBinder.class).invoke(null, new Object[]{retbinder});
            Method clearAll = statusBarClass.getMethod("toggleRecentApps");
            clearAll.setAccessible(true);
            clearAll.invoke(statusBarObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MuteAudio() {
        AudioManager mAlramMAnager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void UnMuteAudio() {
        AudioManager mAlramMAnager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }

    public void showToast2(String msg) {
        if (toast != null)             //this will cancel the toast on the screen if one exists
            toast.cancel();
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void showToastShort(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ic_apple, R.id.ic_low_volume, R.id.ic_high_volume,
            R.id.ic_camera, R.id.ic_window, R.id.ic_lock, R.id.ic_bell, R.id.ic_setting, R.id.ic_wifi})
    public void onViewClicked(View view) {
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch (view.getId()) {
            case R.id.ic_apple:
                clearCache();
                finish();
                break;
            case R.id.ic_low_volume:
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                int volume_level = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                showToast2(volume_level + "");
                break;
            case R.id.ic_high_volume:
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                int volume_level2 = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                showToast2(volume_level2 + "");
                break;
            case R.id.ic_camera:
                openCamera();
                finish();
                break;
            case R.id.ic_window:
                openRecentApps();
                finish();
                break;
            case R.id.ic_lock:
                DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                ComponentName mAdminReceiver = new ComponentName(this, AdminReceiver.class);
                if (mDPM.isAdminActive(mAdminReceiver)) {
                    mDPM.lockNow();
                } else {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                            mAdminReceiver);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "You need to activate VDT Administrator because he is handsome =)))!");
                    startActivityForResult(intent, 112233);
                }
                finish();
                break;
            case R.id.ic_bell:
                AudioManager am;
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                if (am.getRingerMode() == 1) {
                    icBell.setImageResource(R.drawable.ic_bell);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } else {
                    icBell.setImageResource(R.drawable.ic_vibrate);
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
                break;
            case R.id.ic_setting:
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                finish();
                break;
            case R.id.ic_wifi:
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    icWifi.setImageResource(R.drawable.ic_wifi_off);
                    wifiManager.setWifiEnabled(false);
                } else {
                    icWifi.setImageResource(R.drawable.ic_wifi);
                    wifiManager.setWifiEnabled(true);
                }
                break;
        }

    }


    private void stopBrightness() {
        try {
            //sets manual mode and brightnes 255
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 10);  //this will set the brightness to maximum (255)

            //refreshes the screen
            int br = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = (float) br / 255;
            getWindow().setAttributes(lp);

        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112233) {
            showToast("Here you go! Let's lock screen");
        }
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            startActivity(intent);
        } catch (Exception e) {
            Log.i(TAG, "Unable to launch camera: " + e);
        }
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    void clearCache()
    {
        CachePackageDataObserver mClearCacheObserver = new CachePackageDataObserver();

        PackageManager mPM=getPackageManager();

        @SuppressWarnings("rawtypes")
        final Class[] classes= { Long.TYPE, IPackageDataObserver.class };

        Long localLong=Long.valueOf(Long.MAX_VALUE);

        try
        {
            Method localMethod=
                    mPM.getClass().getMethod("freeStorageAndNotify", classes);

      /*
       * Start of inner try-catch block
       */
            try
            {
                localMethod.invoke(mPM, localLong, mClearCacheObserver);
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
      /*
       * End of inner try-catch block
       */
        }
        catch (NoSuchMethodException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }//End of clearCache() method

    private class CachePackageDataObserver implements IPackageDataObserver {
        public void onRemoveCompleted(String packageName, boolean succeeded)
        {

        }//End of onRemoveCompleted() method
    }//End of CachePackageDataObserver instance inner class
    private interface IPackageDataObserver {
        void onRemoveCompleted( String packageName, boolean succeeded);
    }
}
