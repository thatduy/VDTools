package vdt.com.autoairplanemode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by ASUS on 17-Oct-17.
 */

public class ReceiverNormalMode extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager am;
        am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
}
