package com.dlucci.weatherbox.model;

<<<<<<< HEAD
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class CurrentCondition {

    public String cloudcover;
    public String FeelsLikeC;
    public String FeelsLikeF;
    public String humidity;
    public String observation_time;
    public String precipMM;
    public String pressure;
    public String temp_C;
    public String temp_F;
    public String visibility;
    public String weatherCode;

    public ArrayList<WeatherDesc> weatherDesc;
    public ArrayList<WeatherDesc> weatherIconUrl;

    public String winddir16Point;
    public String winddirDegree;
    public String windspeedKmph;
    public String windspeedMiles;
=======
/**
 * Created by derlucci on 3/13/15.
 */
public class CurrentCondition {
    private String cloudcover;
    private String FeelsLikeC;
    private String FeelsLIkeF;
    private String humidity;
    private String observation_time;
    private String precipMM;
    private String pressue;
    private String temp_C;
    private String temp_F;
    private String visibility;
    private String weatherCode;
    private WeatherDesc weatherDesc;
    private String[] weatherIconUrl;
    private String winddir16Point;
    private String winddirDegree;
    private String windspeedKmph;
    private String windspeedMiles;


    public String[] getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String[] weatherIconUrl) {
        android.util.Log.d("EIFLE", weatherIconUrl[0]);
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    private enum WeatherDesc{
        Overcast;
    }

    public CurrentCondition() {
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getFeelsLikeC() {
        return FeelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        FeelsLikeC = feelsLikeC;
    }

    public String getFeelsLIkeF() {
        return FeelsLIkeF;
    }

    public void setFeelsLIkeF(String feelsLIkeF) {
        FeelsLIkeF = feelsLIkeF;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPressue() {
        return pressue;
    }

    public void setPressue(String pressue) {
        this.pressue = pressue;
    }

    public String getTemp_C() {
        return temp_C;
    }

    public void setTemp_C(String temp_C) {
        this.temp_C = temp_C;
    }

    public String getTemp_F() {
        return temp_F;
    }

    public void setTemp_F(String temp_F) {
        this.temp_F = temp_F;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public WeatherDesc getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(WeatherDesc weatherDesc) {
        this.weatherDesc = weatherDesc;
    }
>>>>>>> 25c899d4b1be06be1ddfe2438cdf23da372cd52b
}
