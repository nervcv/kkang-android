package com.example.heo.step11application;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    /**
        안드로이드 윈도우는 ActionBar 랑 Content 로 구성된다.

        <item name="windowNoTitle">true</item>
        <item name="windowActionBar">false</item>

        getSupportActionBar(); // ActionBar 기본제어

        content의 배경이미지가 actionbar에도 보이기 하기 위해선, 해당 ActionBar를 투명하게 하고,
        <item name="windowActionBarOverlay">true</item>
        <item name="colorPrimary">#00000000</item>

        <activity android:name=".MainActivity" android:label="상세 보기">, 타이틀 문자열
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true); // 아이콘을 UP 이미지로 표시 설정
            actionBar.setDisplayShowCustomEnabled(true); // CustomView 표시 설정
            actionBar.setDisplayShowHomeEnabled(true);  // 홈아이콘 표시 설정
            actionBar.setDisplayShowTitleEnabled(true); // 타이틀 문자열 표시 설정
            actionBar.setDisplayUseLogoEnabled(true);   // 로고 표시 설정

            actionBar.setCustomView(int resId); // 리소스로 커스텀뷰 지정
        */

         /*
            minSdkVersion 의 처리
            1) 구글 Support 라이브러리 도움을 받는다.
            2) 오픈소스 라이브러리 도움을 받는다.
            3) 개발자 코드에서 버전을 직접 식별해서 처리한다.

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            }

            ActionBar는 API Level 11에서 추가된 API 입니다.

            implementation 'com.android.support:appcompat-v7:27.1.1'
            appcompat 이라는 support 라이브러리를 이용한다는 이야기입니다.
            이 라이브러리가 ActionBar의 하위 호환성을 해결해주는 라이브러입니다.
            (구체적으론, extends AppCompatActivity 에서 처리한다.)
            <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
            몇가지 규칙은 특정 테마를 상속해야 하는 것이 있다.
         */

         /*
            onCreateOptionsMenu(Menu menu) : 메뉴가 만들어질 때 최초 한 번 호출
                                            (메뉴가 고정이면 한 번만 작업하면 되므로)
            onPrepareOptionsMenu(Menu menu) : 메뉴가 화면에 보일 때마다 반복 호출
                                            (메뉴가 상황에 따라 다르게 구성해야 하는 경우)
            onOptionsItemSelected(MenuItem item) : 사용자의 메뉴 이벤트 처리
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuItem item = menu.add(0, 0, 0, "슬라이드 쇼");
        // add(int groupId, int itemId, int order, int titleRes)
        // 해당 메뉴가 언제나 같다면, 코드에 메뉴를 만들지 않고, 리소스 xml을 이용하여 메뉴를 구현하는 방법도 있다.

        return true;
    }
}
