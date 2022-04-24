package com.example.weatherapp.data;

import com.example.weatherapp.model.weather.WeatherModel;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("weather.ashx?key=d613c8d70f8249e08a7223717222004&format=json&num_of_days=5")
    public Call<WeatherModel> getWeather(@Query("q") String cityName);
}
