package com.dlucci.weatherbox.ui;

import android.app.ActionBar;
import static android.app.ActionBar.OnNavigationListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

import com.dlucci.weatherbox.adapter.DailyWeatherAdapter;
import com.dlucci.weatherbox.R;
import com.dlucci.weatherbox.model.Weather;
import com.dlucci.weatherbox.model.WeatherInformation;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
/*
 *  TODO
 *  7. Add RetroFit for blazing fast API calls
 *  8. Change date to say date (Saturday, May 2nd, etc) instead of 2015-05-02
 *  9. add notification (much like accuweather app)
 *  10. add hugo for better logging
 *  11. add some analytics
 *  13. take another look at error handling
 *  14. rename action_list to something more appropriate
 *  15. rename model variables to camelCase values using @JSONProperties(...)
 *  16. put butterknife in for smoother view injection
 *  17. fix picture height on hourly activity
 *  18. create button in action bar to get weather based on current location
 *  19. add ability in action bar to add new zip codes
 *  20. update action bar listener to the newest guidelines (http://developer.android.com/reference/android/app/ActionBar.OnNavigationListener.html)
 *  21. figure out a good way of doing an action inside of the action bar listener
 *  22. enable configurable home location
 *  23. add material design
 *  24. figure out a better way to represent time (include sharedPref on this)
 */

public class DailyWeatherActivity extends Activity {

    private static final String TAG = "DailyWeatherActivity";
    private static String API_KEY;

    private ProgressDialog dialog;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private SharedPreferences sharedPrefs;

    private WeatherInformation weatherInformation;

    private SpinnerAdapter spinnerAdapter;

    private ActionBar actionBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);

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

        recyclerView = (RecyclerView) findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        /*recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DailyWeatherActivity.this, HourlyWeatherActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Weather", weatherInformation.weather.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        *
        * Move this to the adapter
        */

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        API_KEY = getString(R.string.weatherApi);


    }

    private void fetchWeather(String zipcode){
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait.  Weather loading....");
        dialog.show();

        new WeatherTask(zipcode).execute();
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

    public String getCurrentZipCode() throws IOException {
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

/*
 * We need to add a check for if there is a connection to the interblag
 */

    private class WeatherTask extends AsyncTask<Void, Void, Void>{

        private String zipcode;
        ArrayList<Weather> weatherList;

        public WeatherTask(String zipcode){
            this.zipcode = zipcode;
        }

        @Override protected Void doInBackground(Void... params) {

            Log.d(TAG, "beginning doInBackground");
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected())
                return null;
            try {

                URL url;

                Log.d(TAG, "Getting weather for " + zipcode + " and for " + sharedPrefs.getString("daySetting", "5") + " days");

                boolean imperial = sharedPrefs.getString("measurementSetting", "imperial").equals("imperial") ? true : false;

                if(zipcode != null)
                    url = new URL("http://api.worldweatheronline.com/free/v2/weather.ashx?q="  + zipcode + "&format=json&num_of_days=" + sharedPrefs.getString("daySetting", "5") + "&key="+API_KEY);
                else{
                    /**
                     * Fetch current location
                     * get zipcode for that location
                     */

                    zipcode = DailyWeatherActivity.this.getCurrentZipCode();

                    url = new URL("http://api.worldweatheronline.com/free/v2/weather.ashx?q="  + zipcode + "&format=json&num_of_days=" + sharedPrefs.getString("daySetting", "5") + "&key="+API_KEY);
                }

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                Log.d(TAG, "received communication from url");
                Log.d(TAG, String.valueOf(urlConnection.getResponseCode()));
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    result.append(line);
                }

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
                weatherInformation = mapper.readValue(result.toString(), WeatherInformation.class);

                weatherList = new ArrayList<>();

                for(Weather weather : weatherInformation.weather){
                    weatherList.add(weather);
                }
                adapter = new DailyWeatherAdapter(weatherList);
            }catch(IOException e){
                Log.e(TAG, "IOException:  " + e.getMessage());
            }

            return null;
        }

        @Override protected void onPostExecute(Void args){

            dialog.dismiss();
            //if(zipcode != null) {

               recyclerView.setAdapter(adapter);
            /*} else {
                Log.d(TAG, "zipcode is null");
                ImageView error  = (ImageView)findViewById(R.id.uhoh);
                error.setImageResource(R.drawable.oh_no);
                error.setVisibility(View.VISIBLE);
            }*/

        }
    }
}
