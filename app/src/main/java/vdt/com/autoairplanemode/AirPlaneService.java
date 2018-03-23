package vdt.com.autoairplanemode;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Logger;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 07-Oct-17.
 */

public class AirPlaneService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOGLOG", "onStartCommand");
       // setAirPlaneMode(true, this);
        setSilentMode();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LOGLOG", "onCreate");
    }

    public void setSilentMode(){
        Log.d("LOGLOG", "setSilentMode");
        AudioManager am;
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    public  void setAirPlaneMode(boolean airplaneMode, Context mContext) {
        if (android.os.Build.VERSION.SDK_INT < 17) {
            try {
                Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                intent.putExtra("state", true);
                sendBroadcast(intent);
            } catch (ActivityNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            try {
                Intent intent = new Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                intent.putExtra("state", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sendBroadcast(intent);
            } catch (ActivityNotFoundException e) {
                try {
                    Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException ignored) {

                }
            }
        }

    }
    }


