package com.example.heo.step18application;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// activity anr (응답대기 exception) 처리 : 스레드-핸들러 구조
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView startView;
    ImageView pauseView;
    TextView textView;

    boolean loopFlag = true;
    boolean isFirst = true;
    boolean isRun;

    MyThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startView = (ImageView) findViewById(R.id.main_startBtn);
        pauseView = (ImageView) findViewById(R.id.main_pauseBtn);
        textView = (TextView) findViewById(R.id.main_textView);

        startView.setOnClickListener(this);
        pauseView.setOnClickListener(this);

        thread = new MyThread();
    }

    @Override
    public void onClick(View v) {
        if (v == startView) {
            if (isFirst) {
                isFirst = false;
                isRun = true;
                thread.start();
            } else {
                isRun = true;
            }
        } else if (v == pauseView) {
            isRun = false;
        }
    }

    /*
        Handler handler = new Handler();

        class UIUpdate implements Runnable {

            @Override
            public void run() {
                textView.setText("sum" + sum);
            }
        }

        class MyThread extends Thread {

            @Override
            public void run() {
                for (int i=0; i<10; i++){
                    sum += 1;
                    // handler.postDelayed(new UIUpdate(), 지연시간);
                    handler.post(new UIUpdate());
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException ex){

                    }
                }
            }
        }
    */

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                textView.setText(String.valueOf(msg.arg1));
            }else if(msg.what==2){
                textView.setText((String)msg.obj);
            }
        }
    };

    class MyThread extends Thread {
        @Override
        public void run() {
            try{
                int count=10;
                while (loopFlag){
                    Thread.sleep(1000);
                    if(isRun){
                        count--;
                        Message message=new Message();
                        message.what=1;
                        message.arg1=count; // 스레드에 넘길 데이터, int형
                        handler.sendMessage(message); // UI에 의뢰
                        // handler.sendMessageAtFrontOfQueue(message); // 여러 의뢰 중 최우선 처리 요청
                        // handler.sendMessageAtTime(message, 1000); // 의뢰를 지정된 시간에 수행
                        // handler.sendMessageDelayed(message, 1000); // 지정 시간 후에 수행
                        // handler.sendEmptyMessage(0); // 데이터 전달 없이 의뢰하는 겨우
                        // what : int형 변수 구분자, 요청을 구분하기 위해 사용
                        if(count==0){
                            message=new Message();
                            message.what=2;
                            message.obj="Finish!!"; // 스레드에 넘길 데이터, 객체형
                            handler.sendMessage(message);
                            loopFlag=false;
                        }
                    }
                }
            }catch (Exception e){

            }
        }
    }

    
}














