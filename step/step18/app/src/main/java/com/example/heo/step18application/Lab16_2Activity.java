package com.example.heo.step18application;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// activity anr (응답대기 exception) 처리 : AsyncTask로 처리
public class Lab16_2Activity extends AppCompatActivity implements View.OnClickListener {
    ImageView startView;
    ImageView pauseView;
    TextView textView;

    boolean isFirst = true;

    MyAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab16_2);

        startView = (ImageView) findViewById(R.id.main_startBtn);
        pauseView = (ImageView) findViewById(R.id.main_pauseBtn);
        textView = (TextView) findViewById(R.id.main_textView);

        startView.setOnClickListener(this);
        pauseView.setOnClickListener(this);

        asyncTask = new MyAsyncTask();
    }

    @Override
    public void onClick(View v) {
        if(v==startView){
            if(isFirst){
                asyncTask.isRun=true;
                asyncTask.execute();
                isFirst=false;
            }else {
                asyncTask.isRun=true;
            }
        }else if(v==pauseView){
            asyncTask.isRun=false;
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, String>{
        boolean loopFlag=true;
        boolean isRun;

        // 스레드에 의해 처리될 내용을 담기 위한 함수
        @Override
        protected String doInBackground(Void... params) {
            int count=10;
            while (loopFlag){
                SystemClock.sleep(1000);
                if(isRun){
                    count--;
                    publishProgress(count);
                    if(count==0){
                        loopFlag=false;
                    }
                }
            }
            return "Finish!! MyAsyncTask";
        }

        // doInBackground 함수에 의해 처리되는 중간중간 값을 받아 처리하기 위해 호출, publishProgress() 함수로 값이 넘겨진 값이 여기에 도달
        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText(String.valueOf(values[0]));
        }

        // AsyncTask의 작업을 시작하기 전에 호출. AyncTask에서 가장 먼저 호출
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // AsyncTask의 모든 작업이 완료된 후, 가장 마지막에 한번 호출, doInBackground의 최종 값을 받기 위해
        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
}
