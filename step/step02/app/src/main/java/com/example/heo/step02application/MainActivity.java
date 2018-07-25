package com.example.heo.step02application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(this);

        Button button1 = new Button(this);
        button1.setText("Button No.1");

        linearLayout.addView(button1);

        Button button2 = new Button(this);
        button2.setText("Button No.2");

        // 자바코드로 계층구조 구현, XML 구조는 기본적으로 구현됨.
        linearLayout.addView(button2);

        // setContentView(R.layout.activity_main);
        setContentView(linearLayout);
    }
}
