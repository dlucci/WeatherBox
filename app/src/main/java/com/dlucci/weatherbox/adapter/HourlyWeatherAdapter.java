package com.dlucci.weatherbox.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.dlucci.weatherbox.R;
import com.squareup.picasso.Picasso;

/**
 * Created by derlucci on 5/6/15.
 */
public class HourlyWeatherAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;

    private SharedPreferences sharedPrefs;

    public HourlyWeatherAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent){
        Cursor cur = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(this.layout, parent, false);

        String suffix = "°" + (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? "F" : "C");

        String hour = cur.getString(cur.getColumnIndex("time"));
        String temp = cur.getString(cur.getColumnIndex("temp"));
        String dewPoint = cur.getString(cur.getColumnIndex("dewPoint"));
        String feels = cur.getString(cur.getColumnIndex("feelsLike"));
        String weatherDesc = cur.getString(cur.getColumnIndex("weatherDesc"));
        String weatherUrl = cur.getString(cur.getColumnIndex("weatherUrl"));
        String windChill = cur.getString(cur.getColumnIndex("windChill"));
        String windSpeed = cur.getString(cur.getColumnIndex("windSpeed"));

        TextView h = (TextView) v.findViewById(R.id.hour);
        TextView t = (TextView) v.findViewById(R.id.temperature);
        TextView dp = (TextView) v.findViewById(R.id.dewPoint);
        TextView wd = (TextView) v.findViewById(R.id.weatherDescription);
        TextView f = (TextView) v.findViewById(R.id.feelsLike);
        ImageView icon = (ImageView) v.findViewById(R.id.weatherImage);
        TextView wc = (TextView) v.findViewById(R.id.windChill);
        TextView ws = (TextView) v.findViewById(R.id.windSpeed);

        if(h != null){

            if(hour.length() == 3){
               char[] chars = hour.toCharArray();
               hour = chars[0] + ":" + chars[1] + chars[2];
            } else if(hour.length() == 4) {
                int value = new Integer(Integer.valueOf(hour));
                value = value/100;
                hour = new String(new Integer(value).toString()) + ":00";
            }

            h.setText(hour);
        }

        if(t != null)
            t.setText("Temperature:  " + temp + suffix);
        if(dp != null)
            dp.setText("Dewpoint:  " + dewPoint + suffix);
        if(f != null)
            f.setText("Feels like:  " + feels + suffix);
        if(wd != null)
            wd.setText(weatherDesc);
        if(icon != null){
            Resources r = this.context.getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
            Picasso.with(this.context).load(weatherUrl).resize(px, px).into(icon);
        }
        if(wc != null)
            wc.setText("Wind Chill:  " + windChill + suffix);
        if(ws != null)
            ws.setText("Wind Speed:  " + windSpeed);

        return v;
    }

    @Override public void bindView(View v, Context context, Cursor cur){

        String suffix = "°" + (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? "F" : "C");

        String hour = cur.getString(cur.getColumnIndex("time"));
        String temp = cur.getString(cur.getColumnIndex("temp"));
        String feels = cur.getString(cur.getColumnIndex("feelsLike"));
        String dewPoint = cur.getString(cur.getColumnIndex("dewPoint"));
        String weatherDesc = cur.getString(cur.getColumnIndex("weatherDesc"));
        String weatherUrl = cur.getString(cur.getColumnIndex("weatherUrl"));
        String windChill = cur.getString(cur.getColumnIndex("windChill"));
        String windSpeed = cur.getString(cur.getColumnIndex("windSpeed"));

        TextView h = (TextView) v.findViewById(R.id.hour);
        TextView t = (TextView) v.findViewById(R.id.temperature);
        TextView dp = (TextView) v.findViewById(R.id.dewPoint);
        TextView f = (TextView) v.findViewById(R.id.feelsLike);
        TextView wd = (TextView) v.findViewById(R.id.weatherDescription);
        ImageView icon = (ImageView) v.findViewById(R.id.weatherImage);
        TextView wc = (TextView) v.findViewById(R.id.windChill);
        TextView ws = (TextView) v.findViewById(R.id.windSpeed);

        if(h != null){

            if(hour.length() == 3){
               char[] chars = hour.toCharArray();
               hour = chars[0] + ":" + chars[1] + chars[2];
            } else if(hour.length() == 4) {
                int value = new Integer(Integer.valueOf(hour));
                value = value/100;
                hour = new String(new Integer(value).toString()) + ":00";
            }

            h.setText(hour);
        }

        if(t != null)
             t.setText("Temperature:  " + temp + suffix);
         if(dp != null)
             dp.setText("Dewpoint:  " + dewPoint + suffix);
         if(f != null)
             f.setText("Feels like:  " + feels + suffix);
         if(wd != null)
             wd.setText(weatherDesc);
         if(icon != null){
             Resources r = this.context.getResources();
             int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
             Picasso.with(this.context).load(weatherUrl).resize(px, px).into(icon);
         }
         if(wc != null)
             wc.setText("Wind Chill:  " + windChill + suffix);
         if(ws != null)
             ws.setText("Wind Speed:  " + windSpeed);

    }
}
