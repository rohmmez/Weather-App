package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity
{


    public static final String SHARED_PREFS = "sharedPrefs";
    private String query;

    private String loc;


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

    private Information information;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        query = getIntent().getStringExtra("name");



        information = new Information();
        card3lv = findViewById(R.id.search_card3_lv);
        card1Tv1 = findViewById(R.id.search_card1_tv1);
        card1Tv2 = findViewById(R.id.search_card1_tv2);
        card1Tv3 = findViewById(R.id.search_card1_tv3);
        card1Tv4 = findViewById(R.id.search_card1_tv4);
        card2Tv1 = findViewById(R.id.search_card2_tv1);
        card2Tv2 = findViewById(R.id.search_card2_tv2);
        card2Tv3 = findViewById(R.id.search_card2_tv3);
        card2Tv4 = findViewById(R.id.search_card2_tv4);

        fab = findViewById(R.id.search_fab);




        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    super.run();
                    MyJSON_google myJSON_google = new MyJSON_google();
                    String json_google = myJSON_google.loadJson(query);
                    JSONObject jsonObject_google = new JSONObject(json_google);
                    String lat = jsonObject_google.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String lng = jsonObject_google.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    loc = lat + "," + lng;
                    String locationName = jsonObject_google.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    MyJSON myJSON = new MyJSON();
                    String json = myJSON.loadJson(loc);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonObject_currently = jsonObject.getJSONObject("currently");
                    JSONObject jsonObject_daily = jsonObject.getJSONObject("daily");
                    JSONArray jsonArray = jsonObject.getJSONObject("daily").getJSONArray("data");
                    information.summary_daily = jsonObject_daily.getString("summary");
                    information.icon_daily = jsonObject_daily.getString("icon");
                    String str = jsonObject_currently.getString("temperature");
                    int j = 0;
                    int ans = 0;
                    while (j < str.length())
                    {
                        if (str.charAt(j) == '.')
                        {
                            break;
                        }
                        ans = ans * 10 + str.charAt(j) - '0';
                        j++;
                    }

                    information.temperature = ans + "℉";
                    information.summary = jsonObject_currently.getString("summary");
                    information.cityName = locationName;
                    information.card1Icon = jsonObject_currently.getString("icon");
                    str = jsonObject_currently.getString("humidity");
                    ans = 0;
                    for (int i = 0; i < str.length(); i++)
                    {
                        if (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                        {
                            ans = ans * 10 + str.charAt(i) - '0';
                        }
                    }
                    information.humidity = ans + "%";
                    information.windSpeed = jsonObject_currently.getString("windSpeed") + " mph";
                    information.visibility = jsonObject_currently.getString("visibility") + " km";
                    information.pressure = jsonObject_currently.getString("pressure") + " mb";

                    information.precipIntensity = jsonObject_currently.getString("precipIntensity") + "mmph";

                    str = jsonObject_currently.getString("cloudCover");
                    ans = 0;
                    for (int i = 0; i < str.length(); i++)
                    {
                        if (str.charAt(i) >= '0' && str.charAt(i) <= '9')
                        {
                            ans = ans * 10 + str.charAt(i) - '0';
                        }
                    }
                    information.cloudCover = ans + "%";
                    information.ozone = jsonObject_currently.getString("ozone") + " DU";

                    for (int i = 0; i < 8; i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String temperatureMax = item.getString("temperatureMax");
                        String temperatureMin = item.getString("temperatureMin");
                        int k = 0;
                        int num = 0;
                        while ( k < temperatureMax.length())
                        {
                            if (temperatureMax.charAt(k) == '.')
                            {
                                break;
                            }
                            num = num * 10 + temperatureMax.charAt(k) - '0';
                            k++;
                        }
                        information.temperatureMax[i] = num + "";
                        k = 0;
                        num = 0;
                        while ( k < temperatureMin.length())
                        {
                            if (temperatureMin.charAt(k) == '.')
                            {
                                break;
                            }
                            num = num * 10 + temperatureMin.charAt(k) - '0';
                            k++;
                        }
                        information.temperatureMin[i] = num + "";
                    }

                    for (int i = 0; i < 8; i++)
                    {
                        JSONObject item = jsonArray.getJSONObject(i);
                        information.card3Icon[i] = item.getString("icon");
                    }

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

        handler = new Handler(){
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
                        card3lv.setAdapter(new MyListAdapter(SearchActivity.this, information));
                        break;
                }
            }
        };


        card1Tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        card1Tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                intent.putExtra("information", information);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateData();
                Toast.makeText(SearchActivity.this, information.cityName + " was added to the favorites", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int num = sharedPreferences.getInt("count", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("count", num + 1);
        information.id = num + "";

        Gson gson = new Gson();
        String information_json = gson.toJson(information);
        String id = num + "";
        editor.putString(id, information_json);

        editor.apply();

    }

}
