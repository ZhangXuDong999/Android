package com.main.musicplayer_94244;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    //0x11:stop;0x12:play;0x13:pause;
    int status = 0x11;
    int current = 0;
    private MediaPlayer mediaPlayer = null;
    private AssetManager assetManager = null;
    private  String[] musics = new String[]{"life.mp3", "live.mp3", "star.mp3"};
    private  ServiceReceiver serviceReceiver =null;
    public MusicService() {

    }

    @Override
    public void onCreate() {
        super.onCreate ();
        mediaPlayer = new MediaPlayer ();
        assetManager = getAssets ();
        serviceReceiver = new ServiceReceiver ();
        //动态注册serviceReceiver
        IntentFilter intentFilter = new IntentFilter ( MainActivity.CONTROL );
        registerReceiver ( serviceReceiver,intentFilter );
        mediaPlayer.setOnCompletionListener ( new MediaPlayer.OnCompletionListener () {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;
                if (current>=3)
                {
                    current = 0;
                }
                Intent intent = new Intent ( MainActivity.UPDATE );
                intent.putExtra ( "update",current );
                sendBroadcast ( intent );
                PrepareAndPlay ( musics[current] );
            }
        } );
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException ( "Not yet implemented" );
    }

    private class ServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int control = intent.getIntExtra ( "control" ,-1);
            switch (control){
                case 1:
                    if (status==0x12)
                    {
                        mediaPlayer.pause ();
                        status = 0x13;
                    }
                    else if(status==0x11)
                    {
                        PrepareAndPlay(musics[current]);
                        status = 0x12;
                    }
                    else if(status==0x13)
                    {
                        mediaPlayer.start ();
                        status = 0x12;
                    }
                    break;
                case 2:
                    mediaPlayer.stop ();
                    status = 0x11;
                    break;
            }

            Intent intent1 = new Intent ( MainActivity.UPDATE );
            intent1.putExtra ( "update",status );
            intent1.putExtra ( "current",current );
            sendBroadcast ( intent1 );
        }
    }

    private void PrepareAndPlay(String music){
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd ( music );
            mediaPlayer.reset ();
            mediaPlayer.setDataSource( assetFileDescriptor.getFileDescriptor ()
                    ,assetFileDescriptor.getStartOffset ()
                    ,assetFileDescriptor.getLength ());
            mediaPlayer.prepare ();
            mediaPlayer.start ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
