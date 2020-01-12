package com.example.weatherapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoFragment extends Fragment
{

    private ListView photo_lv;
    private Information information;
    private ArrayList<String> arr;
    private Handler handler;

    public PhotoFragment(Information information)
    {
        this.information = information;
        arr = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.details_photo, container, false);
        photo_lv = view.findViewById(R.id.details_lv);


        new Thread()
        {
            @Override
            public void run()
            {
                super.run();
                try
                {
                    MyJSON_photo myJSON_photo = new MyJSON_photo();
                    String json = myJSON_photo.loadJson(information.cityName + "+pictures");
                    JSONObject jsonObject = new JSONObject(json);
                    for (int i = 0; i < 6; i++)
                    {
                        arr.add(jsonObject.getJSONArray("items").getJSONObject(i).getString("link"));
                    }
                }
                catch (Exception e)
                {
                    Log.e("SimplePhoto", "One or more fields not found in the JSON data");
                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
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
                        photo_lv.setAdapter(new MyPhotoAdapter(getActivity(), arr));
                        break;
                }
            }
        };


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}
