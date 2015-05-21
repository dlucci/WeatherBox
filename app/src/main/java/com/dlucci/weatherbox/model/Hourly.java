package com.dlucci.weatherbox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Hourly  extends Conditions implements Serializable {
    @SerializedName("chanceoffog")
    public String chanceOfFog;
    @SerializedName("chanceoffrost")
    public String chanceOfFrost;
    @SerializedName("chanceofhightemp")
    public String chanceOfHighTemp;
    @SerializedName("chanceofovercast")
    public String chanceOfOvercast;
    @SerializedName("chanceofrain")
    public String chanceOfRain;
    @SerializedName("chanceofremdry")
    public String chanceOfRemDry;
    @SerializedName("chanceofsnow")
    public String chanceOfSnow;
    @SerializedName("chanceofsunshine")
    public String chanceOfSunshine;
    @SerializedName("chanceofthunder")
    public String chanceOfThunder;
    @SerializedName("chanceofwindy")
    public String chanceOfWindy;
    @SerializedName("DewPointC")
    public String dewPointC;
    @SerializedName("DewPointF")
    public String dewPointF;
    @SerializedName("HeatIndexC")
    public String heatIndexC;
    @SerializedName("HeatIndexF")
    public String heatIndexF;
    public String tempC;
    public String tempF;
    public String time;
    @SerializedName("WindChillC")
    public String windChillC;
    @SerializedName("WindChillF")
    public String windChillF;
    @SerializedName("WindGustKmph")
    public String windGustKmph;
    @SerializedName("WindGustMiles")
    public String windGustMiles;
    @SerializedName("windspeedKmph")
    public String windSpeedKmph;
    @SerializedName("windspeedMiles")
    public String windSpeedMiles;
}
