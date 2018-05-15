package com.books.sam.vr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Add a member variable for tv

    private TextView forecast;

    private WeatherEntries weatherEntries = new WeatherEntries();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        forecast = findViewById(R.id.tv_weather_data);


       for (String weather: weatherEntries.getWeatherEntries()){
           forecast.append(weather + "\n\n");
       }



    }

}
