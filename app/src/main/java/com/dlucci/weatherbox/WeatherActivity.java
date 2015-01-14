package com.dlucci.weatherbox;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.dlucci.weatherbox.model.Weather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class WeatherActivity extends ListActivity {

    private static final String TAG = "WeatherActivity";
    private static String API_KEY;

    private String temperatureF, temperatureC;
    private TextView tempF;
    private ImageView weatherIcon;

    private ProgressDialog dialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);

        API_KEY = getString(R.string.weatherApi);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait.  Weather loading....");
        dialog.show();
        new WeatherTask().execute();
    }

    @Override public void onResume(){
        super.onResume();
    }


    /*
        taking this out for now.  i'd like to add some settings once i get this layout working properly

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

/*
 * We need to add a check for if there is a connection to the interblag
 *
 */


    private class WeatherTask extends AsyncTask<Void, Void, Void>{

        private String imageUrl;
        private String zipcode;
        private Object[] arr = new Object[5];
        MatrixCursor mc = new MatrixCursor(new String[] {"_id", "tempF", "tempC", "imageURL"});
        @Override
        protected Void doInBackground(Void... params) {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected())
                return null;
            try {

                LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Geocoder geo = new Geocoder(getApplicationContext());
                List<Address> list = geo.getFromLocation(latitude, longitude, 1);
                if(list.size() == 0)
                    return null;
                Address addr = list.get(0);
                
                zipcode = addr.getPostalCode();
                URL url = new URL("http://api.worldweatheronline.com/free/v2/weather.ashx?q=" + zipcode + "&format=json&num_of_days=5&key="+API_KEY);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    result.append(line);
                }

                JSONObject json = new JSONObject(result.toString());

                JSONArray futureCast = json.getJSONObject("data").getJSONArray("weather");

                for(int i = 0; i < 5; i++){
                    JSONObject j = futureCast.getJSONObject(i).getJSONArray("hourly").getJSONObject(0);
                    Weather weather = new Weather();
                    weather.setTemperatureC(j.getString("tempC"));
                    weather.setTemperatureF(j.getString("tempF"));
                    weather.setImageUrl(j.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));
                    mc.addRow(new Object[]{i, j.getString("tempF"), j.getString("tempC"), j.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value")});
                }

            }catch(MalformedURLException e){
                Log.e(TAG, "URL is malformed:  " + e.getMessage());
            }catch(IOException e){
                Log.e(TAG, "IOException:  " + e.getMessage());
            } catch(JSONException e){
                Log.e(TAG, "JSONException:  " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args){

            dialog.dismiss();
            if(zipcode != null) {
                TextView zippy = (TextView) findViewById(R.id.zipcode);
                zippy.setText("This is the weather for " + zipcode);

                ListAdapter adapter = new WeatherAdapter(getApplicationContext(),
                        R.layout.weather_row,
                        mc,
                        new String[]{"_id", "tempF", "tempC", "imageURL"},
                        new int[]{0,R.id.temperatureF, R.id.temperatureC, R.id.icon});

                setListAdapter(adapter);
            } else {
                Log.d(TAG, "zipcode is null");
                ImageView error  = (ImageView)findViewById(R.id.uhoh);
                error.setImageResource(R.drawable.oh_no);
            }


        }
    }
}
