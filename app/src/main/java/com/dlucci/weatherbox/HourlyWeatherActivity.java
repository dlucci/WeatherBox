package com.dlucci.weatherbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlucci.weatherbox.model.Weather;
import com.squareup.picasso.Picasso;

/**
 * Created by derlucci on 5/5/15.
 */
public class HourlyWeatherActivity extends Activity {

    private static final String TAG = "HourlyWeatherActivity";

    private Weather weather;

    private ImageView image;
    private TextView maxTemp, date;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hourly_weather);

        Intent intent = getIntent();
        Bundle data = new Bundle();

        if(intent != null){
            weather = (Weather)intent.getSerializableExtra("Weather");
        }

        image = (ImageView) findViewById(R.id.weatherImage);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        date = (TextView) findViewById(R.id.date);

        Picasso.with(this).load(weather.hourly.get(0).weatherIconUrl.get(0).value).into(image);
        maxTemp.setText(weather.hourly.get(0).tempF);
        date.setText(weather.date);

    }
}
