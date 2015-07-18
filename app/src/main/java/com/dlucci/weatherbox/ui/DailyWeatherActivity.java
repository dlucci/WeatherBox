package com.dlucci.weatherbox.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.dlucci.weatherbox.FetchWeatherTask;
import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.model.WeatherInformation;
import com.dlucci.weatherbox.util.RecyclerViewItemClick;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.app.ActionBar.OnNavigationListener;
/*
 *  TODO
 *  9. add notification (much like accuweather app)
 *  10. add hugo for better logging
 *  11. add some analytics
 *  13. take another look at error handling
 *  14. rename action_list to something more appropriate
 *  18. create button in action bar to get weather based on current location
 *  19. add ability in action bar to add new zip codes
 *  20. update action bar listener to the newest guidelines (http://developer.android.com/reference/android/app/ActionBar.OnNavigationListener.html)
 *  21. figure out a good way of doing an action inside of the action bar listener
 *  22. enable configurable home location
 *  23. PULL TO REFRESH (sample in the android samples [$SDK_HOME/samples])
 *  25. take another look at the date stuff.  i don't like having to roll my own solution (JODA TIME BITCHES!)
 *  27. add testing
 *  28. add themes for pre Lollipop devices
 *  29. experiment with permissions for android m
 */

public class DailyWeatherActivity extends Activity {

    private static final String TAG = "DailyWeatherActivity";

    private static ProgressDialog dialog;

    @InjectView(R.id.list)
    public static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private SharedPreferences sharedPrefs;

    private SpinnerAdapter spinnerAdapter;

    private ActionBar actionBar;

    private WeatherInformation weatherInformation;

    @InjectView(R.id.swipeRefresh)
    public SwipeRefreshLayout swipeRefreshLayout;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("weather");

            weatherInformation = (WeatherInformation)intent.getSerializableExtra("weather");
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);
        ButterKnife.inject(this);

        Log.d(TAG, "onCreate");

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        setTitle("Daily Weather For");
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);

        OnNavigationListener navigationListener = new OnNavigationListener() {
            String[] strings = getResources().getStringArray(R.array.action_list);

            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                //fetchWeather(strings[itemPosition]);
                strings[0] = "44114";//This does not work.  We need to figure out how to dynamically change this
                return true;
            }
        };

        actionBar.setListNavigationCallbacks(spinnerAdapter, navigationListener);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClick(this, new RecyclerViewItemClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(DailyWeatherActivity.this, HourlyWeatherActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("Weather", weatherInformation.weather.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchWeather("44114");
            }
        });


    }

    private void fetchWeather(String zipcode){
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait.  Weather loading....");
        dialog.show();

        new FetchWeatherTask(zipcode, this).execute(swipeRefreshLayout);
    }

    @Override public void onResume(){
        super.onResume();

        fetchWeather("44114"); //FOR DEV PURPOSES ONLY

        Log.d(TAG, "onResume");
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getCurrentZipCode() throws IOException {
        LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder geo = new Geocoder(getApplicationContext());
        List<Address> list = geo.getFromLocation(latitude, longitude, 1);
        if(list.size() == 0)
            return null;
        Address addr = list.get(0);
        return addr.getPostalCode();
    }

    public static ProgressDialog getDialog(){
        if(dialog != null)
            return dialog;

        return null;
    }

    public static RecyclerView getRecyclerView(){
        if(recyclerView != null)
            return recyclerView;

        return null;
    }
}

