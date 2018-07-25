package com.example.heo.step19application;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Lab17_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab17_2);

        ViewPager pager=(ViewPager)findViewById(R.id.lab2_pager);
        MyPagerAdapter pagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment> fragments;
        public MyPagerAdapter(FragmentManager manager){
            super(manager);
            fragments=new ArrayList<>();
            fragments.add(new OneFragment());
            fragments.add(new ThreeFragment());
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
