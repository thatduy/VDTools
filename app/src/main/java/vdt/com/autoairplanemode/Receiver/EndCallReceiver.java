package vdt.com.autoairplanemode.Receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ASUS on 23-Oct-17.
 */

public class EndCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Class c = null;
        try {
            c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
            c = Class.forName(telephonyService.getClass().getName()); // Get its class
            m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
            m.setAccessible(true); // Make it accessible
            m.invoke(telephonyService); // invoke endCall()
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(1122334455);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
