package com.dlucci.weatherbox.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Hourly implements Serializable {
    public String chanceoffog;
    public String chanceoffrost;
    public String chanceofhightemp;
    public String chanceofovercast;
    public String chanceofrain;
    public String chanceofremdry;
    public String chanceofsnow;
    public String chanceofsunshine;
    public String chanceofthunder;
    public String chanceofwindy;
    public String cloudcover;
    public String DewPointC;
    public String DewPointF;
    public String FeelsLikeC;
    public String FeelsLikeF;
    public String HeatIndexC;
    public String HeatIndexF;
    public String humidity;
    public String precipMM;
    public String pressure;
    public String tempC;
    public String tempF;
    public String time;
    public String visibility;
    public String weatherCode;
    public ArrayList<WeatherDesc> weatherDesc;
    public ArrayList<WeatherDesc> weatherIconUrl;
    public String WindChillC;
    public String WindChillF;
    public String winddir16Point;
    public String winddirDegree;
    public String WindGustKmph;
    public String WindGustMiles;
    public String windspeedKmph;
    public String windspeedMiles;
}
