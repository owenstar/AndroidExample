package com.example.sanyolewis.nsthread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tx ;
    private  boolean isrunning = true;
    private  Thread thread;
    private int mTimer;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = this.findViewById(R.id.btn);
        tx = this.findViewById(R.id.textview);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isrunning = false;
            }
        });

        thread = new Thread(){
            @Override
            public void run() {
                while (isrunning){
                    try {
                        Thread.currentThread().sleep(1000);
                        mTimer++;
                        Log.i("itime","-----------------"+mTimer);

//                        tx.setText("currentNum:" + mTimer);
                        Message msg = new Message();
                        msg.obj = mTimer;
                        msg.what = 0;
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Log.i("thread", String.valueOf(thread));
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        tx.setText(msg.obj + "");
                        break;
                }
            }
        };
        thread.start();


        /**
         * 服务
         */
        Button startServiceBtn = this.findViewById(R.id.startService);
        startServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(MainActivity.this,MyService.class);
                startService(serviceIntent);
            }
        });

        Button stopServiceBtn = this.findViewById(R.id.stopService);
        stopServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyService.class);
                stopService(intent);
            }
        });

        /**
         * 广播
         */

        Button broadcast = this.findViewById(R.id.broadcastReciever);
        broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("nihao");
                sendBroadcast(intent);
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(mReciever,filter);

    }

    private BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("jajha","进后台");
            Toast.makeText(context,"您的nebula广播接受地收到了广播",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciever);
    }
}
