package com.example.heo.step02application;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Lab01_Activity extends AppCompatActivity implements View.OnClickListener {

    Button visibleButton;
    Button nonVisibleButton;
    TextView textView;
    CheckBox checkBox;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab01);

        visibleButton = findViewById(R.id.button_visible);
        textView = findViewById(R.id.textView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "NanumGothic.ttf");
        textView.setTypeface(typeface);

        nonVisibleButton = findViewById(R.id.button_non_visible);

        imageView = findViewById(R.id.test_img);

        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    imageView.setVisibility(View.GONE);
                    checkBox.setText("이미지 확인");
                }else{
                    imageView.setVisibility(View.VISIBLE);
                    checkBox.setText("클릭하면 이미지가 사라집니다.");
                }
            }
        });
        visibleButton.setOnClickListener(this);
        nonVisibleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == visibleButton){
            textView.setVisibility(View.VISIBLE);
        }else if(view == nonVisibleButton){
            textView.setVisibility(View.INVISIBLE);
        }
    }
}
