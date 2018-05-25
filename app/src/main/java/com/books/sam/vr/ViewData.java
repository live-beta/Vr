package com.books.sam.vr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by sam on 5/21/18.
 */

public class ViewData extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = "#VÆR";

    private String mForecast;
    private TextView wWeatherDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_view);
        wWeatherDisplay = findViewById(R.id.tv_display_weather);

        Intent intentViewIntent = getIntent();

        if (intentViewIntent  != null){
            if(intentViewIntent.hasExtra(Intent.EXTRA_TEXT)){
                mForecast = intentViewIntent.getStringExtra(Intent.EXTRA_TEXT);
                wWeatherDisplay.setText(mForecast);
            }
        }



    }
}
