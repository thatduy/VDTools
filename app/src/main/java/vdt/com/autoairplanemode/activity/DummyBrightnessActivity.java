package vdt.com.autoairplanemode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import vdt.com.autoairplanemode.R;

import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

/**
 * Created by ASUS on 23-Oct-17.
 */

public class DummyBrightnessActivity extends Activity {
    private static final int DELAYED_MESSAGE = 1;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        // Let touches go through to apps/activities underneath.
        window.addFlags(FLAG_NOT_TOUCHABLE);

        // Now set up content view
        setContentView(R.layout.dummy_brightness);
    }
}
