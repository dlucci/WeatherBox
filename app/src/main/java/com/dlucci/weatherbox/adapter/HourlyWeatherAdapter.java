package com.dlucci.weatherbox.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by derlucci on 5/6/15.
 */
public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {


   private ArrayList<Hourly> hourlyArrayList;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView hour;
        public TextView temperature;
        public TextView dewPoint;
        public TextView feelsLike;
        public TextView weatherDescription;
        public TextView windChill;
        public TextView windSpeed;

        public Context context;

        public CardView cardView;

        public ViewHolder(View view) {
            super(view);

            icon = (ImageView) view.findViewById(R.id.weatherImage);
            hour = (TextView) view.findViewById(R.id.hour);
            temperature = (TextView) view.findViewById(R.id.temperature);
            dewPoint = (TextView) view.findViewById(R.id.dewPoint);
            feelsLike = (TextView) view.findViewById(R.id.feelsLike);
            weatherDescription = (TextView) view.findViewById(R.id.weatherDescription);
            windChill = (TextView) view.findViewById(R.id.windChill);
            windSpeed = (TextView) view.findViewById(R.id.windSpeed);

            context = view.getContext();

            cardView = (CardView) view.findViewById(R.id.hourlyCardView);
            cardView.setUseCompatPadding(true);
        }
    }

    public HourlyWeatherAdapter(ArrayList<Hourly> hourlyArrayList){
        this.hourlyArrayList = hourlyArrayList;
    }

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
        if(suffix.contains("C")){
            dataArr[2] = hourlyArrayList.get(position).tempC;
            dataArr[3] = hourlyArrayList.get(position).DewPointC;
            dataArr[4] = hourlyArrayList.get(position).FeelsLikeC;
            dataArr[6] = hourlyArrayList.get(position).WindChillC;
            dataArr[7] = hourlyArrayList.get(position).windspeedKmph;
        } else {
            dataArr[2] = hourlyArrayList.get(position).tempF;
            dataArr[3] = hourlyArrayList.get(position).DewPointF;
            dataArr[4] = hourlyArrayList.get(position).FeelsLikeF;
            dataArr[6] = hourlyArrayList.get(position).WindChillF;
            dataArr[7] = hourlyArrayList.get(position).windspeedMiles;
        }

        Resources r = holder.context.getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        Picasso.with(holder.context).load(dataArr[0]).resize(px, px).into(holder.icon);

        if(dataArr[1] != null){

            if(dataArr[1].length() == 3){
                char[] chars = dataArr[1].toCharArray();
                dataArr[1] = chars[0] + ":" + chars[1] + chars[2];
            } else if(dataArr[1].length() == 4) {
                int value = new Integer(Integer.valueOf(dataArr[1]));
                value = value/100;
                dataArr[1] = new String(new Integer(value).toString()) + ":00";
            }

            holder.hour.setText(dataArr[1]);
        }

        holder.temperature.setText("Temperature:  " + dataArr[2] + suffix);
        holder.dewPoint.setText("Dew Point:  " + dataArr[3] + suffix);
        holder.feelsLike.setText("Feels Like " + dataArr[4] + suffix);
        holder.weatherDescription.setText(dataArr[5]);
        holder.windChill.setText("Wind Chill:  " + dataArr[6] + suffix);
        holder.windSpeed.setText("Wind Speed:  " + dataArr[7]);

    }

    @Override
    public int getItemCount() {
        return hourlyArrayList.size();
    }
}
