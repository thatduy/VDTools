package vdt.com.autoairplanemode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vdt.com.autoairplanemode.R;

/**
 * Created by ASUS on 13-Jan-18.
 */

public class ForecastHolder extends RecyclerView.ViewHolder {
    public TextView txtTemp, txtDate, txtDesc;
    public ImageView imgCond;
    public ForecastHolder(View itemView) {
        super(itemView);
        txtTemp = itemView.findViewById(R.id.txtTemp);
        txtDate = itemView.findViewById(R.id.txtDate);
        txtDesc = itemView.findViewById(R.id.txtDesc);
        imgCond = itemView.findViewById(R.id.imgCond);
    }
}
