package com.example.milind.texttospeechnotes;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class home extends AppCompatActivity {
    private TextToSpeech t1;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        checkPermission();
        getSupportActionBar().hide();
        findViewById(R.id.Editor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.textadder.class);
                startActivity(in);
            }
        });
        findViewById(R.id.MoneyTracker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.moneytrack.class);
                startActivity(in);
            }
        });
        //for notification
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            Intent alarmIntent = new Intent(this, moneytrack.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }
        //notify ends
        findViewById(R.id.textView2).setVisibility(View.GONE);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                {
                    String mostLikelyThingHeard = matches.get(0);
                    // toUpperCase() used to make string comparison equal
                    if(mostLikelyThingHeard.toUpperCase().equals("EDITOR")){
                        startActivity(new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.textadder.class));
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("DISCO"))
                    {
                        Intent in=new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.Main2Activity.class);
                        startActivity(in);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("READER"))
                    {
                        String tospeak="Reader is currently disabled,please wait for further updates";
                        t1.setSpeechRate(0.8f);
                        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("MONEY TRACKER"))
                    {
                        startActivity(new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.moneytrack.class));
                    }
                    else{
                        String tospeak="Invalid command sir please Repeat";
                        t1.setSpeechRate(0.8f);
                        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        //Toast.makeText(getApplicationContext(),"Invalid Command",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.Main2Activity.class);
                startActivity(in);
            }
        });

        findViewById(R.id.voice).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        //editText.setText("");
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.textView2).getVisibility()!=View.VISIBLE)
                findViewById(R.id.textView2).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.textView2).setVisibility(View.GONE);
            }
        });
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.CANADA);
                }
            }
        });
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
