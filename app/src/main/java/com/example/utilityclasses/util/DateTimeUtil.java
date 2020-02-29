package com.example.utilityclasses.util;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    /**
     * Converts the String date to Date object
     * the given String will be converted in "MM/dd/yyyy hh:mm:ss a" this form
     * @param strDate - pass the String date
     * @return this function returns Date object
     */
    public static Date StringToDate(String strDate) {
        try {
            if (strDate != null) {
                return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH).parse(strDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts the Date into String format
     * the given Date will be converted in "MM/dd/yyyy hh:mm:ss a" this form
     * @param date pass Date date as a parameter to this method
     * @return this function return String Object
     */
    public static String DateToString(Date date) {
        if (date != null) {
            return new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH).format(date);
        }
        return null;
    }

    /**
     * This method converts the Given Date date into Local date format
     * @param context needs a context
     * @param date to be converted into Local date
     * @return String form of the given Date
     */
    public static String DateToStringLocale(Context context, Date date) {
        return DateUtils.formatDateTime(context, date.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR| DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * This method converts the Given String strDate into Local date format
     * @param context needs a context
     * @param strDate to be converted into Local Date
     * @return String form of the given String-Date
     */
    public static String StringToLocaleString(Context context, String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy hh:mm:ss a",Locale.ENGLISH);
        Date tempDate;
        try {
            tempDate = sdf.parse(strDate);
            if(tempDate != null) {
                return DateUtils.formatDateTime(context, tempDate.getTime(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
