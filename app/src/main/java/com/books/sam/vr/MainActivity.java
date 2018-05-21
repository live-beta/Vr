package com.books.sam.vr;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.ForecastAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = findViewById(R.id.rv_display_weather_data);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);

        mRecyclerView.setAdapter(mForecastAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadWeatherData();

    }

    private void loadWeatherData() {
        showWeatherDataView();
        String location = VtPreferences.getPrefferedWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    @Override
    public void onClick(String weatherForDay){
        Context context = this;
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT).show();
    }

    private void showWeatherDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
       // forecast.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage(){
        //forecast.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);

    }


    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);
            try {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);
                String[] simpleJsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringFromJson(MainActivity.this, jsonWeatherResponse);
                return simpleJsonWeatherData;


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (weatherData != null) {
                showWeatherDataView();

                mForecastAdapter.setWeatherData(weatherData);
            }else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
           // forecast.setText("");
            mForecastAdapter.setWeatherData(null);
            loadWeatherData();;
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}