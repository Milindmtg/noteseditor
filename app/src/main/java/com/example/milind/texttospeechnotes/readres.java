package com.example.milind.texttospeechnotes;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Locale;

public class readres extends Activity {
    TextToSpeech t1;
    Button b1;
    private Bundle bun;
    private String tospeak;
    private RelativeLayout parent;
    private RelativeLayout load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readres);
        bun=new Bundle();
        parent=findViewById(R.id.parent);
        parent.setVisibility(View.GONE);
        parent.postDelayed(new Runnable() {
            public void run() {
                parent.setVisibility(View.VISIBLE);
            }
        }, 4000);
        load=findViewById(R.id.loading);
        load.setVisibility(View.VISIBLE);
        load.postDelayed(new Runnable() {
            public void run() {
                load.setVisibility(View.GONE);
            }
        }, 4000);
        bun=getIntent().getExtras();
        tospeak=bun.getString("Text");
        b1=(Button)findViewById(R.id.button4);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    int langcode=bun.getInt("Code");
                    switch (langcode)
                    {
                        case 1:t1.setLanguage(Locale.CHINESE);
                            String lang="Speak in Chinese";
                            b1.setText(lang);
                            break;
                        case 2:t1.setLanguage(Locale.FRENCH);
                            lang="Speak in FRENCH";
                            b1.setText(lang);
                            break;
                        case 3:t1.setLanguage(Locale.GERMAN);
                            lang="Speak in GERMAN";
                            b1.setText(lang);
                            break;
                        case 4:t1.setLanguage(Locale.JAPANESE);
                            lang="Speak in JAPANESE";
                            b1.setText(lang);
                            break;
                        case 5:t1.setLanguage(Locale.KOREAN);
                            lang="Speak in KOREAN";
                            b1.setText(lang);
                            break;
                        case 6:t1.setLanguage(Locale.UK);
                            lang="Speak in UK";
                            b1.setText(lang);
                            break;
                        case 7:t1.setLanguage(Locale.US);
                            lang="Speak in US";
                            b1.setText(lang);
                            break;
                        case 8:t1.setLanguage(Locale.ITALIAN);
                            lang="Speak in ITALIAN";
                            b1.setText(lang);
                            break;
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setSpeechRate(0.5f);
                Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                //Intent back=new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(back);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        // mAuth.addAuthStateListener(mAuthListener);
        //   mAuth2.addAuthStateListener(mAuthListener);
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
