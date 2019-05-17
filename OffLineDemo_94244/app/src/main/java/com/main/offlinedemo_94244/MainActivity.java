package com.main.offlinedemo_94244;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    public static final String FORCE_OFFLINE = "com.main.offlinedemo_94244";
    private Button offline = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        offline = (Button) findViewById ( R.id.offline );
        offline.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( FORCE_OFFLINE );
                sendBroadcast ( intent );
            }
        } );
    }
}
