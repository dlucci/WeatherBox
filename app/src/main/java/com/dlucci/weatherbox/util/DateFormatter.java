package com.dlucci.weatherbox.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by derlucci on 5/19/15.
 */
public class DateFormatter {

    /**
     * I created this class because I was not happy with the options for
     * converting a date string into human readable text.  This should serve
     * the needs for this app (and maybe other projects)
     */

    private static final String TAG = "DateFormatter";

    public static String getToday(String date){
        String[] dates = date.split("-");

        int year = new Integer(dates[0]).intValue();
        /**
         * The reason one is being subtracted from the month variable is because the
         * Calendar library zero indexes months.  So, we need to subtract one from the current
         * month so Calendar will use the correct month
         */
        int month = (new Integer(dates[1]).intValue()) - 1;
        int day = new Integer(dates[2]).intValue();

        Log.d(TAG, "Month is " + month);
        Log.d(TAG, "Day is " + day);
        Log.d(TAG, "Year is " + year);

        Date help = new Date(year, month, day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(help);

        return getDay(calendar.get(Calendar.DAY_OF_WEEK)) + " " + (++month) + "/" + day;
    }

    private static String getDay(int day) {
        String retVal = null;
        switch (day-1){
            case Calendar.SUNDAY:
                retVal = "Sunday";
                break;
            case Calendar.MONDAY:
                retVal = "Monday";
                break;
            case Calendar.TUESDAY:
                retVal = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                retVal = "Wednesday";
                break;
            case Calendar.THURSDAY:
                retVal = "Thursday";
                break;
            case Calendar.FRIDAY:
                retVal = "Friday";
                break;
            case Calendar.SATURDAY:
                retVal = "Saturday";
                break;
        }

        return retVal;
    }
}
