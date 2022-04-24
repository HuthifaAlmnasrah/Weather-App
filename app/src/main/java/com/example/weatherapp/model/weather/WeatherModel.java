package com.example.weatherapp.model.weather;

import com.example.weatherapp.model.weather.Data;

public class WeatherModel {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getAlget(){
        return data.getWeather().get(0).getDate();
    }

    @Override public String toString() {
        return data.toString();
    }
}