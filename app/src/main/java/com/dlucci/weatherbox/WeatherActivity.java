package com.dlucci.weatherbox;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        API_KEY = getString(R.string.weatherApi);

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


    private class WeatherTask extends AsyncTask<Void, Void, Void>{

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

                Log.d(TAG, json1.getString("temp_F"));

            }catch(MalformedURLException e){
                Log.e(TAG, "URL is malformed:  " + e.getMessage());
            }catch(IOException e){
                Log.e(TAG, "IOException:  " + e.getMessage());
            } catch(JSONException e){
                Log.e(TAG, "JSONException:  " + e.getMessage());
            }


            return null;
        }
    }
}
