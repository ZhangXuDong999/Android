package com.main.litepaldemo_94244;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {
    private Button create = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        create = (Button) findViewById ( R.id.create_btn );
        create.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase ();
            }
        } );
    }
}
