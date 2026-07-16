package com.example.earthquake_androidapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.earthquake_androidapp.R;
import com.example.earthquake_androidapp.api.EarthquakeAPI;
import com.example.earthquake_androidapp.repository.EarthquakeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 地震情報を監視するためのサービス
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class EarthquakeMonitorService extends Service {

    private static final String NOTIFY_ID = "MyNotify";
    private LocalDateTime beforeGetTime = LocalDateTime.MIN;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final EarthquakeRepository repository = EarthquakeRepository.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFY_ID, "地震の通知", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);

        startForegroundService();
        startEarthquakeMonitor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scheduler.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startEarthquakeMonitor() {
        scheduler.scheduleWithFixedDelay(() -> {
            repository.fetchEarthquakes(new EarthquakeRepository.Callback<>() {
                @Override
                public void onResult(List<EarthquakeAPI> result) {
                    if (result == null || result.isEmpty()) return;

                    // 最新の地震情報を取得
                    EarthquakeAPI latestApi = result.get(0);
                    EarthquakeAPI.Earthquake earthquake = latestApi.getEarthquake();

                    DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                            .appendPattern("yyyy/MM/dd HH:mm:ss")
                            .optionalStart()
                            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 3, true)
                            .optionalEnd()
                            .toFormatter();

                    LocalDateTime latestTime = LocalDateTime.parse(earthquake.getRawTime(), timeFormatter);

                    if (beforeGetTime.isBefore(latestTime)) {
                        if (beforeGetTime != LocalDateTime.MIN) {
                            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            var notification = new NotificationCompat.Builder(getApplicationContext(), NOTIFY_ID)
                                    .setContentTitle("地震情報")
                                    .setContentText("新しい地震が発生しました")
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .build();
                            notificationManager.notify(100, notification);
                        }
                        beforeGetTime = latestTime;
                    }
                }

                @Override
                public void onError(Exception e) {
                }
            });
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void startForegroundService() {
        var notification = new NotificationCompat.Builder(this, NOTIFY_ID)
                .setContentTitle("地震API")
                .setContentText("バックグラウンドで監視しています")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build();
        startForeground(1, notification);
    }
}
