package com.dlucci.weatherbox.model;

<<<<<<< HEAD
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Weather implements Serializable{
    public ArrayList<Astronomy> astronomy;
    public String date;
    public ArrayList<Hourly> hourly;
    public String maxtempC;
    public String maxtempF;
    public String mintempC;
    public String mintempF;
    public String uvIndex;
=======
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by derrillucci on 11/19/14.
 */
public class Weather {

    private List<WeatherInformation> current_condition;
    private List<WeatherInformation> request;
    private List<WeatherInformation> weather;

    @JsonProperty(value="current_condition")
    public List<WeatherInformation> getCurrent_condition() {
        return this.current_condition;
    }

    @JsonProperty(value="current_condition")
    public void setCurrent_condition(List<WeatherInformation> current_condition) {
        android.util.Log.d("EIFLE", "installing data");
        this.current_condition = current_condition;
    }

    @JsonProperty(value="request")
    public List<WeatherInformation> getRequest() {
        return request;
    }

    @JsonProperty(value="request")
    public void setRequest(List<WeatherInformation> request) {
        this.request = request;
    }

    @JsonProperty(value="weather")
    public List<WeatherInformation> getWeather() {
        return weather;
    }

    @JsonProperty(value="weather")
    public void setWeather(List<WeatherInformation> weather) {
        this.weather = weather;
    }

    //public ArrayList<WeatherInformation> data;
>>>>>>> 25c899d4b1be06be1ddfe2438cdf23da372cd52b
}
