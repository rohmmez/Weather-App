package com.example.weatherapp;

import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class MainFragmentAdapter extends FragmentStatePagerAdapter
{
    private int num;

    public MainFragmentAdapter(FragmentManager fm, int num)
    {
        super(fm);
        this.num = num;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            return new MainFragment();
        }
        return new DynamicFragment(position);
    }

    @Override
    public int getCount()
    {
        return num;
    }

}
