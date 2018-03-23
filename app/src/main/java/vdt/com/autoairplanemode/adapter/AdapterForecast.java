package vdt.com.autoairplanemode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vdt.com.autoairplanemode.R;
import zh.wang.android.yweathergetter4a.WeatherInfo;

/**
 * Created by ASUS on 13-Jan-18.
 */

public class AdapterForecast extends RecyclerView.Adapter<ForecastHolder>{
    WeatherInfo weatherInfo;
    Context context;
    public AdapterForecast(WeatherInfo weatherInfo, Context context) {
        this.weatherInfo = weatherInfo;
        this.context  = context;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ForecastHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        WeatherInfo.ForecastInfo forecastInfo = weatherInfo.getForecastInfoList().get(position);
        holder.txtTemp.setText(forecastInfo.getForecastTempLow() + " °C - " + forecastInfo.getForecastTempHigh() + " °C");
        holder.txtDate.setText(forecastInfo.getForecastDay() + ", " + forecastInfo.getForecastDate());
        holder.txtDesc.setText(forecastInfo.getForecastText());
        Picasso.with(context).load(forecastInfo.getForecastConditionIconURL()).into(holder.imgCond);
    }

    @Override
    public int getItemCount() {
        return weatherInfo.getForecastInfoList().size();
    }
}
