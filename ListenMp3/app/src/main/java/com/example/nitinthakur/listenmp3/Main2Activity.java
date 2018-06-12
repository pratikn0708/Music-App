package com.example.nitinthakur.listenmp3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.nitinthakur.listenmp3.R;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
static MediaPlayer mp;
    SeekBar sb;
   Thread updateseekbar;
    Uri u;
    Button backforw,forw,next,previous,playpause;
    int poistion;
    ArrayList<File>mysongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sb=(SeekBar)findViewById(R.id.seekBar);
        backforw=(Button)findViewById(R.id.button);
      forw  =(Button)findViewById(R.id.button4);
      next  =(Button)findViewById(R.id.button5);
        previous=(Button)findViewById(R.id.button2);
       playpause =(Button)findViewById(R.id.button3);
        backforw.setOnClickListener(this);
        forw.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        playpause.setOnClickListener(this);
        updateseekbar=new Thread()
        {
            @Override
            public void run() {
               int totalduration=mp.getDuration();
                int currentpoisition=0;
                while (currentpoisition<totalduration)
                {
                    try
                        {
                        sleep(500);
                            currentpoisition=mp.getCurrentPosition();
                            sb.setProgress(currentpoisition);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                }
              //  super.run();


            }
        };
if(mp!=null)
{
    mp.stop();
    mp.release();
}
        Intent i=getIntent();
       Bundle b= i.getExtras();
       mysongs=(ArrayList) b.getParcelableArrayList("songlist");
        poistion=b.getInt("pos",0);
        u=Uri.parse(mysongs.get(poistion).toString());
        mp=MediaPlayer.create(getApplicationContext(),u);
        mp.start();
        sb.setMax(mp.getDuration());
        updateseekbar.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.button3:
                if (mp.isPlaying())
                {
                    mp.pause();
                    playpause.setText("PLAY");
                }
                else {
                    mp.start();
                    playpause.setText("PAUSE");
                }
                break;
            case R.id.button5:
                mp.stop();
                mp.release();
                poistion=(poistion+1)%mysongs.size();
                 u=Uri.parse(mysongs.get(poistion).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
               sb.setMax(mp.getDuration());
                break;
            case R.id.button4:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;
            case R.id.button:
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;
            case R.id.button2:
                mp.stop();
                mp.release();
                poistion=(poistion-1<0)? mysongs.size()-1:poistion-1;
              /*  if (poistion-1<0)
                {
                    poistion=mysongs.size()-1;

                }
                else
                {
                    poistion=poistion-1;
                }*/
                u=Uri.parse(mysongs.get(poistion).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                sb.setMax(mp.getDuration());

                break;
        }

    }
}
