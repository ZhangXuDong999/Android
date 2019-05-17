package com.main.downloaddemo_94244;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button load_btn = null;
    private TextView finish_tv = null;
    private ProgressBar bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load_btn = (Button) findViewById(R.id.load_btn);
        finish_tv = (TextView) findViewById(R.id.finish_tv);
        bar =(ProgressBar) findViewById(R.id.bar);

        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadTask downLoadTask =new DownLoadTask(finish_tv,bar);
                downLoadTask.execute(100);
            }
        });
    }
}
