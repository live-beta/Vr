package com.books.sam.vr;

import android.content.Context;
import android.text.format.DateUtils;

import com.books.sam.vr.R;
import java.text.SimpleDateFormat;
import java.util.TimeZone;



/**
 * Created by sam on 5/17/18.
 */

public final class VtDateUtils {

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS *60;
    public static final long HOUR_IN_MILLS = MINUTE_IN_MILLIS *60;
    public static final long DAYS_IN_MILLIS = HOUR_IN_MILLS * 24;

    public static long getDayNumber(long date) {

        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(date);
        return(date+ gmtOffset)/ DAYS_IN_MILLIS;
    }


    public static long normalizeDate(long date){
        long retValNew = date / DAYS_IN_MILLIS * DAYS_IN_MILLIS;
        return retValNew;
    }
    public static long getLocalDateFromUTC(long utcDate){
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(utcDate);
        return utcDate-gmtOffset;
    }

    public static long getUTCDateFromLocal(long localDate){
        TimeZone  tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(localDate);
        return localDate+gmtOffset;
    }

    public static String getFriendlyDateString(Context context , long dateInMillis, boolean showFullDate){
        long localDate = getLocalDateFromUTC(dateInMillis);
        long dayNumber = getDayNumber(localDate);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());

        if (dayNumber == currentDayNumber || showFullDate) {
            String dayName = getDayName(context, localDate);
            String readableDate = getReadableDateString(context, localDate);

            if (dayNumber - currentDayNumber < 2 ){

                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName,dayName);
            }else {
                return readableDate;
            }
            }else if (dayNumber < currentDayNumber + 7){
                return getDayName(context, localDate);
            }else {
                int flags = DateUtils.FORMAT_SHOW_DATE
            | DateUtils.FORMAT_NO_YEAR
                        | DateUtils.FORMAT_ABBREV_ALL
                        | DateUtils.FORMAT_SHOW_WEEKDAY;
                return DateUtils.formatDateTime(context, localDate, flags);
            }
        }

        public static String getReadableDateString(Context context, long timeInMills){
        int flags = DateUtils.FORMAT_SHOW_DATE
            |DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

        return DateUtils.formatDateTime(context, timeInMills, flags);

        }

        private static String getDayName(Context context, long dateInMills){
            long dayNumber = getDayNumber(System.currentTimeMillis());
            long currentDatNumber = getDayNumber(System.currentTimeMillis());
            if(dayNumber == currentDatNumber){
                return context.getString(R.string.today);
            }else if (dayNumber == currentDatNumber + 1) {
                return context.getString(R.string.tomorrow);
            }else {

                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMills);
            }
        }

    }

