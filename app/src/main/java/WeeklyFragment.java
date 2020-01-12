package com.example.weatherapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Map;

public class WeeklyFragment extends Fragment {

    private Information information;
    private TextView weekly_tv;
    private LineChart mpLineChart;

    public WeeklyFragment(Information information) {
        this.information = information;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_weekly, container, false);
        weekly_tv = view.findViewById(R.id.weekly_tv);
        mpLineChart = view.findViewById(R.id.weekly_lineChart);


        LineDataSet lineDataSetMax = new LineDataSet(temMaxValue(), "Maximum Temperature");
        LineDataSet lineDataSetMin = new LineDataSet(temMinValue(), "Minimum Temperature");

        int color = ContextCompat.getColor(getActivity(), R.color.colorPurple);
        lineDataSetMin.setColor(color);
        color = ContextCompat.getColor(getActivity(), R.color.colorOrange);
        lineDataSetMax.setColor(color);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSetMin);
        dataSets.add(lineDataSetMax);


        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();

        Legend legend = mpLineChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(15f);
        legend.setXEntrySpace(10f);
        legend.setFormSize(15f);

        mpLineChart.getAxisLeft().setTextColor(Color.WHITE);
        mpLineChart.getXAxis().setTextColor(Color.WHITE);

        weekly_tv.setText(information.summary_daily);
        weekly_tv.setCompoundDrawablesWithIntrinsicBounds(getDrawable(information.icon_daily), null, null, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<Entry> temMaxValue()
    {
        ArrayList<Entry> dataValues = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            String str = information.temperatureMax[i];
            int j = 0;
            int ans = 0;
            while (j < str.length())
            {
                ans = ans * 10 + str.charAt(j) - '0';
                j++;
            }
            dataValues.add(new Entry(i, ans));
        }
        return dataValues;
    }

    private ArrayList<Entry> temMinValue()
    {
        ArrayList<Entry> dataValues = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            String str = information.temperatureMin[i];
            int j = 0;
            int ans = 0;
            while (j < str.length())
            {
                ans = ans * 10 + str.charAt(j) - '0';
                j++;
            }
            dataValues.add(new Entry(i, ans));
        }
        return dataValues;
    }


    private Drawable getDrawable(String icon)
    {
        Drawable drawableRight = null;
        if (icon.equals("partly-cloudy-night"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_night_partly_cloudy_big);
        }
        else if (icon.equals("clear-day"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_sunny_big);
        }
        else if (icon.equals("clear-night"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_night_big);
        }
        else if (icon.equals("rain"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_rainy_big);
        }
        else if (icon.equals("sleet"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_snowy_rainy_big);
        }
        else if (icon.equals("snow"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_snowy_big);
        }
        else if (icon.equals("wind"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_windy_variant_big);
        }
        else if (icon.equals("fog"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_fog_big);
        }
        else if (icon.equals("cloudy"))
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_cloudy_big);
        }
        else
        {
            drawableRight = getResources().getDrawable(R.drawable.weather_partly_cloudy_big);
        }
        return drawableRight;
    }
}