package com.dlucci.weatherbox;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        Cursor cur = getCursor();

        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);

        String tempF = cur.getString(cur.getColumnIndex("tempF")) + "F";
        String tempC = cur.getString(cur.getColumnIndex("tempC")) + "C";
        String imageUrl = cur.getString(cur.getColumnIndex("imageURL"));

        TextView f = (TextView) v.findViewById(R.id.temperatureF);
        TextView c = (TextView) v.findViewById(R.id.temperatureC);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);

        if(f != null)
            f.setText(tempF);
        if(c != null)
            c.setText(tempC);
        if(icon != null)
            Picasso.with(context).load(imageUrl).into(icon);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor cur){
        String tempF = cur.getString(cur.getColumnIndex("tempF")) + "F";
        String tempC = cur.getString(cur.getColumnIndex("tempC")) + "C";
        String imageUrl = cur.getString(cur.getColumnIndex("imageURL"));

        TextView f = (TextView) v.findViewById(R.id.temperatureF);
        TextView c = (TextView) v.findViewById(R.id.temperatureC);
        ImageView icon = (ImageView) v.findViewById(R.id.icon);

        if(f != null)
            f.setText(tempF);
        if(c != null)
            c.setText(tempC);
        if(icon != null) {
            if(imageUrl == null)
                icon.setImageResource(R.drawable.oh_no);
            else
                Picasso.with(context).load(imageUrl).into(icon);
        }
    }
}
