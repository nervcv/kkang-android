package com.example.heo.step09application;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Lab10_2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab10_2);

        ListView listView = findViewById(R.id.custom_listview);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_drive", null);

        // 가상 연속 데이터
        ArrayList<DriveVO> datas = new ArrayList<>();

        while (cursor.moveToNext()) {
            DriveVO vo = new DriveVO();
            vo.type = cursor.getString(3);
            vo.title = cursor.getString(1);
            vo.date = cursor.getString(2);
            datas.add(vo);
        }
        db.close();

        // ArrayAdapter<DriveVO> 상속한 DriveAdapter
        DriveAdapter adapter = new DriveAdapter(this, R.layout.custom_item, datas);
        listView.setAdapter(adapter);
    }
}
