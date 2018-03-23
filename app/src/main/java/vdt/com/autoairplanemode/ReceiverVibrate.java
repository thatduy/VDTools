package vdt.com.autoairplanemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class ReceiverVibrate extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        Toast.makeText(context, "Switched RINGER_MODE_VIBRATE " + day, Toast.LENGTH_LONG).show();
        boolean night = intent.getBooleanExtra("night", false);
        if(night){
            AudioManager am;
            am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            if (day < 7 && day > 1){
                AudioManager am;
                am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        }

    }
}
