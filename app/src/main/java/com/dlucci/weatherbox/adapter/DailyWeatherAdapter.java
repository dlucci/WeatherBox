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
import com.dlucci.weatherbox.model.Weather;
import com.dlucci.weatherbox.util.DateFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;

/**
 * Created by derrillucci on 11/20/14.
 */
public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>{

    private ArrayList<Weather> weatherList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.icon)
        public ImageView icon;

        @InjectViews({R.id.date, R.id.temperature, R.id.uvIndex, R.id.sunrise, R.id.sunset})
        public List<TextView> textViewList;

        @InjectView(R.id.dailyCardView)
        public CardView cardView;

        public Context context;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.inject(this, view);

            context = view.getContext();
            cardView.setRadius(4);
            cardView.setUseCompatPadding(true);
        }
    }

    public DailyWeatherAdapter(ArrayList<Weather> weathers) {
        weatherList = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_row, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(viewHolder.context);
        String suffix = "°" + (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? "F" : "C");

        String[] dataArr = new String[7];

        dataArr[2] = weatherList.get(i).hourly.get(4).weatherIconUrl.get(0).value;
        dataArr[3] = weatherList.get(i).date;
        dataArr[4] = weatherList.get(i).uvIndex;
        dataArr[5] = weatherList.get(i).astronomy.get(0).sunrise;
        dataArr[6] = weatherList.get(i).astronomy.get(0).sunset;

        if(suffix.contains("C")){
            dataArr[0] = weatherList.get(i).maxTempC;
            dataArr[1] = weatherList.get(i).minTempC;
        }else{
            dataArr[0] = weatherList.get(i).maxTempF;
            dataArr[1] = weatherList.get(i).minTempF;
        }

        viewHolder.textViewList.get(1).setText("Temperature:  " + dataArr[0] + suffix + "/" + dataArr[1] + suffix);
        viewHolder.textViewList.get(0).setText("Date:  " + DateFormatter.getToday(dataArr[3]));

        Resources r = viewHolder.context.getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        Picasso.with(viewHolder.context).load(dataArr[2]).resize(px, px).into(viewHolder.icon);

        viewHolder.textViewList.get(2).setText("uvIndex:  " + dataArr[4]);
        viewHolder.textViewList.get(3).setText("Sunrise:  " + dataArr[5]);
        viewHolder.textViewList.get(4).setText("Sunset:  " + dataArr[6]);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
