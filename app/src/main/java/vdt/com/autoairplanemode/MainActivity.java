package vdt.com.autoairplanemode;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.Calendar;

import vdt.com.autoairplanemode.adapter.AdapterForecast;
import vdt.com.autoairplanemode.util.GPSTracker;
import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

public class MainActivity extends AppCompatActivity {

    Switch swActive, swActiveSilent;
    EditText edtEndTime;
    String prefname="my_data";
  //  Button btnClick;
    EditText edtDate;
    TextView txtCurrentTemp, txtCurrentLocation, txtForecastTemp, txtDescription;
    ImageView imgCondition, img_update;
    LinearLayout groupWeather;
    EditText edtQuery;
    Button btnQuery;
    RecyclerView recycle_forecast;
    String newInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        showWeather();
        addEvents();

    }

    private void addEvents() {
        swActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                enableEndUpCall(b);

            }
        });
        swActiveSilent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setSilentMode(113, 23, 30);
                    setSilentMode(114, 7, 30);
                    setNormalMode(115, 6, 30);
                    setNormalMode(116, 10, 0);
                } else {
                    AudioManager am;
                    am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    //For Normal mode
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    cancelAlarm(113);
                    cancelAlarm(114);
                    cancelAlarm(115);
                    cancelAlarm(116);

                }
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtQuery.getText().toString().isEmpty()){
                    queryWeatherByName(edtQuery.getText().toString());
                }

            }
        });
        img_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtQuery.getText().toString().isEmpty()){
                    showWeather();
                } else {
                    queryWeatherByName(edtQuery.getText().toString());
                }
            }
        });
    }


    private void showWeather() {
        YahooWeather yahooWeather = new YahooWeather();
        yahooWeather.queryYahooWeatherByGPS(this, new YahooWeatherInfoListener() {
            @Override
            public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
                if (weatherInfo != null){
                    groupWeather.setVisibility(View.VISIBLE);
                    txtCurrentLocation.setText(weatherInfo.getAddress().getSubLocality() +", "+ weatherInfo.getAddress().getSubAdminArea());//subLocality + subAdmin
                    txtCurrentTemp.setText(weatherInfo.getCurrentTemp() + " °C");
                    txtDescription.setText(weatherInfo.getCurrentText());//text
                    // imgCondition.setImageBitmap(weatherInfo.getCurrentConditionIcon());
                    Picasso.with(MainActivity.this).load(weatherInfo.getCurrentConditionIconURL()).into(imgCondition);
                    AdapterForecast forecast = new AdapterForecast(weatherInfo, MainActivity.this);
                    recycle_forecast.setAdapter(forecast);
                }

            }
        });
    }

    private void queryWeatherByName(final String s) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(edtQuery.getWindowToken(), 0);

        edtQuery.clearFocus();
        YahooWeather yahooWeather = new YahooWeather();
        yahooWeather.queryYahooWeatherByPlaceName(this, s, new YahooWeatherInfoListener() {
            @Override
            public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
                if (weatherInfo != null){
                    groupWeather.setVisibility(View.VISIBLE);
                    txtCurrentLocation.setText( s);
                    txtCurrentTemp.setText(weatherInfo.getCurrentTemp() + " °C");
                    txtDescription.setText(weatherInfo.getCurrentText());//text
                    Picasso.with(MainActivity.this).load(weatherInfo.getCurrentConditionIconURL()).into(imgCondition);
                    AdapterForecast forecast = new AdapterForecast(weatherInfo, MainActivity.this);
                    recycle_forecast.setAdapter(forecast);
                }
            }
        });

    }

    private void addControls() {
        swActive =  findViewById(R.id.swActive);
        swActiveSilent =  findViewById(R.id.swActiveSilent);
        edtEndTime =  findViewById(R.id.edtEndTime);
        txtCurrentTemp =  findViewById(R.id.currentTemp);
        txtCurrentLocation =  findViewById(R.id.txtCurrentLocation);
        txtForecastTemp =  findViewById(R.id.txtForecastTemp);
        txtDescription =  findViewById(R.id.txtDescription);
        imgCondition = findViewById(R.id.imgCondition);
        groupWeather = findViewById(R.id.groupWeather);
        edtQuery = findViewById(R.id.edtQuery);
        btnQuery = findViewById(R.id.btnQuery);
        recycle_forecast  = findViewById(R.id.recycle_forecast);
        img_update = findViewById(R.id.imgUpdate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Control orientation of the items
        // also supports LinearLayoutManager.HORIZONTAL
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        recycle_forecast.setLayoutManager(layoutManager);


        Typeface face = Typeface.createFromAsset(getAssets(),
                "font/roboto_re.ttf");
        txtCurrentTemp.setTypeface(face);
        txtCurrentLocation.setTypeface(face);
        txtForecastTemp.setTypeface(face);
        txtDescription.setTypeface(face);
    }

    void cancelAlarm(int requestCode){
        AlarmManager mAlarmManger = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, ReceiverVibrate.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mAlarmManger.cancel(pendingIntent);
    }

    void enableEndUpCall(boolean b){
        int flag=(b ?
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
        ComponentName component=new ComponentName(MainActivity.this, IncomingCallReceiver.class);

        getPackageManager()
                .setComponentEnabledSetting(component, flag,
                        PackageManager.DONT_KILL_APP);
        edtEndTime.setEnabled(b);
    }

    void setSilentMode(int requestCode, int hour, int minute){
        AlarmManager mAlarmManger = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(MainActivity.this, ReceiverVibrate.class);
        if(requestCode == 113){
            intent.putExtra("night", true);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, 0);

        //set timer you want alarm to work (here I have set it to 9.00)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        //set that timer as a RTC Wakeup to alarm manager object
        mAlarmManger.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    void setNormalMode(int requestCode, int hour, int minute){
        AlarmManager mAlarmManger = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //Create pending intent & register it to your alarm notifier class
        Intent intent = new Intent(MainActivity.this, ReceiverNormalMode.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode, intent, 0);

        //set timer you want alarm to work (here I have set it to 9.00)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        //set that timer as a RTC Wakeup to alarm manager object
        mAlarmManger.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    public void showMenu(View v ) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.actions);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_save:
                        showToast("hi");
                        return true;
                    case R.id.group_delete:
                        showToast("hello");
                        return true;
                    default:
                        return false;
                }
            }
        });
        //popup.inflate(R.menu.actions);
        popup.show();
    }
    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    void displayCircle(){
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, 1234);
            } else {
                Intent intent = new Intent(this, ChatHeadService.class);
                intent.setAction("START");
                startService(intent);
            }
        } else {
            Intent intent = new Intent(this, ChatHeadService.class);
            intent.setAction("START");
            startService(intent);
        }
    }

    public void savingPreferences()
    {
        //tạo đối tượng getSharedPreferences
        SharedPreferences pre=getSharedPreferences
                (prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        boolean bchk = swActive.isChecked();
            //lưu vào editor
        if(edtEndTime.isEnabled() ){
            if(!edtEndTime.getText().toString().isEmpty()){
                editor.putInt("minutes", Integer.parseInt(edtEndTime.getText().toString()));
            }

        }
        editor.putBoolean("checkedCall", bchk);
        editor.putBoolean("checkedSilent", swActiveSilent.isChecked());
        //chấp nhận lưu xuống file
        editor.apply();
    }
    /**
     * hàm đọc trạng thái đã lưu trước đó
     */
    public void restoringPreferences()
    {
        SharedPreferences pre=getSharedPreferences
                (prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk = pre.getBoolean("checkedCall", false);
        swActive.setChecked(bchk);
        swActiveSilent.setChecked(pre.getBoolean("checkedSilent", false));
        edtEndTime.setText(String.valueOf(pre.getInt("minutes", 10)));
        if(!bchk){
            edtEndTime.setEnabled(false);
        }
        enableEndUpCall(bchk);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
        displayCircle();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
