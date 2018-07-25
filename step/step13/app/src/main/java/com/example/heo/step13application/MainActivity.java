package com.example.heo.step13application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_call);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*암시적 인텐트 호출, 주로 외부 앱 연동 때 사용*/
                Intent i = new Intent("com.example.heo.intentfilter.secondview");
                startActivity(i);

            }
        });
    }
}
