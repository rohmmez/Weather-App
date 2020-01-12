package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment
{

    public static final String SHARED_PREFS = "sharedPrefs";
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

    private Information information;



    private LocationManager locationManager;
    private String locationProvider;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        information = new Information();
        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        card3lv = view.findViewById(R.id.card3_lv);

        card1Tv1 = view.findViewById(R.id.card1_tv1);
        card1Tv2 = view.findViewById(R.id.card1_tv2);
        card1Tv3 = view.findViewById(R.id.card1_tv3);
        card1Tv4 = view.findViewById(R.id.card1_tv4);
        card2Tv1 = view.findViewById(R.id.card2_tv1);
        card2Tv2 = view.findViewById(R.id.card2_tv2);
        card2Tv3 = view.findViewById(R.id.card2_tv3);
        card2Tv4 = view.findViewById(R.id.card2_tv4);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{ACCESS_FINE_LOCATION}, 1);
        }


        List<String> providerList = locationManager.getProviders(true);

        if (providerList.contains(LocationManager.GPS_PROVIDER))
        {
            locationProvider = LocationManager.GPS_PROVIDER;
            locationManager.requestLocationUpdates(locationProvider, Integer.MAX_VALUE, 0, mLocationListener);
        }
        else if (providerList.contains(LocationManager.NETWORK_PROVIDER))
        {
            locationProvider = LocationManager.NETWORK_PROVIDER;
            locationManager.requestLocationUpdates(locationProvider, Integer.MAX_VALUE, 0, mLocationListener);
        }
        else if (providerList.contains(LocationManager.PASSIVE_PROVIDER))
        {
            locationProvider = LocationManager.PASSIVE_PROVIDER;
            locationManager.requestLocationUpdates(locationProvider, Integer.MAX_VALUE, 0, mLocationListener);
        }
        else
        {
            Toast.makeText(getActivity(), "No sites to use", Toast.LENGTH_SHORT).show();
        }


        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location!=null)
        {
            updateToNewLocation(location);
        }

        new Thread()
        {
            @Override
            public void run()
            {
                super.run();
                try
                {
                    MyJSON_google myJSON_google = new MyJSON_google();
                    String json_google = myJSON_google.loadJson(loc);
                    JSONObject jsonObject_google = new JSONObject(json_google);
                    JSONArray jsonArray_google = jsonObject_google.getJSONArray("results").getJSONObject(0).getJSONArray("address_components");
                    String locName = "";
                    for (int i = 0; i < jsonArray_google.length(); i++)
                    {
                        if (jsonArray_google.getJSONObject(i).getJSONArray("types").getString(0).equals("locality"))
                        {
                            locName = jsonArray_google.getJSONObject(i).getString("long_name");
                            break;
                        }
                    }
                    String json_google2 = myJSON_google.loadJson(locName);
                    JSONObject jsonObject_google2 = new JSONObject(json_google2);
                    String locationName = jsonObject_google2.getJSONArray("results").getJSONObject(0).getString("formatted_address");

                    MyJSON myJSON = new MyJSON();
                    String json = myJSON.loadJson(loc);
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonObject_currently = jsonObject.getJSONObject("currently");
                    JSONObject jsonObject_daily = jsonObject.getJSONObject("daily");
                    JSONArray jsonArray = jsonObject.getJSONObject("daily").getJSONArray("data");
                    String str = jsonObject_currently.getString("temperature");
                    information.summary_daily = jsonObject_daily.getString("summary");
                    information.icon_daily = jsonObject_daily.getString("icon");
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
                        updateData();
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

        return view;
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public final LocationListener mLocationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location) {
            updateToNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void updateToNewLocation(Location location)
    {
        double lat;
        double lng;

        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();;
            loc = lat + "," + lng;
        }
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        information.id = "0";
        Gson gson = new Gson();
        String information_json = gson.toJson(information);
        String id = "0";
        editor.putString(id, information_json);
        editor.apply();
    }
}
