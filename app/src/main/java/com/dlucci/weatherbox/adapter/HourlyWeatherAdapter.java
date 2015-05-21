package com.dlucci.weatherbox.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.model.Hourly;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

/**
 * Created by derlucci on 5/6/15.
 */
public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {


    private ArrayList<Hourly> hourlyArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.weatherImage)
        public ImageView icon;

        @InjectViews({R.id.hour, R.id.temperature, R.id.dewPoint, R.id.feelsLike, R.id.weatherDescription, R.id.windChill, R.id.windSpeed})
        public List<TextView> textViewList;

        public Context context;

        @InjectView(R.id.hourlyCardView)
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);

            context = view.getContext();

            cardView.setUseCompatPadding(true);
        }
    }

    public HourlyWeatherAdapter(ArrayList<Hourly> hourlyArrayList){ this.hourlyArrayList = hourlyArrayList;   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_row, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(holder.context);
        String suffix = "°" + (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? "F" : "C");

        String[] dataArr = new String[8];

        dataArr[0] = hourlyArrayList.get(position).weatherIconUrl.get(0).value;
        dataArr[1] = hourlyArrayList.get(position).time;
        dataArr[5] = hourlyArrayList.get(position).weatherDesc.get(0).value;
        if (suffix.contains("C")) {
            dataArr[2] = hourlyArrayList.get(position).tempC;
            dataArr[3] = hourlyArrayList.get(position).dewPointC;
            dataArr[4] = hourlyArrayList.get(position).feelsLikeC;
            dataArr[6] = hourlyArrayList.get(position).windChillC;
            dataArr[7] = hourlyArrayList.get(position).windSpeedKmph;
        } else {
            dataArr[2] = hourlyArrayList.get(position).tempF;
            dataArr[3] = hourlyArrayList.get(position).dewPointF;
            dataArr[4] = hourlyArrayList.get(position).feelsLikeF;
            dataArr[6] = hourlyArrayList.get(position).windChillF;
            dataArr[7] = hourlyArrayList.get(position).windSpeedMiles;
        }

        Resources r = holder.context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        Picasso.with(holder.context).load(dataArr[0]).resize(px, px).into(holder.icon);

        if (dataArr[1] != null) {
            String postifx = "AM";
            int date = (new Integer(dataArr[1])) / 100;
            if (sharedPrefs.getString("timeSetting", "24").equals("12")) {
                if (date >= 12) {
                    date = date - 12;
                    postifx = "PM";
                }
                dataArr[1] = date + ":00" + postifx;
            } else {
                dataArr[1] = date + ":00";
            }
        }
        holder.textViewList.get(0).setText(dataArr[1]);
        holder.textViewList.get(1).setText("Temperature:  " + dataArr[2] + suffix);
        holder.textViewList.get(2).setText("Dew Point:  " + dataArr[3] + suffix);
        holder.textViewList.get(3).setText("Feels Like " + dataArr[4] + suffix);
        holder.textViewList.get(4).setText(dataArr[5]);
        holder.textViewList.get(5).setText("Wind Chill:  " + dataArr[6] + suffix);
        holder.textViewList.get(6).setText("Wind Speed:  " + dataArr[7]);
    }

    @Override
    public int getItemCount() {
        return hourlyArrayList.size();
    }
}
