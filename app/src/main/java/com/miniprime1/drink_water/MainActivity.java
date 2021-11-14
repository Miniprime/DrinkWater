package com.miniprime1.drink_water;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker = findViewById(R.id.time_picker);
        final Calendar calendar = Calendar.getInstance();
        final Intent my_intent = new Intent(this.context, Alarm_Reciver.class);

        Button alarm_on = findViewById(R.id.btn_start);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
                calendar.set(Calendar.SECOND, 1);
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                int second = 0;
                Toast.makeText(MainActivity.this,"물마시세용 알람 예정: " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();
                my_intent.putExtra("state","alarm on");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        Button alarm_off = findViewById(R.id.btn_stop);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"물마시세용 알람 종료",Toast.LENGTH_SHORT).show();
                alarm_manager.cancel(pendingIntent);
                my_intent.putExtra("state","alarm off");
                sendBroadcast(my_intent);
            }
        });
    }
}
