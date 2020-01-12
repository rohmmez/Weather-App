package com.example.weatherapp;

import android.graphics.drawable.Drawable;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Information implements Serializable
{
    public String id;
    public String temperature;
    public String summary;
    public String cityName;
    public String humidity;
    public String windSpeed;
    public String visibility;
    public String pressure;
    public String[] temperatureMin;
    public String[] temperatureMax;
    public String card1Icon;
    public String precipIntensity;
    public String cloudCover;
    public String ozone;
    public String summary_daily;
    public String icon_daily;
    public Date[] dates;
    public Calendar calendar;
    public String[] card3Icon;

    public Information()
    {
        id = "";
        temperature = "";
        summary = "";
        cityName = "";
        humidity = "";
        windSpeed = "";
        visibility = "";
        pressure = "";
        precipIntensity = "";
        cloudCover = "";
        ozone = "";
        card1Icon = "";
        summary_daily = "";
        icon_daily = "";
        card3Icon = new String[8];
        temperatureMin = new String[8];
        temperatureMax = new String[8];
        calendar = new GregorianCalendar();
        dates = new Date[8];
        for (int i = 0; i < 8; i++)
        {
            dates[i] = new Date();
            calendar.setTime(dates[i]);
            calendar.add(calendar.DATE, i);
            dates[i] = calendar.getTime();
        }

    }
}
