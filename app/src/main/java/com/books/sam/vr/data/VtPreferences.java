package com.books.sam.vr.data;

/**
 * Created by sam on 5/17/18.
 */

import android.content.Context;

public class VtPreferences {

    public static final String PREF_CITY_NAME = "city_name";
    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String DEFAULT_WEATHER_LOCATION = "94043,USA";
    private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284, 122.0724};

    private static final  String DEFAULT_MAP_LOCATION = "1600 Amphitheatre Parkway, Mountain View, CA 94043";

    static public void setLocation(Context c, String locationSetting, double lat, double lon){

    }

    static public void resetLocationCoordinates(Context c) {

    }
    public static String getPrefferedWeatherLocation(Context context){
        return getDefaultWeatherLocation();
    }
    public static boolean isMetric(Context context){
        return true;
    }
    public static double[] getLocationCoordinates(Context context){
        return getDefaultWeatherCoordinates();
    }
    public static boolean isLocationLatLonAvailable(Context context){
        return false;
    }
    public static String getDefaultWeatherLocation(){
        return DEFAULT_WEATHER_LOCATION;
    }
    public static double[] getDefaultWeatherCoordinates(){
        return DEFAULT_WEATHER_COORDINATES;
    }
}
