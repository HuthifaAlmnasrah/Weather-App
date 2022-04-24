package com.example.weatherapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.showWeather.ShowWeatherActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder> implements LocationListener {

    ArrayList<String> citiesNames = new ArrayList<>();
    Context context;

    LocationManager locationManager;

    ProgressDialog dialog;

    public CitiesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CitiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cityName.setText(citiesNames.get(position).toString());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.remove.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.remove.setVisibility(View.INVISIBLE);
                if (citiesNames.get(position).equalsIgnoreCase("my current location")) {
                    startProgressDialog();
                    getLocation();
                } else {
                    context.startActivity(new Intent(context, ShowWeatherActivity.class).putExtra("cityName", citiesNames.get(position)));
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context).setTitle("Are You sure to remove " + citiesNames.get(position) + " from list").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        citiesNames.remove(position);
                        notifyDataSetChanged();
                        holder.remove.setVisibility(View.INVISIBLE);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.remove.setVisibility(View.INVISIBLE);
                    }
                }).show();
            }
        });
    }

    private void startProgressDialog() {
        dialog = new ProgressDialog(context);
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
            return false;
        }
        return true;
    }

    private void checkGPSIsEnabled() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(context).setTitle("Enable GPS").setCancelable(false).setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    private void getLocation() {
        checkGPSIsEnabled();
        if (checkPermissions()) {
            try {
                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, this::onLocationChanged);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return citiesNames.size();
    }

    public void setList(ArrayList<String> citiesNames) {
        this.citiesNames = citiesNames;
        notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.get(0).getLocality() != null) {
                citiesNames.set(0, addresses.get(0).getLocality());
                dialog.dismiss();
                context.startActivity(new Intent(context, ShowWeatherActivity.class).putExtra("cityName", citiesNames.get(0)));
            } else {
                dialog.dismiss();
                citiesNames.set(0, addresses.get(0).getCountryName());
                context.startActivity(new Intent(context, ShowWeatherActivity.class).putExtra("cityName", citiesNames.get(0)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CitiesViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;
        private ImageView remove;

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
