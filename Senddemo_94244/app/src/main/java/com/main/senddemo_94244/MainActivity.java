package com.main.senddemo_94244;

import android.Manifest;
//import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText num, mess, num2=null;
    Button btn, btn2=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        btn = (Button) findViewById ( R.id.btn );
        num = (EditText) findViewById ( R.id.num );
        mess = (EditText) findViewById ( R.id.Mess );

        num2 = (EditText) findViewById ( R.id.num2 );
        btn2 = (Button) findViewById ( R.id.btn2 );

        btn.setOnClickListener ( new OnClickListener () {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission ( MainActivity.this, Manifest.permission.SEND_SMS ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions ( MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 2 );
                } else {
                    sendMessage ();
                }
            }
        } );

        btn2.setOnClickListener ( new OnClickListener () {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission ( MainActivity.this, Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions ( MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1 );
                } else {
                    call ();
                }
            }
        } );
    }

    private void call() {
        Uri uri=Uri.parse("tel:"+ num2.getText().toString());
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        MainActivity.this.startActivity(intent);
    }

    private void sendMessage(){
        String mobile = num.getText ().toString ();
        String content = mess.getText ().toString ();
        Intent intent = new Intent ();
        intent.setData ( Uri.parse ( "smsto:" + mobile ) );
        intent.putExtra ( "sms_body", content );
        startActivity ( intent );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText ( this, "You denied the permission", Toast.LENGTH_SHORT ).show ();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage ();
                } else {
                    Toast.makeText ( this, "You denied the permission", Toast.LENGTH_SHORT ).show ();
                }
                break;
            default:
        }

    }
}