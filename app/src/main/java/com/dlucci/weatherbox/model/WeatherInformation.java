package com.dlucci.weatherbox.model;

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
}
