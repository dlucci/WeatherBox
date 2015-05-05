package com.dlucci.weatherbox;

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
}
