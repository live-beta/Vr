package com.books.sam.vr;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sam on 5/21/18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

private String [] mweatherData;

private final ForecastAdapterOnClickHandler mClickHandler;

public  interface ForecastAdapterOnClickHandler{
    void onClick(String weatherForDay);
}

public ForecastAdapter(ForecastAdapterOnClickHandler clickHandler) {
    mClickHandler = clickHandler;

}

public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    public final TextView mwWeatherTextView;

    public ForecastAdapterViewHolder(View view){
        super(view);
        mwWeatherTextView = view.findViewById(R.id.tv_weather_data);
        view.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        int adapterPosition = getAdapterPosition();
        String weatherForDay = mweatherData[adapterPosition];
        mClickHandler.onClick(weatherForDay);
    }

}
@Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){

    // called when the recyclerview instanciated a new viewholder instance

    Context context = viewGroup.getContext();
    int layoutIdForListItem = R.layout.forecast_list_item;
    LayoutInflater inflater = LayoutInflater.from(context);
    boolean shouldAttachToParentImmediately= false;

    View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
    return new ForecastAdapterViewHolder(view);
}

@Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position){

        // Binding the view with data
        String weatherForThisDay = mweatherData[position];
        forecastAdapterViewHolder.mwWeatherTextView.setText(weatherForThisDay);
}

@Override
    public int getItemCount() {

        // determined the length of the data that is being received and consequently, the number of
    // objects to invoke.

        if (null == mweatherData) return 0;
        return mweatherData.length;
}

public void setWeatherData(String[] weatherData){
        mweatherData = weatherData;
        notifyDataSetChanged();
}
}

