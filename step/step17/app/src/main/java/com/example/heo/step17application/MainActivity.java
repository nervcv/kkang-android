package com.example.heo.step17application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ArrayList<String> datas;
    ArrayAdapter<String> adapter;

    Button detailBtn;
    Button dialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 베이스 뷰위에 이미지 추가
        // ImageView imageView = new ImageView(this);
        // addContentView(imageView, params);

        listView=(ListView)findViewById(R.id.main_list);
        detailBtn=(Button)findViewById(R.id.main_btn_detail);
        dialogBtn=(Button)findViewById(R.id.main_btn_dialog);

        detailBtn.setOnClickListener(this);
        dialogBtn.setOnClickListener(this);

        datas=new ArrayList<>();

        datas.add("onCreate....");

        adapter=new ArrayAdapter<String>(this, R.layout.item_main_list, datas);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v==detailBtn){
            Intent intent=new Intent(this, DetailActivity.class);
            startActivity(intent);
        }else if(v==dialogBtn){
            Intent intent=new Intent(this, DialogActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume()가 실행되어야, 실질적으로 앱화면이 그려진다.
        datas.add("onResume....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 애니메이션이나 cpu 소비를 야기할 수 있는 기타 지속적인 작업 정지
        // gps와 같은 센서 수신, 서버 네트워킹 등 엑티비티가 일시 정지하는 동안 불필요한 동작정지
        datas.add("onPause....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        datas.add("onStart....");
        adapter.notifyDataSetChanged();
    }

    // 기존 액티비티 화면이 보여지지 않으면 호출
    @Override
    protected void onStop() {
        super.onStop();

        datas.add("onStop....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        datas.add("onRestart....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        datas.add("onDestory....");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        datas.add("onSaveInstanceStatel....");
        adapter.notifyDataSetChanged();

        outState.putString("data1", "hello");
        outState.putInt("data2", 100);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        datas.add("onRestoreInstanceState....");
        adapter.notifyDataSetChanged();

        String data1=savedInstanceState.getString("data1");
        int data2=savedInstanceState.getInt("data2");

        Toast toast=Toast.makeText(this, data1+":"+data2, Toast.LENGTH_SHORT);
        toast.show();
    }

}
