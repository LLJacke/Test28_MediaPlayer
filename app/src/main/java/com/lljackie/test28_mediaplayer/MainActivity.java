package com.lljackie.test28_mediaplayer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer mp = new MediaPlayer();

        final Button bt_start = (Button) findViewById(R.id.bt_start);
        final Button bt_pause = (Button) findViewById(R.id.bt_pause);
        final Button bt_stop = (Button) findViewById(R.id.bt_stop);
        final Button bt_loop = (Button) findViewById(R.id.bt_loop);

        //未播放时设置不可点击
        bt_pause.setEnabled(false);
        bt_stop.setEnabled(false);
        bt_loop.setEnabled(false);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.reset();
                AssetManager assetManager = getAssets();
                try {
                    AssetFileDescriptor assetFileDescriptor = assetManager.openFd("madobe2015winter_07.wav");
                    mp.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor
                            .getStartOffset(),assetFileDescriptor.getLength());
                    mp.prepare();
                    mp.start();

                    mp.setLooping(true);

                    //开始播放设置可点击
                    bt_pause.setEnabled(true);
                    bt_stop.setEnabled(true);
                    bt_loop.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){
                    bt_pause.setText(R.string.play);
                    mp.pause();
                }else{
                    bt_pause.setText(R.string.pause);
                    mp.start();
                }
            }
        });

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){
                    mp.stop();

                }
                //停止后设置三个按钮不可点击
                bt_pause.setEnabled(false);
                bt_stop.setEnabled(false);
                bt_loop.setEnabled(false);
            }
        });

        bt_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loop = mp.isLooping();
                mp.setLooping(!loop);

                if (!loop)
                    bt_loop.setText(R.string.looping);
                else
                    bt_loop.setText(R.string.isnlooping);
            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                bt_pause.setEnabled(false);
                bt_stop.setEnabled(false);
                bt_loop.setEnabled(false);

                mp.release();
            }
        });
    }
}
