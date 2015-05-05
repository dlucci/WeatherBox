package com.dlucci.weatherbox;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.TextView;

//import com.dlucci.weatherbox.model.Weather;

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
import java.net.MalformedURLException;
import java.net.URL;

/*
 *  TODO:
 *  0. Get Jackson to work (DONE)
 *  1. Figure out a better way to do images.
 *  2. Do temperature max/min, not hourly
 *  3. If clicked, display hourly data for each day
 *  4. Add color coding for temperature (blue[t<=32], black[(32<t<75]], red[t>=75])
 *  5. Add settings for data
 */

public class WeatherActivity extends ListActivity {

    private static final String TAG = "WeatherActivity";
    private static String API_KEY;

    private ProgressDialog dialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_list);

        Log.d(TAG, "onCreate");

        API_KEY = getString(R.string.weatherApi);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait.  Weather loading....");
        dialog.show();
        new WeatherTask().execute();
    }

    @Override public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
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
 */

    private class WeatherTask extends AsyncTask<Void, Void, Void>{

        private String zipcode;
        private Object[] arr = new Object[5];
        MatrixCursor mc = new MatrixCursor(new String[] {"_id", "maxTemp", "minTemp", "imageURL", "date"});

        @Override protected Void doInBackground(Void... params) {

            Log.d(TAG, "beginning doInBackground");
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnected())
                return null;
            try {

                /*
                TODO:  uncomment this code out before testing on an actual device...same for the zipcode stuff in onPostExecute

                LocationManager manager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Geocoder geo = new Geocoder(getApplicationContext());
                List<Address> list = geo.getFromLocation(latitude, longitude, 1);
                if(list.size() == 0)
                    return null;
                Address addr = list.get(0);
                
                zipcode = addr.getPostalCode();*/
                URL url = new URL("http://api.worldweatheronline.com/free/v2/weather.ashx?q="  /* + zipcode*/ +"44114" + "&format=json&num_of_days=5&key="+API_KEY);

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                Log.d(TAG, "received communication from url");

                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    result.append(line);
                }
                Log.d(TAG, result.toString());
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
                WeatherInformation weatherInformation = mapper.readValue(result.toString(), WeatherInformation.class);

                for(Weather weather : weatherInformation.weather){
                    int i = 0;
                    mc.addRow(new Object[]{i, weather.maxtempF, weather.mintempF, weather.hourly.get(4).weatherIconUrl.get(0).value, weather.date});
                    i++;
                }

            }catch(MalformedURLException e){
                Log.e(TAG, "URL is malformed:  " + e.getMessage());
            }catch(IOException e){
                Log.e(TAG, "IOException:  " + e.getMessage());
            }

            return null;
        }

        @Override protected void onPostExecute(Void args){

            dialog.dismiss();
            //if(zipcode != null) {
                TextView zippy = (TextView) findViewById(R.id.zipcode);
                zippy.setText("This is the weather for " + zipcode);

                ListAdapter adapter = new WeatherAdapter(getApplicationContext(),
                        R.layout.weather_row,
                        mc,
                        new String[]{"_id", "maxTemp", "minTemp", "imageURL", "date"},
                        new int[]{0,R.id.temperature, R.id.date, R.id.icon});

                setListAdapter(adapter);
            /*} else {
                Log.d(TAG, "zipcode is null");
                ImageView error  = (ImageView)findViewById(R.id.uhoh);
                error.setImageResource(R.drawable.oh_no);
                error.setVisibility(View.VISIBLE);
            }*/

        }
    }
}
