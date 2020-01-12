package com.example.weatherapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    private String[] mTitles = new String[]{"TODAY", "WEEKLY", "PHOTOS"};
    private Information information;

    public MyFragmentPagerAdapter(FragmentManager fm, Information information)
    {
        super(fm);
        this.information = information;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {
            return new TodayFragment(information);
        }
        else if (position == 1)
        {
            return new WeeklyFragment(information);
        }
        return new PhotoFragment(information);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles[position];
    }
}