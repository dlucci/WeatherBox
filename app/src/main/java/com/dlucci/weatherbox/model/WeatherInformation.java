package com.dlucci.weatherbox.model;

<<<<<<< HEAD
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
=======
/**
 * Created by derlucci on 3/13/15.
 */
public class WeatherInformation {
    private CurrentCondition current_condition;

    public CurrentCondition getCurrent_condition() {
        return current_condition;
    }

    public void setCurrent_condition(CurrentCondition current_condition) {
        android.util.Log.d("EIFLE", "installing curCondition");
        this.current_condition = current_condition;
    }
>>>>>>> 25c899d4b1be06be1ddfe2438cdf23da372cd52b
}
