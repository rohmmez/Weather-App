package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{

    public static final String SHARED_PREFS = "sharedPrefs";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int num = saveData();
        initViews(num);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_action, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("name", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(MainActivity.this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));

        return true;
    }

     public void initViews(int num)
    {
        mViewPager = findViewById(R.id.main_viewPager);
        mTabLayout = findViewById(R.id.main_tabLayout);


        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), num);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < num; i++)
        {
            mTabLayout.getTabAt(i).setIcon(R.drawable.tab_selector);
        }
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }


    private int saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int num = 0;

        if (sharedPreferences.getInt("count", 0) == 0)
        {
            num = 1;
            editor.putInt("count", 1);

        }
        else
        {
            num = sharedPreferences.getInt("count", 0);
            editor.putInt("count", num);
        }

        editor.apply();
        return num;
    }
}
