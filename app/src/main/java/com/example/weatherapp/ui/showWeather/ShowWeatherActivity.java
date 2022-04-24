package com.example.weatherapp.ui.showWeather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.weather.Current_condition;
import com.example.weatherapp.model.weather.Weather;
import com.example.weatherapp.model.weather.WeatherModel;
import com.example.weatherapp.ui.DaysAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowWeatherActivity extends AppCompatActivity {

    private WeatherViewModel viewModel;

    private TextView city, currentDate, currentTemp, status, showGraphical;
    private CircleImageView imageView;
    private LottieAnimationView animationView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;

    private SharedPreferences DarkModePreference;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applayTheme();
        setContentView(R.layout.activity_show_weather);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");

        initialization();
        initData();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                refresh.setRefreshing(false);
            }
        });
        showGraphical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowWeatherActivity.this, GraphicalRepresentationActivity.class).putExtra("cityName", cityName));
            }
        });
    }

    private void initialization() {
        currentTemp = findViewById(R.id.current_tempreture);
        city = findViewById(R.id.city_name);
        currentDate = findViewById(R.id.current_date);
        status = findViewById(R.id.status);
        showGraphical = findViewById(R.id.graphical);
        imageView = findViewById(R.id.img);
        animationView = findViewById(R.id.animationView);
        recyclerView = findViewById(R.id.recycler);
        refresh = findViewById(R.id.refresh);
    }
    private void initData(){

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);

        viewModel.getWeather(cityName);

        viewModel.weatherMutableLiveData.observe(this, new Observer<WeatherModel>() {
            @Override
            public void onChanged(WeatherModel weatherModel) {
                List<Weather> weathers = weatherModel.getData().getWeather();
                List<Current_condition> current_conditions = weatherModel.getData().getCurrent_condition();

                DaysAdapter adapter = new DaysAdapter(ShowWeatherActivity.this);
                adapter.setList(weathers);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShowWeatherActivity.this));

                city.setText(weatherModel.getData().getRequest().get(0).getQuery());
                currentTemp.setText(current_conditions.get(0).getTemp_C()+" \u2103");
                currentDate.setText(current_conditions.get(0).getObservation_time());
                status.setText(current_conditions.get(0).getWeatherDesc().get(0).getValue());
                Glide
                        .with(ShowWeatherActivity.this)
                        .load(weatherModel.getData().getCurrent_condition().get(0).getWeatherIconUrl().get(0).getValue())
                        .centerCrop()
                        .into(imageView);
                animationView.setVisibility(View.GONE);
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