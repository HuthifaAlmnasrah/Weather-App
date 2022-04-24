package com.example.weatherapp.ui.showWeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.weather.Current_condition;
import com.example.weatherapp.model.weather.Weather;
import com.example.weatherapp.model.weather.WeatherModel;
import com.example.weatherapp.ui.DaysAdapter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class GraphicalRepresentationActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;
    private SharedPreferences DarkModePreference;
    private List<Weather> weathers;
    private LineGraphSeries<DataPoint> seriesMin, seriesMax;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applayTheme();
        setContentView(R.layout.activity_graphical_representation);
        graph = (GraphView) findViewById(R.id.graph);
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        weathers = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        viewModel.getWeather(cityName);

        viewModel.weatherMutableLiveData.observe(this, new Observer<WeatherModel>() {
            @Override
            public void onChanged(WeatherModel weatherModel) {
                weathers = weatherModel.getData().getWeather();
                seriesMin = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(1, Double.parseDouble(weathers.get(0).getMintempC())),
                        new DataPoint(2, Double.parseDouble(weathers.get(1).getMintempC())),
                        new DataPoint(3, Double.parseDouble(weathers.get(2).getMintempC())),
                        new DataPoint(4, Double.parseDouble(weathers.get(3).getMintempC())),
                        new DataPoint(5, Double.parseDouble(weathers.get(4).getMintempC()))
                });
                seriesMax = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(1, Double.parseDouble(weathers.get(0).getMaxtempC())),
                        new DataPoint(2, Double.parseDouble(weathers.get(1).getMaxtempC())),
                        new DataPoint(3, Double.parseDouble(weathers.get(2).getMaxtempC())),
                        new DataPoint(4, Double.parseDouble(weathers.get(3).getMaxtempC())),
                        new DataPoint(5, Double.parseDouble(weathers.get(4).getMaxtempC()))
                });
                seriesMin.setColor(Color.BLUE);
                seriesMin.setTitle("Min Temperature");
                seriesMin.setDrawDataPoints(true);
                seriesMin.setDataPointsRadius(15);
                seriesMin.setThickness(10);

                seriesMax.setColor(Color.RED);
                seriesMax.setTitle("Min Temperature");
                seriesMax.setDrawDataPoints(true);
                seriesMax.setDataPointsRadius(15);
                seriesMax.setThickness(10);

                graph.addSeries(seriesMin);
                graph.addSeries(seriesMax);
                graph.setTitle(cityName);
                graph.setTitleTextSize(90);

                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

                GridLabelRenderer label = graph.getGridLabelRenderer();
                label.setHorizontalAxisTitle("Days");
                label.setHorizontalAxisTitleTextSize(30);
                label.setVerticalAxisTitle("Min And Max Temperature"+" \u2103");
                label.setVerticalAxisTitleTextSize(30);
            }
        });
    }

    private void applayTheme() {
        DarkModePreference = getSharedPreferences("DARK_MODE_PREFERENCE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DarkModePreference.edit();

        if (DarkModePreference.getBoolean("DARK_MODE", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}