package com.example.sanyolewis.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button clickBtn;
    private  ProgressBar bar;
    private  final  String imageUrl = "http://img4.imgtn.bdimg.com/it/u=324439414,2579147526&fm=21&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = this.findViewById(R.id.imageview);
        clickBtn = this.findViewById(R.id.btn1);
        bar = this.findViewById(R.id.progress);
        bar.setVisibility(View.GONE);
    }

    public void click(View view){
        new ImageLoadAsyTask().execute(imageUrl);
    }

    class  ImageLoadAsyTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            URLConnection connection;
            Bitmap bitmap = null;
            InputStream is;
            try{
                connection = new URL(url).openConnection();
                Thread.sleep(300);
                is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                is.close();
                bis.close();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bar.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
        }
    }
}
