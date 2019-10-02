package com.equipo.superttapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormater {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static Date convertStringToDate(String dateToConvert) {
        Date date = null;
        try {
            date = format.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDateToString(Date dateToConvert) {
        return format.format(dateToConvert);
    }
}
