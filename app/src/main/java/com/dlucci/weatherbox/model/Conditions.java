package com.dlucci.weatherbox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/21/15.
 */
public class Conditions implements Serializable {

    @SerializedName("cloudcover")
    public String cloudCover;
    @SerializedName("FeelsLikeC")
    public String feelsLikeC;
    @SerializedName("FeelsLikeF")
    public String feelsLikeF;
    public String humidity;
    public String precipMM;
    public String pressure;
    public String visibility;
    public String weatherCode;
    public ArrayList<WeatherDesc> weatherDesc;
    public ArrayList<WeatherDesc> weatherIconUrl;
    public String winddir16Point;
    public String winddirDegree;


}
