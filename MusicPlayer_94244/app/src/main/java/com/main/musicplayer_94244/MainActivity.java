package com.main.musicplayer_94244;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton stop = null;
    private ImageButton play = null;
    private TextView songname = null;
    private TextView author = null;

    private  MyListener myListener = null;

    private ActivityReceiver activityReceiver =null;

    public static final String CONTROL = "com.main.musicplayer_94244.control";
    public static final String UPDATE = "com.main.musicplayer_94244.update";

    //0x11:stop;0x12:play;0x13:pause;
    private int status = 0x11;
    private int current = 0;
    private String[] songnames = new String[]{"生活不止眼前的苟且","平凡之路","夜空中最亮的星"};
    private String[] authors = new String[]{"许巍","朴树","张恒远"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        stop = (ImageButton) findViewById ( R.id.stop );
        play = (ImageButton) findViewById ( R.id.play );
        songname = (TextView) findViewById ( R.id.songname );
        author = (TextView) findViewById ( R.id.author );

        myListener = new MyListener ();

        stop.setOnClickListener ( myListener );
        play.setOnClickListener ( myListener );

        activityReceiver = new ActivityReceiver ();
        IntentFilter intentFilter =new IntentFilter ( UPDATE );
        registerReceiver ( activityReceiver,intentFilter );
        Intent intent = new Intent ( MainActivity.this,MusicService.class );
        startService ( intent );
    }

    private class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent =new Intent ( CONTROL );
            switch (v.getId ()){
                case R.id.play:
                    intent.putExtra( "control",1 );
                    break;
                case R.id.stop:
                    intent.putExtra ( "control",2 );
                    break;
            }
            //发送普通广播
            sendBroadcast ( intent );
        }
    }

    private class ActivityReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            status = intent.getIntExtra ( "update",-1 );
            current = intent.getIntExtra ( "current",-1 );
            if (current>0){
                songname.setText ( songnames[current] );
                author.setText ( authors[current] );
            }

            switch (status){
                case 0x11:
                    play.setImageResource ( R.drawable.play );
                    break;
                case 0x12:
                    play.setImageResource( R.drawable.pause );
                    break;
                case 0x13:
                    play.setImageResource( R.drawable.play );
                    break;
            }

        }
    }
}
