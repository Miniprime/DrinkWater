package com.miniprime1.drink_water;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class RingtonePlayingService extends Service {
    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "default";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,  "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("물마시세용").setContentText("물마시세용").setSmallIcon(R.mipmap.ic_launcher).build();
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String getState = intent.getExtras().getString("state");

        assert getState != null;
        switch (getState) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.drinkwater300);
            mediaPlayer.start();
            this.isRunning = true;
            this.startId = 0;
        } else if (this.isRunning && startId == 0) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            this.isRunning = false;
            this.startId = 0;
        } else if (!this.isRunning && startId == 0) {
            this.isRunning = false;
            this.startId = 0;
        } else if (this.isRunning && startId == 1) {
            this.isRunning = true;
            this.startId = 1;
        } else { }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestory() 실행", "서비스 파괴");
    }
}