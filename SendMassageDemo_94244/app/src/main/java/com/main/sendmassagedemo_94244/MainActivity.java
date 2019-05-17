package com.main.sendmassagedemo_94244;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * 常用的系统级的服务有：
 * 1.SmsManager //短信
 * 2.TelephoneManager//获取网络状态，读取SIM卡信息，监听来电通话状态
 * 3.AudioManager//调节音量大小或静音
 * 4.AlarmMananger//闹钟服务，定时器功能
 * 5.Vibrator//震动器
 */
public class MainActivity extends AppCompatActivity {
    private EditText num = null;
    private EditText content = null;
    private Button send = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        num = (EditText) findViewById ( R.id.num );
        content = (EditText) findViewById ( R.id.content );
        send = (Button) findViewById ( R.id.send );

        ActivityCompat.requestPermissions ( this,
                new String[]{Manifest.permission.SEND_SMS},1 );

        send.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String num1 = num.getText ().toString ().trim ();
                String content1 = content.getText ().toString ().trim ();

                SmsManager smsManager = SmsManager.getDefault ();

                PendingIntent pendingIntent = PendingIntent.getBroadcast ( MainActivity.this,
                        0,new Intent (  ),0 );
                List<String>msgs=smsManager.divideMessage ( content1 );

                for(String msg:msgs){
                    smsManager.sendTextMessage ( num1,null,msg,pendingIntent,null );
                }
                Toast.makeText ( MainActivity.this,"短信发送完毕！",Toast.LENGTH_LONG).show ();
            }
        } );
    }
}
