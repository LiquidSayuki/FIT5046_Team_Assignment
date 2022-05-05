package com.teamliquid.volksfitness;

import com.teamliquid.volksfitness.fragment.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather?")
    Call<CurrentWeather> getWeather(@Query("lat") String lat,
                                    @Query("lon") String lon,
                                    @Query("appid") String key);
}
