package com.books.sam.vr.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.books.sam.vr.utilities.VtDateUtils;
import com.books.sam.vr.utilities.VtWeatherUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by sam on 5/17/18.
 */

public final class OpenWeatherJsonUtils {

    public static String[] getSimpleWeatherStringFromJson(Context context, String forecastJsonStr)
    throws JSONException{
        final String OWM_LIST = "list";

        final String OWM_TEMPERATURE = "temp";

        final String OWM_MAX= "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";
        final String OWM_MESSAGE_CODE ="cod";

        String [] parseWeatherData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_MESSAGE_CODE)){
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode){
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }
        JSONArray  weatherArray = forecastJson.getJSONArray(OWM_LIST);

        parseWeatherData = new String[weatherArray.length()];

        long localDate = System.currentTimeMillis();
        long utcDate = VtDateUtils.getUTCDateFromLocal(localDate);
        long startDay = VtDateUtils.normalizeDate(utcDate);

        // parsing through the weather data

        for (int i = 0; i < weatherArray.length(); i++){
            String date;
            String highAndLow;

            long dateTimeMillis;
            double high;
            double low;
            String description;
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            dateTimeMillis = startDay + VtDateUtils.DAYS_IN_MILLIS * i;
            date = VtDateUtils.getFriendlyDateString(context,dateTimeMillis, false);

            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAndLow  = VtWeatherUtil.formatHighLows(context, high, low);

            parseWeatherData[i] = date + " - " + description + " - " + highAndLow;

        }
        return parseWeatherData;


    }
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr){
        return null;
    }
}
