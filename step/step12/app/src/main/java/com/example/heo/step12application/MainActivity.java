package com.example.heo.step12application;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
    라이브러리에서 제공하는 뷰가 없다면 개발자가 직접 만들어야 함.

    1. API 제공하는 뷰를 그대로 이용하면서 약간 변형
        MyView extends TextView : 데이터만 입력해주면 어느 색상으로 나올지 알아서 판단해주는 뷰

    2. 여러 뷰를 합쳐서 한번에 출력하기 위한 뷰
        MyView extends ViewGroup : 여러 뷰를 합치는 경우는 주로, ViewGroup/LinearLayout 를 상속한다.

    3. 기본 API에는 전혀 존재하지 않는 뷰
        MyView extends View : 다이나믹한 움직임을 가지는 그래프를 만드는 경우.
*/
public class MainActivity extends AppCompatActivity implements OnMyChangeListener {

    View barView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomView customView = findViewById(R.id.customView);

        // 기준값에 따른 색상변경 뷰
        barView = findViewById(R.id.barView);
        customView.setOnMyChangeListener(this);
    }

    @Override
    public void onChange(int value) {
        if (value < 0) {
            barView.setBackgroundColor(Color.RED);
        } else if (value < 30) {
            barView.setBackgroundColor(Color.YELLOW);
        } else if (value < 60) {
            barView.setBackgroundColor(Color.BLUE);
        } else {
            barView.setBackgroundColor(Color.GREEN);
        }
    }
}
