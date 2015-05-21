package com.dlucci.weatherbox.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Weather implements Serializable{
    public ArrayList<Astronomy> astronomy;
    public String date;
    public ArrayList<Hourly> hourly;
    @SerializedName("maxtempC")
    public String maxTempC;
    @SerializedName("maxtempF")
    public String maxTempF;
    @SerializedName("mintempC")
    public String minTempC;
    @SerializedName("mintempF")
    public String minTempF;
    public String uvIndex;
}
