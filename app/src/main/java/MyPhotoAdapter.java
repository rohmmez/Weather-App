package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyPhotoAdapter extends BaseAdapter

{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> arr;


    public MyPhotoAdapter(Context context, ArrayList<String> arr)
    {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.arr = arr;
    }

    @Override
    public int getCount()
    {
        return 6;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    static class PhotoViewHolder
    {
        public ImageView photoHolder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PhotoViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.photo_item, null);
            holder = new PhotoViewHolder();
            holder.photoHolder = convertView.findViewById(R.id.photo_iv);
            convertView.setTag(holder);
        }
        else
        {
            holder = (PhotoViewHolder) convertView.getTag();
        }

        String url = arr.get(position);
        Glide.with(mContext).load(url).into(holder.photoHolder);

        return convertView;
    }
}
