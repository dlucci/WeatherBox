package com.dlucci.weatherbox.networking;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by derlucci on 5/20/15.
 */
public interface WeatherService {

    @GET("/free/v2/weather.ashx?format=json")
    Map<String, Object> getWeather(@Query("q") String zipcode, @Query("num_of_days") String days, @Query("key") String apiKey);
}
