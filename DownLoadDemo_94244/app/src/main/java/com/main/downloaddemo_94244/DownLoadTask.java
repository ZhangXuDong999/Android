package com.main.downloaddemo_94244;

import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownLoadTask extends AsyncTask {

    private TextView finish_tv = null;
    private ProgressBar bar = null;

    public DownLoadTask(){

    }

    public DownLoadTask(TextView textView,ProgressBar bar){
        finish_tv = textView;
        this.bar = bar;
    }

    @Override
    protected String doInBackground(Object[] objects) {

        for(int i = 0;i<=100;i++)
        {
            publishProgress(i);
            try {
                Thread.sleep((int)objects[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "下载完毕！";
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        bar.setProgress((int) values[0]);
        bar.setVisibility(View.VISIBLE);
        finish_tv.setText("当前完成任务的:"+(int)values[0]+"%");
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        finish_tv.setText((String)o);
        finish_tv.setTextColor(Color.RED);
        bar.setVisibility(View.INVISIBLE);
    }
}
