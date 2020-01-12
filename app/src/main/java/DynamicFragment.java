package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;


import static android.content.Context.MODE_PRIVATE;

public class DynamicFragment extends Fragment
{

    public static final String SHARED_PREFS = "sharedPrefs";


    private Handler handler;


    private TextView card1Tv1;
    private TextView card1Tv2;
    private TextView card1Tv3;
    private TextView card1Tv4;
    private TextView card2Tv1;
    private TextView card2Tv2;
    private TextView card2Tv3;
    private TextView card2Tv4;

    private ListView card3lv;

    private FloatingActionButton fab;

    private String id;

    private Information information;

    public DynamicFragment(int position)
    {
        this.id = position + "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        final View view = inflater.inflate(R.layout.dynamic_fragment, container, false);

        card3lv = view.findViewById(R.id.dynamic_card3_lv);

        card1Tv1 = view.findViewById(R.id.dynamic_card1_tv1);
        card1Tv2 = view.findViewById(R.id.dynamic_card1_tv2);
        card1Tv3 = view.findViewById(R.id.dynamic_card1_tv3);
        card1Tv4 = view.findViewById(R.id.dynamic_card1_tv4);
        card2Tv1 = view.findViewById(R.id.dynamic_card2_tv1);
        card2Tv2 = view.findViewById(R.id.dynamic_card2_tv2);
        card2Tv3 = view.findViewById(R.id.dynamic_card2_tv3);
        card2Tv4 = view.findViewById(R.id.dynamic_card2_tv4);

        fab = view.findViewById(R.id.dynamic_fab);

        new Thread()
        {
            @Override
            public void run()
            {
                super.run();
                try
                {
                    information = loadData(id);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                }
                catch (Exception e)
                {
                    Log.e("SimpleWeather", "One or more fields not found in the JSON data");
                }
            }
        }.start();

        handler = new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 1:
                        card1Tv1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(information.card1Icon), null);
                        card1Tv2.setText(information.temperature);
                        card1Tv3.setText(information.summary);
                        card1Tv4.setText(information.cityName);
                        card2Tv1.setText(information.humidity);
                        card2Tv2.setText(information.windSpeed);
                        card2Tv3.setText(information.visibility);
                        card2Tv4.setText(information.pressure);
                        card3lv.setAdapter(new MyListAdapter(getActivity(), information));
                        break;
                }
            }
        };

        card1Tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(),information.cityName + " was removed from the favorites", Toast.LENGTH_LONG).show();
                updateData();
            }
        });

        return view;
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

    private void updateData()
    {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int num = sharedPreferences.getInt("count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("count", num - 1);
        String id = (num - 1) + "";
        editor.putString(id, "");

        editor.apply();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.initViews(num - 1);
    }

    private Information loadData(String id)
    {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String information_json = sharedPreferences.getString(id, "");
        information =  gson.fromJson(information_json, Information.class);
        return information;
    }
}
