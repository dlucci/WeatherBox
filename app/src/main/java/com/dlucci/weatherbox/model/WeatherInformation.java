package com.dlucci.weatherbox.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by derlucci on 5/5/15.
 */
public class WeatherInformation implements Serializable {

    public List<CurrentCondition> current_condition;
    public List<Request> request;
    public List<Weather> weather;
}
