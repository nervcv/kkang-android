package com.example.heo.step03application;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class TabHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost_layout);

        TabHost tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon1, null));
        tabSpec.setContent(R.id.tab_content_01);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon2, null));
        tabSpec.setContent(R.id.tab_content_02);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab3");
        tabSpec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon3, null));
        tabSpec.setContent(R.id.tab_content_03);
        tabHost.addTab(tabSpec);

    }
}
