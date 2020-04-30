package com.equipo.superttapp;

import com.equipo.superttapp.util.DateFormater;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class DateFormaterTest {
    @Test
    public void convertStringToDate_validString_shouldParseToDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 0, 1, 0, 0, 0);
        cal.setTimeInMillis(1577858400000L);
        Date date = cal.getTime();
        Assert.assertEquals("Probando que se obtenga un objeto date con la fecha en string",
                date.getTime(), DateFormater.convertStringToDate("01/01/2020").getTime());
    }

    @Test
    public void convertDateToString_validDate_shouldParseToString() {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 0, 1);
        Date date = cal.getTime();
        Assert.assertEquals("Probando que no se obtenga null despues de realizar un cast",
                "01/01/2020", DateFormater.convertDateToString(date));
    }

    @Test(expected = NullPointerException.class)
    public void convertDateToString_NoValidDate_shouldThrowException() {
        DateFormater.convertDateToString(null);
    }
}
