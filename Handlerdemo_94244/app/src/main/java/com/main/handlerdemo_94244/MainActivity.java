package com.main.handlerdemo_94244;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView random_tv = null;

    //声明一个handler对象
    private Handler myHandler = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random_tv = (TextView) findViewById(R.id.random_tv);

        random_tv.setText("产生随机数\n"+Math.random());

        myHandler = new Handler(){
            public void handleMessage(Message message){
                if(message.what==0x11){
                    random_tv.setText("产生随机数\n"+Math.random());
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Message message =new Message();
                    message.what = 0x11;
                    myHandler.sendMessage(message);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
