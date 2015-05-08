package com.dlucci.weatherbox;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dlucci.weatherbox.model.Hourly;
import com.dlucci.weatherbox.model.Weather;
import com.squareup.picasso.Picasso;

/**
 * Created by derlucci on 5/5/15.
 */
public class HourlyWeatherActivity extends ListActivity {

    private static final String TAG = "HourlyWeatherActivity";

    private Weather weather;

    private ImageView image;
    private TextView maxTemp, date;

    private ListView list;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hourly_weather);

        Intent intent = getIntent();

        if(intent != null)
            weather = (Weather)intent.getSerializableExtra("Weather");

        setTitle("Hourly Weather For " + weather.date );


        list = (ListView) findViewById(android.R.id.list);

        MatrixCursor mc = new MatrixCursor(new String[] {"_id", "temp", "dewPoint", "feelsLike", "weatherDesc", "weatherUrl", "windChill", "windSpeed", "time"});

        int i =0;
        for(Hourly hourly : weather.hourly){
                mc.addRow(new Object[]{i, hourly.tempF, hourly.DewPointF, hourly.FeelsLikeF, hourly.weatherDesc.get(0).value, hourly.weatherIconUrl.get(0).value, hourly.WindChillF, hourly.windspeedMiles, hourly.time});
                i++;
        }

        ListAdapter listAdapter = new HourlyWeatherAdapter(getApplicationContext(),
                R.layout.hourly_weather_row,
                mc,
                new String[] {"_id", "temp", "dewPoint", "feelsLike", "weatherDesc", "weatherUrl", "windChill", "windSpeed", "time"},
                new int[] {0, R.id.temperature, R.id.dewPoint, R.id.feelsLike, R.id.weatherDescription, R.id.weatherImage, R.id.windChill, R.id.windSpeed, R.id.hour }
                );

        setListAdapter(listAdapter);

        /*image = (ImageView) findViewById(R.id.weatherImage);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        date = (TextView) findViewById(R.id.date);

        Resources r = this.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        Picasso.with(this).load(weather.hourly.get(0).weatherIconUrl.get(0).value).resize(px, px).into(image);
        maxTemp.setText(weather.hourly.get(0).tempF);
        date.setText(weather.date);*/
    }
}
