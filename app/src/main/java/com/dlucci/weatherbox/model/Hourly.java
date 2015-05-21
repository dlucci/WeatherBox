package com.dlucci.weatherbox.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Hourly  extends Conditions implements Serializable {
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
    public String DewPointC;
    public String DewPointF;
    public String HeatIndexC;
    public String HeatIndexF;
    public String tempC;
    public String tempF;
    public String time;
    public String WindChillC;
    public String WindChillF;
    public String WindGustKmph;
    public String WindGustMiles;
    public String windspeedKmph;
    public String windspeedMiles;
}
