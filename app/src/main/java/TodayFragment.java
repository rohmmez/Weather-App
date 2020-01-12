package com.example.weatherapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TodayFragment extends Fragment
{
    private Information information;
    private TextView card1_tv2;
    private TextView card2_tv2;
    private TextView card3_tv2;
    private TextView card4_tv2;
    private TextView card5_tv1;
    private TextView card5_tv2;
    private TextView card6_tv2;
    private TextView card7_tv2;
    private TextView card8_tv2;
    private TextView card9_tv2;

    public TodayFragment(Information information)
    {
        this.information = information;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.details_today, container, false);

        card1_tv2 = view.findViewById(R.id.details_card1_tv2);
        card2_tv2 = view.findViewById(R.id.details_card2_tv2);
        card3_tv2 = view.findViewById(R.id.details_card3_tv2);
        card4_tv2 = view.findViewById(R.id.details_card4_tv2);
        card5_tv1 = view.findViewById(R.id.details_card5_tv1);
        card5_tv2 = view.findViewById(R.id.details_card5_tv2);
        card6_tv2 = view.findViewById(R.id.details_card6_tv2);
        card7_tv2 = view.findViewById(R.id.details_card7_tv2);
        card8_tv2 = view.findViewById(R.id.details_card8_tv2);
        card9_tv2 = view.findViewById(R.id.details_card9_tv2);

        card1_tv2.setText(information.windSpeed);
        card2_tv2.setText(information.pressure);
        card3_tv2.setText(information.precipIntensity);
        card4_tv2.setText(information.temperature);
        card5_tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(information.card1Icon), null);
        card5_tv2.setText(information.summary);
        card6_tv2.setText(information.humidity);
        card7_tv2.setText(information.visibility);
        card8_tv2.setText(information.cloudCover);
        card9_tv2.setText(information.ozone);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
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
