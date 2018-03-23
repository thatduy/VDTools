package vdt.com.autoairplanemode.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import vdt.com.autoairplanemode.IncomingCallReceiver;
import vdt.com.autoairplanemode.MainActivity;

/**
 * Created by ASUS on 22-Oct-17.
 */

public class ScreenshotService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1s = 1000ms
                try {
                    takeScreenshot();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

        return super.onStartCommand(intent, flags, startId);

    }

    private void takeScreenshot() throws IOException {
        Process sh = Runtime.getRuntime().exec("su", null, null);

        OutputStream os = sh.getOutputStream();
        os.write(("/system/bin/screencap -p " + "/sdcard/img.png").getBytes("ASCII"));
        os.flush();

        os.close();
        try {
            sh.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bitmap screen = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() +
                File.separator + "img.png");

//my code for saving
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        screen.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

//you can create a new file name "test.jpg" in sdcard folder.

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "test.jpg");
        f.createNewFile();
//write the bytes in file
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
// remember close de FileOutput
        Toast.makeText(this, "captured", Toast.LENGTH_LONG).show();
        fo.close();
        //stop service
        ComponentName component=new ComponentName(this, ScreenshotService.class);
        getPackageManager()
                .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
    }
}
