package com.dlucci.weatherbox.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.adapter.HourlyWeatherAdapter;
import com.dlucci.weatherbox.model.Hourly;
import com.dlucci.weatherbox.model.Weather;

import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class HourlyWeatherActivity extends Activity {

    private static final String TAG = "HourlyWeatherActivity";

    private Weather weather;

    private RecyclerView recyclerView;

    SharedPreferences sharedPrefs;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hourly_weather);

        Intent intent = getIntent();

        if(intent != null)
            weather = (Weather)intent.getSerializableExtra("Weather");

        setTitle("Hourly Weather For " + weather.date);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = (RecyclerView) findViewById(R.id.hourlyWeather);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        ArrayList<Hourly> hourlyArrayList = new ArrayList<>();
        for(Hourly hourly : weather.hourly) {
            hourlyArrayList.add(hourly);
        }

        HourlyWeatherAdapter adapter = new HourlyWeatherAdapter(hourlyArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return false;
    }
}
