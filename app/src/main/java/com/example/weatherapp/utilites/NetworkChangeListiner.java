package com.example.weatherapp.utilites;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.weatherapp.R;

public class NetworkChangeListiner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.checkNetWorkAvalible(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialog_layout = LayoutInflater.from(context).inflate(R.layout.no_connection_dialog, null);
            builder.setView(dialog_layout);
            TextView retry = dialog_layout.findViewById(R.id.txt_retry);
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);

            dialog.getWindow().setGravity(Gravity.CENTER);

            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onReceive(context, intent);
                }
            });
        }
    }
}
