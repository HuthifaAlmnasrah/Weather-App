package com.example.weatherapp.ui.showWeather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.data.WeatherClient;
import com.example.weatherapp.model.weather.WeatherModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    MutableLiveData<WeatherModel> weatherMutableLiveData = new MutableLiveData<>();

    public void getWeather(String cityName){
        WeatherClient.getINSTANSE().getWeather(cityName)
                .enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                weatherMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });
    }
}
