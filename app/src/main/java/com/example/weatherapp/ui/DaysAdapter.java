package com.example.weatherapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.weather.Hourly;
import com.example.weatherapp.model.weather.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysHiewHolder> {

    List<Weather> weathers = new ArrayList<>();
    List<Hourly> hourlies = new ArrayList<>();
    Context context;
    public DaysAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DaysHiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaysHiewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_info_item, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DaysHiewHolder holder, @SuppressLint("RecyclerView") int position) {
        hourlies = weathers.get(position).getHourly();
        holder.dayName.setText(getDayName(weathers.get(position).getDate()));
        holder.dayDate.setText(weathers.get(position).getDate());
        holder.minTemp.setText(weathers.get(position).getMintempC() +" \u2103");
        holder.maxTemp.setText(weathers.get(position).getMaxtempC() +" \u2103");
        holder.hour1.setText(formatHour(hourlies.get(0).getTime()));
        holder.hour2.setText(formatHour(hourlies.get(1).getTime()));
        holder.hour3.setText(formatHour(hourlies.get(2).getTime()));
        holder.hour4.setText(formatHour(hourlies.get(3).getTime()));
        holder.hour5.setText(formatHour(hourlies.get(4).getTime()));
        holder.hour6.setText(formatHour(hourlies.get(5).getTime()));
        holder.hour7.setText(formatHour(hourlies.get(6).getTime()));
        holder.hour8.setText(formatHour(hourlies.get(7).getTime()));
        holder.temp1.setText(hourlies.get(0).getTempC()+" \u2103");
        holder.temp2.setText(hourlies.get(1).getTempC()+" \u2103");
        holder.temp3.setText(hourlies.get(2).getTempC()+" \u2103");
        holder.temp4.setText(hourlies.get(3).getTempC()+" \u2103");
        holder.temp5.setText(hourlies.get(4).getTempC()+" \u2103");
        holder.temp6.setText(hourlies.get(5).getTempC()+" \u2103");
        holder.temp7.setText(hourlies.get(6).getTempC()+" \u2103");
        holder.temp8.setText(hourlies.get(7).getTempC()+" \u2103");
        holder.status1.setText(hourlies.get(0).getWeatherDesc().get(0).getValue());
        holder.status2.setText(hourlies.get(1).getWeatherDesc().get(0).getValue());
        holder.status3.setText(hourlies.get(2).getWeatherDesc().get(0).getValue());
        holder.status4.setText(hourlies.get(3).getWeatherDesc().get(0).getValue());
        holder.status5.setText(hourlies.get(4).getWeatherDesc().get(0).getValue());
        holder.status6.setText(hourlies.get(5).getWeatherDesc().get(0).getValue());
        holder.status7.setText(hourlies.get(6).getWeatherDesc().get(0).getValue());
        holder.status8.setText(hourlies.get(7).getWeatherDesc().get(0).getValue());
        Glide
                .with(context)
                .load(hourlies.get(0).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img1);
        Glide
                .with(context)
                .load(hourlies.get(1).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img2);
        Glide
                .with(context)
                .load(hourlies.get(2).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img3);
        Glide
                .with(context)
                .load(hourlies.get(3).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img4);
        Glide
                .with(context)
                .load(hourlies.get(4).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img5);
        Glide
                .with(context)
                .load(hourlies.get(5).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img6);
        Glide
                .with(context)
                .load(hourlies.get(6).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img7);
        Glide
                .with(context)
                .load(hourlies.get(7).getWeatherIconUrl().get(0).getValue())
                .centerCrop()
                .into(holder.img8);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public void setList(List<Weather> list) {
        this.weathers = list;
        notifyDataSetChanged();
    }

    public class DaysHiewHolder extends RecyclerView.ViewHolder {
        CircleImageView img1, img2 ,img3, img4, img5, img6, img7, img8;
        TextView dayDate, dayName, minTemp, maxTemp;
        TextView hour1, hour2, hour3, hour4, hour5, hour6, hour7, hour8;
        TextView temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8;
        TextView status1, status2, status3, status4, status5, status6, status7, status8;
        public DaysHiewHolder(@NonNull View itemView) {
            super(itemView);
            dayDate = itemView.findViewById(R.id.day_date);
            dayName = itemView.findViewById(R.id.day_name);
            minTemp = itemView.findViewById(R.id.min_tem);
            maxTemp = itemView.findViewById(R.id.max_tem);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            img4 = itemView.findViewById(R.id.img4);
            img5 = itemView.findViewById(R.id.img5);
            img6 = itemView.findViewById(R.id.img6);
            img7 = itemView.findViewById(R.id.img7);
            img8 = itemView.findViewById(R.id.img8);
            hour1 = itemView.findViewById(R.id.hour1);
            hour2 = itemView.findViewById(R.id.hour2);
            hour3 = itemView.findViewById(R.id.hour3);
            hour4 = itemView.findViewById(R.id.hour4);
            hour5 = itemView.findViewById(R.id.hour5);
            hour6 = itemView.findViewById(R.id.hour6);
            hour7 = itemView.findViewById(R.id.hour7);
            hour8 = itemView.findViewById(R.id.hour8);
            temp1 = itemView.findViewById(R.id.tem1);
            temp2 = itemView.findViewById(R.id.tem2);
            temp3 = itemView.findViewById(R.id.tem3);
            temp4 = itemView.findViewById(R.id.tem4);
            temp5 = itemView.findViewById(R.id.tem5);
            temp6 = itemView.findViewById(R.id.tem6);
            temp7 = itemView.findViewById(R.id.tem7);
            temp8 = itemView.findViewById(R.id.tem8);
            status1 = itemView.findViewById(R.id.status1);
            status2 = itemView.findViewById(R.id.status2);
            status3 = itemView.findViewById(R.id.status3);
            status4 = itemView.findViewById(R.id.status4);
            status5 = itemView.findViewById(R.id.status5);
            status6 = itemView.findViewById(R.id.status6);
            status7 = itemView.findViewById(R.id.status7);
            status8 = itemView.findViewById(R.id.status8);
        }
    }
    public String formatHour(String  time) {
        switch (time){
            case "0": return "01:00 AM";
            case "300": return "03:00 AM";
            case "600":return "06:00 AM";
            case "900": return "9:00 AM";
            case "1200": return "12:00 PM";
            case "1500": return "03:00 PM";
            case "1800": return "06:00 PM";
            case "2100": return "09:00 PM";
        }
        return null;
    }
    public String  getDayName(String s){
        String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        Date data = null;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            data = format.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return days[data.getDay()];
    }
}
