package com.main.servicelifecircledemo_94244;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.util.Log;

public class MyService extends Service {

    private  static  final  String TAG = "MyService";
    private boolean quit = false;//计数子线程循环控制变量
    private int count = 0;//计数器变量
    private MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder{
        public MyBinder()
        {
            Log.i (TAG,"MyBinder's constructior invoked!");
        }
        public int getCount()
        {
            return count;
        }
    }

    @NonNull
    @Override
    //ibinder是一个接口，用于实现Service和其他组件之间的交互
    //该接口有一个实现类binder，通常我们会写一个binder类的子类，让这个子类对象来实现之间的交互
    public IBinder onBind(Intent intent){
        Log.i (TAG,"MyService's onBind invoked!");
        return  myBinder;
    }

    @Override
    public void onCreate() {
        Log.i (TAG,"MyService's onCreate invoked!");
        super.onCreate ();
        new Thread ( new Runnable () {
            @Override
            public void run() {
                while (!quit)
                {
                    try {
                        count++;
                        Thread.sleep ( 500 );
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }
        } ).start ();
    }

    @Override
    public void onDestroy() {
        Log.i (TAG,"MyService's onDestroy invoked!");
        super.onDestroy ();
        quit = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i (TAG,"MyService's onStartCommand invoked!");
        Intent intent1 = new Intent ( this,MainActivity.class );

        PendingIntent pendingIntent = PendingIntent.getActivity (this,0,intent1,0  );

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle ( "This is content title!" )
                .setContentText ( "This is content text!" )
                .setWhen ( System.currentTimeMillis () )
                .setSmallIcon ( R.mipmap.ic_launcher )
                .setLargeIcon ( BitmapFactory.decodeResource(getResources (),R.mipmap.ic_launcher) )
                .setContentIntent ( pendingIntent );

        Notification notification = builder.build ();
        startForeground ( 0,notification );

        return super.onStartCommand ( intent, flags, startId );
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i (TAG,"MyService's onRebind invoked!");
        super.onRebind ( intent );
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i (TAG,"MyService's onUnbind invoked!");
        return super.onUnbind ( intent );
    }
}
