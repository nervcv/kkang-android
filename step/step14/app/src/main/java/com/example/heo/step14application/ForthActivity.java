package com.example.heo.step14application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ForthActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("4번째 Activity 입니다.");
		tv.setTextSize(30);
		setContentView(tv);
	}
}
