package com.example.heo.step18application;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

// Looper는 메시지 큐만을 감시하는 역활을 합니다.
public class Lab16_3Activity extends AppCompatActivity {

    OneThread oneThread;

    ArrayList<String> oddDatas;
    ArrayList<String> evenDatas;

    ArrayAdapter<String> oddAdapter;
    ArrayAdapter<String> evenAdapter;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab16_3);

        ListView oddListView=(ListView)findViewById(R.id.lab3_list_odd);
        ListView evenListView=(ListView)findViewById(R.id.lab3_list_even);

        oddDatas=new ArrayList<>();
        evenDatas=new ArrayList<>();

        oddAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, oddDatas);
        evenAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, evenDatas);

        oddListView.setAdapter(oddAdapter);
        evenListView.setAdapter(evenAdapter);

        handler=new Handler();

        oneThread=new OneThread();
        oneThread.start();

        TwoThread twoThread=new TwoThread();
        twoThread.start();

    }

    // HandlerThread를 어렵게 생각하지 말고, 앞에서 작성한 OneThread라고 보면 됩니다.
    // handlerThread = new HandlerThread("handler");
    // handlerThread.start()
    // looperHandler = new Handler(handlerThread.getLooper());

    class OneThread extends Thread {
        Handler oneHandler;

        @Override
        public void run() {
            Looper.prepare(); // OneThread 스레드를 메시지 큐를 준비

            oneHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    SystemClock.sleep(1000);
                    final int data=msg.arg1;
                    if(msg.what==0){
                        // post는 다른 스레드에 단순 사후 처리를 의뢰, message 객체 처리 알고리즘이 없어서.
                        handler.post(new Runnable(){
                            @Override
                            public void run() {
                                evenDatas.add("even : "+data);
                                evenAdapter.notifyDataSetChanged();
                            }
                        });
                    }else if(msg.what==1){
                        handler.post(new Runnable(){
                            @Override
                            public void run() {
                                oddDatas.add("odd : "+data);
                                oddAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            };
            Looper.loop(); //  데이터가 담기길 기다리다, 담기면, 루프 구동
        }
    }

    class TwoThread extends Thread {
        @Override
        public void run() {
            Random random=new Random();
            for(int i=0; i<10; i++){
                SystemClock.sleep(100);
                int data=random.nextInt(10);
                Message message=new Message();
                if(data % 2 == 0){
                    message.what=0;
                }else {
                    message.what=1;
                }
                message.arg1=data;
                message.arg2=i;
                oneThread.oneHandler.sendMessage(message); // 데이터 랜덤 생성하여 OneThread에 전달

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oneThread.oneHandler.getLooper().quit();
    }
}
