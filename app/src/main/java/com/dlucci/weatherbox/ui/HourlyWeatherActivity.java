package com.dlucci.weatherbox.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.adapter.HourlyWeatherAdapter;
import com.dlucci.weatherbox.model.Hourly;
import com.dlucci.weatherbox.model.Weather;
import com.dlucci.weatherbox.util.DateFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by derlucci on 5/5/15.
 */
public class HourlyWeatherActivity extends Activity {

    private static final String TAG = "HourlyWeatherActivity";

    private Weather weather;

    @InjectView(R.id.hourlyWeather)
    public RecyclerView recyclerView;

    SharedPreferences sharedPrefs;

    @InjectView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hourly_weather);
        ButterKnife.inject(this);

        Intent intent = getIntent();

        if(intent != null)
            weather = (Weather)intent.getSerializableExtra("Weather");

        setTitle("Hourly Weather For " + DateFormatter.getToday(weather.date));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

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
