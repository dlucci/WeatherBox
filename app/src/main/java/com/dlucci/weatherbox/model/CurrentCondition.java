package com.dlucci.weatherbox.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class CurrentCondition extends Conditions {

    @SerializedName("windspeedKmph")
    public String windSpeedKmph;
    @SerializedName("windspeedMiles")
    public String windSpeedMiles;
    @SerializedName("observation_time")
    public String observationTime;
    @SerializedName("temp_C")
    public String tempC;
    @SerializedName("temp_F")
    public String tempF;
}
