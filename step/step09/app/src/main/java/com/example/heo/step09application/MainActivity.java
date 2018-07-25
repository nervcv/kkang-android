package com.example.heo.step09application;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] arrayDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ArrayAdapter : 문자열 데이터 하나를 표출할 때 주로 사용
        ListView arrayView = findViewById(R.id.main_listview_array);
        arrayView.setOnItemClickListener(this);
        arrayDatas = getResources().getStringArray(R.array.location);

        // simple_list_item_1 : 항목에 문자열 데이터 하나
        // simple_list_item_2 : 항목에 문자열 데이터 두개 위아래 나열
        // simple_list_item_multiple_choice : 문자열과 오른쪽 체크박스 제공
        // simple_list_item_single_choice : 문자열과 오른쪽 라디오버튼 제공

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayDatas);
        arrayView.setAdapter(arrayAdapter);

        // 가상 데이터 설정
        ArrayList<HashMap<String, String>> simpleDatas = new ArrayList<>();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();


        Cursor cursor = db.rawQuery("select * from tb_data", null);

        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("content", cursor.getString(2));
            simpleDatas.add(map);
        }

        // SimpleAdapter : 문자열 데이터를 여러개 나열하는 경우, 리소스는 simpleDatas
        ListView simpleView = findViewById(R.id.main_listview_simple);

        SimpleAdapter adapter = new SimpleAdapter(this, simpleDatas, android.R.layout.simple_list_item_2,
                new String[]{"name", "content"}, // 한 항목의 데이트를 가지는 HashMap에서 데이터를 추출하기 위한 키 값
                new int[]{android.R.id.text1, android.R.id.text2}); // 추출한 데이터를 화면에 출력하기 위한 레이아웃 파일 내의 뷰 id 값
        simpleView.setAdapter(adapter);


        // CursorAdapter : 안드로이드 DBMS SELECT 결과값을 그대로 이용해 항목을 구성해준다. 리소스는 cursor
        ListView cursorView = findViewById(R.id.main_listview_cursor);

        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                cursor, new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER); // FLAG
        cursorView.setAdapter(cursorAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast t = Toast.makeText(this, arrayDatas[position], Toast.LENGTH_SHORT);
        t.show();
    }
}

