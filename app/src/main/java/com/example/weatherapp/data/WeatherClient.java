package com.example.weatherapp.data;

import com.example.weatherapp.model.weather.WeatherModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    //  http://api.worldweatheronline.com/premium/v1/weather.ashx?key=d613c8d70f8249e08a7223717222004&q=London&format=json&num_of_days=1
    private static final String BASE_URL = "https://api.worldweatheronline.com/premium/v1/";
    private ApiInterface apiInterface;
    private static WeatherClient INSTANSE;

    public WeatherClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static WeatherClient getINSTANSE() {
        if (INSTANSE == null)
            INSTANSE = new WeatherClient();
        return INSTANSE;
    }
    public Call<WeatherModel> getWeather(String cityName) {
        return apiInterface.getWeather(cityName);
    }
}