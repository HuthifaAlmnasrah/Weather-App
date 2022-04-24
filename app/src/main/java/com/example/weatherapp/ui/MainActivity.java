package com.example.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.weatherapp.R;
import com.example.weatherapp.utilites.NetworkChangeListiner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AutoCompleteTextView autoCompleteTextView;
    private FloatingActionButton settingsFab;

    private SharedPreferences DarkModePreference;
    private NetworkChangeListiner networkChangeListiner = new NetworkChangeListiner();

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiner, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiner);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applayTheme();
        setContentView(R.layout.activity_main);

        ArrayList<String> citiesNames = new ArrayList<>();
        citiesNames.add("my current location");
        citiesNames.add("london");
        citiesNames.add("New York");
        citiesNames.add("Barcelona");

        recyclerView = findViewById(R.id.recycler);
        CitiesAdapter adapter = new CitiesAdapter(this);
        adapter.setList(citiesNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> cities = new ArrayList<>();
        cities.add("Amman");
        cities.add("Zarqa");
        cities.add("Irbid");
        cities.add("Baghdad");
        cities.add("cairo");
        cities.add("Alexandria");
        cities.add("Riyadh");
        cities.add("Jeddah");
        cities.add("Mecca");
        cities.add("Istanbul");
        cities.add("Ankara");
        cities.add("Dubai");
        cities.add("Aden");
        cities.add("chicago");
        cities.add("paris");

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, cities);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                citiesNames.add(item);
                adapter.setList(citiesNames);
                cities.remove(i);
            }
        });

        settingsFab = findViewById(R.id.settings_fab);
        settingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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