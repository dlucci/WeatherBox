package com.dlucci.weatherbox.model;

import com.dlucci.weatherbox.model.Astronomy;
import com.dlucci.weatherbox.model.Hourly;

import java.util.ArrayList;

/**
 * Created by derlucci on 5/5/15.
 */
public class Weather {
    public ArrayList<Astronomy> astronomy;
    public String date;
    public ArrayList<Hourly> hourly;
    public String maxtempC;
    public String maxtempF;
    public String mintempC;
    public String mintempF;
    public String uvIndex;
}
