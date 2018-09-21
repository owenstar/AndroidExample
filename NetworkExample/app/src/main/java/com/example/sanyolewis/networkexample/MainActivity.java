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
import java.io.DataOutputStream;
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
        Button btn = this.findViewById(R.id.get);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //子线程 网络请求
                Thread t = new Thread(){
                    @Override
                    public void run() {
                        requestGetService();
                    }
                };
                t.start();
            }
        });

        Button btn1 = this.findViewById(R.id.post);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        requestPostService();
                    }
                };
                thread.start();
            }
        });
    }

    private  void requestGetService() {
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
            msg.obj = "Get" + sb.toString();
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

    private  void requestPostService(){
        String urlString = "http://www.51work6.com/service/mynotes/WebService.php";
        String parameterString = String.format("email=%s&type=%s&action=%s","wjy_ios@163.com","JSON","query");
        BufferedReader br = null;
        HttpURLConnection con = null;
        try {
            URL requestUrl = new URL(urlString);
            con = (HttpURLConnection) requestUrl.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            //设置参数
            DataOutputStream stream = new DataOutputStream(con.getOutputStream());
            stream.writeBytes(parameterString);
            stream.close();

            //打开网络输入流
            InputStream is = con.getInputStream();
            //通过is对象创建InputStreamReader对象
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            //通过isr对象创建BufferedReader对象
            br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.i("777777",sb.toString());
            Message msg = new Message();
            msg.obj = "Post" + sb.toString();
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
