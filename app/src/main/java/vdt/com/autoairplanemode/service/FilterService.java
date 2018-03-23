package vdt.com.autoairplanemode.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import vdt.com.autoairplanemode.MainActivity;
import vdt.com.autoairplanemode.R;
import vdt.com.autoairplanemode.util.Cfg;
import vdt.com.autoairplanemode.util.Grids;
import vdt.com.autoairplanemode.util.Ntf;

/**
 * Created by ASUS on 02-Nov-17.
 */

public class FilterService extends Service {
    public static final String LOG = "Pixel Filter"; //NON-NLS
    WindowManager wm;
    SurfaceView mView;
    SurfaceView mView_bg;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startFilter();
    }

    public void startFilter() {
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mView = new SurfaceView(this);
        mView_bg = new SurfaceView(this);
        mView_bg.setBackgroundColor(this.getResources().getColor(R.color.color_overlay));
        final DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        // making service view fullscreen
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        //params2.flags =  WindowManager.LayoutParams.FLAG_FULLSCREEN ;
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;

        wm.addView(mView_bg, params);
        wm.addView(mView, params);
    }



    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mView_bg != null){
            wm.removeView(mView_bg);
            wm.removeView(mView);
        }
    }


}