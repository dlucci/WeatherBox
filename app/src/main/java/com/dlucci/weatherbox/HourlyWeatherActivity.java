package com.dlucci.weatherbox;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dlucci.weatherbox.model.Hourly;
import com.dlucci.weatherbox.model.Weather;

/**
 * Created by derlucci on 5/5/15.
 */
public class HourlyWeatherActivity extends ListActivity {

    private static final String TAG = "HourlyWeatherActivity";

    private Weather weather;

    private ListView list;

    SharedPreferences sharedPrefs;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hourly_weather);

        Intent intent = getIntent();

        if(intent != null)
            weather = (Weather)intent.getSerializableExtra("Weather");

        setTitle("Hourly Weather For " + weather.date );

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        list = (ListView) findViewById(android.R.id.list);

        MatrixCursor mc = new MatrixCursor(new String[] {"_id", "temp", "dewPoint", "feelsLike", "weatherDesc", "weatherUrl", "windChill", "windSpeed", "time"});

        int i =0;
        for(Hourly hourly : weather.hourly) {
            if (sharedPrefs.getString("measurementSetting", "imperial").equals("imperial"))
                mc.addRow(new Object[]{i, hourly.tempF, hourly.DewPointF, hourly.FeelsLikeF, hourly.weatherDesc.get(0).value, hourly.weatherIconUrl.get(0).value, hourly.WindChillF, hourly.windspeedMiles, hourly.time});
            else
                mc.addRow(new Object[]{i, hourly.tempC, hourly.DewPointC, hourly.FeelsLikeC, hourly.weatherDesc.get(0).value, hourly.weatherIconUrl.get(0).value, hourly.WindChillC, hourly.windspeedKmph, hourly.time});
                i++;
        }

        ListAdapter listAdapter = new HourlyWeatherAdapter(getApplicationContext(),
                R.layout.hourly_weather_row,
                mc,
                new String[] {"_id", "temp", "dewPoint", "feelsLike", "weatherDesc", "weatherUrl", "windChill", "windSpeed", "time"},
                new int[] {0, R.id.temperature, R.id.dewPoint, R.id.feelsLike, R.id.weatherDescription, R.id.weatherImage, R.id.windChill, R.id.windSpeed, R.id.hour }
                );

        setListAdapter(listAdapter);

    }

    @Override public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return false;
    }
}
