package com.example.sanyolewis.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabs = this.findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("this is 1st tab");
        spec.setContent(R.id.View1);
        spec.setIndicator("Move1");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("this is 2st tab");
        spec.setContent(R.id.View2);
        spec.setIndicator("Move2");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("this is 3st tab");
        spec.setContent(R.id.View3);
        spec.setIndicator("Mov3");
        tabs.addTab(spec);

        setTitle("Online ");

        tabs.setCurrentTab(0);

        final ProgressBar bar = this.findViewById(R.id.progress);
        RatingBar rateBar = this.findViewById(R.id.ratBar);
        rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                  bar.setProgress((int)rating);
            }
        });
        SeekBar seekBar = this.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
