package com.dlucci.weatherbox;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by derrillucci on 11/20/14.
 */
public class WeatherAdapter extends SimpleCursorAdapter{

    private Context context;
    private int layout;

    public WeatherAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
    }

    @Override public View newView(Context context, Cursor cursor, ViewGroup parent){

        Cursor cur = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);

        String tempF = cur.getString(cur.getColumnIndex("maxTemp")) + "°F";
        String tempC = cur.getString(cur.getColumnIndex("minTemp")) + "°F";
        String imageUrl = cur.getString(cur.getColumnIndex("imageURL"));
        String date = cur.getString(cur.getColumnIndex("date"));
        String uvIndex = cur.getString(cur.getColumnIndex("uvIndex"));
        String sunrise = cur.getString(cur.getColumnIndex("sunrise"));
        String sunset = cur.getString(cur.getColumnIndex("sunset"));

        TextView f = (TextView) v.findViewById(R.id.temperature);
        TextView d = (TextView) v.findViewById(R.id.date);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        TextView uv = (TextView) v.findViewById(R.id.uvIndex);
        TextView rise = (TextView) v.findViewById(R.id.sunrise);
        TextView set = (TextView) v.findViewById(R.id.sunset);

        if(f != null && tempF != null && tempC != null)
            f.setText("Temperature:  " + tempF + "/" + tempC);
        else
            f.setText("Temperature:  --°F/--°F");

        if(d != null && date != null)
            d.setText("Date:  " + date);
        else
            d.setText("Date:  ??-??-????");
        if(icon != null) {
            if(imageUrl == null) {
                icon.setImageResource(R.drawable.oh_no);
                icon.setVisibility(View.VISIBLE);
            }else
                Picasso.with(context).load(imageUrl).into(icon);
        }

        if(uv != null)
            uv.setText("uvIndex:  " + uvIndex);
        else
            uv.setText("uvIndex:  --");

        if(rise != null)
            rise.setText("Sunrise:  "  + sunrise);
        else
            rise.setText("Sunrise:  --:--");

        if(set != null)
            set.setText("Sunset:  " + sunset);
        else
            set.setText("Sunset:  --:--");

        return v;
    }

    @Override public void bindView(View v, Context context, Cursor cur){

        String tempF = cur.getString(cur.getColumnIndex("maxTemp")) + "°F";
        String tempC = cur.getString(cur.getColumnIndex("minTemp")) + "°F";
        String imageUrl = cur.getString(cur.getColumnIndex("imageURL"));
        String uvIndex = cur.getString(cur.getColumnIndex("uvIndex"));
        String sunrise = cur.getString(cur.getColumnIndex("sunrise"));
        String sunset = cur.getString(cur.getColumnIndex("sunset"));
        String date = cur.getString(cur.getColumnIndex("date"));

        TextView f = (TextView) v.findViewById(R.id.temperature);
        TextView d = (TextView) v.findViewById(R.id.date);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);
        TextView uv = (TextView) v.findViewById(R.id.uvIndex);
        TextView rise = (TextView) v.findViewById(R.id.sunrise);
        TextView set = (TextView) v.findViewById(R.id.sunset);

        if(f != null && tempF != null && tempC != null)
            f.setText("Temperature:  " + tempF + "/" + tempC);
        else
            f.setText("Temperature:  --°F/--°F");

        if(d != null && date != null)
            d.setText("Date:  " + date);
        else
            d.setText("Date:  ??-??-????");

        if(icon != null) {
            if(imageUrl == null) {
                icon.setImageResource(R.drawable.oh_no);
                icon.setVisibility(View.VISIBLE);
            }else {
                Picasso.with(context).load(imageUrl).into(icon);
            }
        }

        if(uv != null)
            uv.setText("uvIndex:  " + uvIndex);
        else
            uv.setText("uvIndex:  --");

        if(rise != null)
            rise.setText("Sunrise:  " + sunrise);
        else
            rise.setText("Sunrise:  --:--");

        if(set != null)
            set.setText("Sunset:  " + sunset);
        else
            set.setText("Sunset:  --:--");

    }
}
