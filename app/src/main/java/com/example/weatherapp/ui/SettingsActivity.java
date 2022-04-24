package com.example.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import com.example.weatherapp.R;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsActivity extends AppCompatActivity {

    private Switch aSwitch;
    private SharedPreferences DarkModePreference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applayTheme();
        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.btn_switch);

        checkCurrentTheme();

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editor.putBoolean("DARK_MODE", true).commit();
                recreate();
            } else {
                editor.putBoolean("DARK_MODE", false).commit();
                recreate();
            }
        });
    }
    public void checkCurrentTheme() {
        Boolean isDark = DarkModePreference.getBoolean("DARK_MODE", false);
        aSwitch.setChecked(isDark);
    }
    private void applayTheme() {
        DarkModePreference = getSharedPreferences("DARK_MODE_PREFERENCE", Context.MODE_PRIVATE);
        editor = DarkModePreference.edit();

        if (DarkModePreference.getBoolean("DARK_MODE", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}