package com.dlucci.weatherbox;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Properties;


public class WeatherActivity extends Activity {

    private static final String TAG = "WeatherActivity";
    private static String API_KEY;

    private String temperatureF, temperatureC;
    private TextView tempF;
    private ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        API_KEY = getString(R.string.weatherApi);

        tempF = (TextView)findViewById(R.id.temperature);
        weatherIcon = (ImageView)findViewById(R.id.icon);
    }

    @Override
    public void onResume(){
        super.onResume();
        new WeatherTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*
 * We need to add a check for if there is a connection to the interblag
 *
 */


    private class WeatherTask extends AsyncTask<Void, Void, Void>{

        private String imageUrl;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http://api.worldweatheronline.com/free/v2/weather.ashx?q=44114&format=json&num_of_days=5&key="+API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = br.readLine()) != null){
                    result.append(line);
                }

                JSONObject json = new JSONObject(result.toString());
                JSONObject json1 = json.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0);

                temperatureF = json1.getString("temp_F");
                temperatureC = json1.getString("temp_C");

                JSONObject symbol = json1.getJSONArray("weatherIconUrl").getJSONObject(0);

                imageUrl = symbol.getString("value");
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
            if(temperatureF == null)
                tempF.setText("Unable to load temperature");
            else
                tempF.setText(temperatureF + "° F/" + temperatureC + "° C");

            if(imageUrl == null)
                Log.d(TAG, ":-(");
            else
                Picasso.with(getApplicationContext()).load(imageUrl).into(weatherIcon);
        }
    }
}
