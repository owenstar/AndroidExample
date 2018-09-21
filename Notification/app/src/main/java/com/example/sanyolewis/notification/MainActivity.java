package com.example.sanyolewis.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = this.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String id ="channel_1";//channel的id
                String description = "123";//channel的描述信息
                int importance = NotificationManager.IMPORTANCE_LOW;//channel的重要性
                NotificationChannel channel = new NotificationChannel(id, "123", importance);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this,id);
                mBuilder.setContentTitle("i am title")
                        .setContentText("i am content")
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("i am test string")
                        .setDefaults(Notification.DEFAULT_SOUND);
                manager.notify(10,mBuilder.build());
            }
        });
    }
}
