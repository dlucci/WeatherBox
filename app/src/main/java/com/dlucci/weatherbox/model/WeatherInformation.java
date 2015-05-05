package com.dlucci.weatherbox.model;

import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.List;

/**
 * Created by derlucci on 5/5/15.
 */
@JsonRootName(value = "data")
public class WeatherInformation {

    public List<CurrentCondition> current_condition;
    public List<Request> request;
    public List<Weather> weather;
}
