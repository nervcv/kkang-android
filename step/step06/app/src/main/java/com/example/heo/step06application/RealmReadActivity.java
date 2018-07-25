package com.example.heo.step06application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import io.realm.Realm;

public class RealmReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_read);

        TextView titleView = findViewById(R.id.realm_read_title);
        TextView contentView = findViewById(R.id.realm_read_content);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        Realm mRealm = Realm.getDefaultInstance();

        MemoVO vo = mRealm.where(MemoVO.class).equalTo("title", title).findFirst();

        // findAll()
        // findAllSorted(String fieldName)
        // findAllSorted(String[] fieldName, Sort[] sortOrders)
        // findAllSorted(String fieldName, Sort sortOrders)
        // findAllSorted(String fieldName1, Sort sortOrder1, String fieldName2, Sort sortOrder2)
        // findFirst()

        // ex) mRealm.where(MemoVO.class).findAllSorted("title", Sort.DESCENDING);

        // equalTO, beginsWith, endsWith, isNotNull, in, isNull, lessThan, lessThanOrEqualTo, contains, like

        titleView.setText(vo.title);
        contentView.setText(vo.content);

    }
}

