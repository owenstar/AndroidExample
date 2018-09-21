package com.example.sanyolewis.networkexample;

import android.net.UrlQuerySanitizer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = this.findViewById(R.id.txt);
        Button btn = this.findViewById(R.id.go);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //子线程 网络请求
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        requestNotes();
                    }
                };
                t.start();
            }
        });
    }

    private  void requestNotes() {
        String urlFormatString = "http://www.51work6.com/service/mynotes/WebService.php?"+"email=%s&type=%s&action=%s";
        String urlString = String.format(urlFormatString,"wjy_ios@163.com","JSON","query");
        BufferedReader br = null;
        HttpURLConnection con = null;
        try {
            URL requestUrl = new URL(urlString);
            con = (HttpURLConnection) requestUrl.openConnection();
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.i("777777",sb.toString());
            Message msg = new Message();
            msg.obj = sb.toString();
            mHander.sendMessage(msg);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (con != null){
                con.disconnect();
            }
            if(br != null){
                try{
                   br.close();
                }catch (IOException e){
                   e.printStackTrace();
                }
            }
        }

    }
    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String textString = (String) msg.obj;
            txt.setText(textString);
        }
    };
}
