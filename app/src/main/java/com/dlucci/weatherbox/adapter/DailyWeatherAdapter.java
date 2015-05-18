package com.dlucci.weatherbox.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.model.Weather;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by derrillucci on 11/20/14.
 */
public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder>{

    private Context context;
    private int layout;

    private SharedPreferences sharedPrefs;
    private ArrayList<Weather> weatherList;



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView icon;
        public TextView date;
        public TextView temperature;
        public TextView uvIndex;
        public TextView sunrise;
        public TextView sunset;

        public Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView)itemView.findViewById(R.id.icon);
            date = (TextView)itemView.findViewById(R.id.date);
            temperature = (TextView)itemView.findViewById(R.id.temperature);
            uvIndex = (TextView)itemView.findViewById(R.id.uvIndex);
            sunrise = (TextView)itemView.findViewById(R.id.sunrise);
            sunset = (TextView)itemView.findViewById(R.id.sunset);

            context = itemView.getContext();
            CardView cardView = (CardView)itemView.findViewById(R.id.dailyCardView);
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

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(viewHolder.context);
        String suffix = "°" + (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? "F" : "C");

        String[] dataArr = new String[7];

        dataArr[2] = weatherList.get(i).hourly.get(4).weatherIconUrl.get(0).value;
        dataArr[3] = weatherList.get(i).date;
        dataArr[4] = weatherList.get(i).uvIndex;
        dataArr[5] = weatherList.get(i).astronomy.get(0).sunrise;
        dataArr[6] = weatherList.get(i).astronomy.get(0).sunset;

        if(suffix.contains("C")){
            dataArr[0] = weatherList.get(i).maxtempC;
            dataArr[1] = weatherList.get(i).mintempC;
        }else{
            dataArr[0] = weatherList.get(i).maxtempF;
            dataArr[1] = weatherList.get(i).mintempF;
        }

        viewHolder.temperature.setText("Temperature:  " + dataArr[0] + suffix + "/" + dataArr[1] + suffix);
        viewHolder.date.setText("Date:  " + dataArr[3]);

        Resources r = viewHolder.context.getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        Picasso.with(viewHolder.context).load(dataArr[2]).resize(px, px).into(viewHolder.icon);

        viewHolder.uvIndex.setText("uvIndex:  " + dataArr[4]);
        viewHolder.sunrise.setText("Sunrise:  " + dataArr[5]);
        viewHolder.sunset.setText("Sunset:  " + dataArr[6]);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
