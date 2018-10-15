package com.example.milind.texttospeechnotes;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
private android.os.Handler customHandler;
    private android.os.Handler customHandler2;
    private int randomAndroidColor;
    private int[] androidColors;
    private TextView d1;
    private TextView d2;
    private MediaPlayer mediaplayer;
    private int tag1;
    private int tag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        d1=findViewById(R.id.disco);
        d2=findViewById(R.id.disco2);
        tag1=0;
       // mediaplayer=new MediaPlayer();
        //mediaplayer= MediaPlayer.create(getApplicationContext(),R.raw.polsir);
        //if(mediaplayer!=null)
         //   mediaplayer.start();
        tag2=0;
        tag1=1;
        d1.setBackgroundColor(Color.parseColor("#ff1a00"));
        d2.setBackgroundColor(Color.parseColor("#000000"));
        customHandler2 = new android.os.Handler();
        customHandler2.postDelayed(updateTimerThread2, 0);
        customHandler = new android.os.Handler();
        customHandler.postDelayed(updateTimerThread, 0);
        androidColors = getResources().getIntArray(R.array.androidcolors);
        randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            //findViewById(R.id.disco).setBackgroundColor(randomAndroidColor);
            //androidColors = getResources().getIntArray(R.array.androidcolors);
            //randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            //findViewById(R.id.disco).setBackgroundColor(randomAndroidColor);
            //randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            //findViewById(R.id.disco2).setBackgroundColor(randomAndroidColor);
            //write here whaterver you want to repeat
            if(tag1==1)
            {
                tag1=0;
                d1.setBackgroundColor(Color.parseColor("#000000"));
                d2.setBackgroundColor(Color.parseColor("#0e0bdc"));
            }
            else
            {
                tag1=1;
                d2.setBackgroundColor(Color.parseColor("#000000"));
                d1.setBackgroundColor(Color.parseColor("#ff1a00"));
            }
            customHandler.postDelayed(this, 60);
        }
    };
    private Runnable updateTimerThread2 = new Runnable() {
        public void run() {
            //int[] androidColors = getResources().getIntArray(R.array.androidcolors);
            //int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            //findViewById(R.id.disco).setBackgroundColor(randomAndroidColor);
            //randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            //findViewById(R.id.disco2).setBackgroundColor(randomAndroidColor);
            //write here whaterver you want to repeat
           // mediaplayer=new MediaPlayer();
            //mediaplayer= MediaPlayer.create(getApplicationContext(),R.raw.polsir);
            //if(mediaplayer!=null)
              //  mediaplayer.start();
            customHandler.postDelayed(this, 3000);
        }
    };
   @Override
    protected void onDestroy()
   {
       //if(mediaplayer!=null)
       {
         //  mediaplayer.stop();
          // mediaplayer.release();
          // mediaplayer=null;
       }
       super.onDestroy();
   }
}
