package vdt.com.autoairplanemode.Receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.widget.Toast;

import vdt.com.autoairplanemode.R;

/**
 * Created by ASUS on 21-Oct-17.
 */

public class AdminReceiver extends DeviceAdminReceiver {
    void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "onEnabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "onDisableRequested admin";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "onDisabled admin");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "onPasswordChanged admin");
    }

}
