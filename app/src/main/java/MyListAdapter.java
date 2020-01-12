package com.example.weatherapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;

public class MyListAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat simpleDateFormat;
    private Information information;


    public MyListAdapter(Context context, Information information)
    {
        simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.information = information;
    }

    @Override
    public int getCount()
    {
        return 8;
    }

    @Override

    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ListViewHolder
    {
        public TextView dateHolder;
        public TextView weatherPic;
        public TextView lowTHolder;
        public TextView highTHolder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ListViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            holder = new ListViewHolder();
            holder.dateHolder = convertView.findViewById(R.id.card3_tv1);
            holder.weatherPic = convertView.findViewById(R.id.card3_tv2);
            holder.lowTHolder = convertView.findViewById(R.id.card3_tv3);
            holder.highTHolder = convertView.findViewById(R.id.card3_tv4);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ListViewHolder) convertView.getTag();
        }
        Drawable weather = getDrawable(information.card3Icon[position]);
        weather.setBounds(0, 0, weather.getMinimumWidth(), weather.getMinimumWidth());
        holder.dateHolder.setText(simpleDateFormat.format(information.dates[position]));
        holder.weatherPic.setCompoundDrawables(null, null, weather,null);
        holder.lowTHolder.setText(information.temperatureMin[position]);
        holder.highTHolder.setText(information.temperatureMax[position]);
        return convertView;
    }

    private Drawable getDrawable(String icon)
    {
        Drawable drawableRight = null;
        if (icon.equals("partly-cloudy-night"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_night_partly_cloudy_small);
        }
        else if (icon.equals("clear-day"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_sunny_small);
        }
        else if (icon.equals("clear-night"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_night_small);
        }
        else if (icon.equals("rain"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_rainy_small);
        }
        else if (icon.equals("sleet"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_snowy_rainy_small);
        }
        else if (icon.equals("snow"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_snowy_small);
        }
        else if (icon.equals("wind"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_windy_variant_small);
        }
        else if (icon.equals("fog"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_fog_small);
        }
        else if (icon.equals("cloudy"))
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_cloudy_small);
        }
        else
        {
            drawableRight = mContext.getResources().getDrawable(R.drawable.weather_partly_cloudy_small);
        }
        return drawableRight;
    }
}
