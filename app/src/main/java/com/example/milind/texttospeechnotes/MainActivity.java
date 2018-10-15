package com.example.milind.texttospeechnotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    TextToSpeech t1;
    EditText ed1;
    Button b1;
    Button b2;
    Button b3;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private int langcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        b1=findViewById(R.id.button5);
        langcode=0;
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
                if (matches != null) {
                    String mostLikelyThingHeard = matches.get(0);
                    // toUpperCase() used to make string comparison equal
                    if (mostLikelyThingHeard.toUpperCase().equals("CHINESE")) {
                        langcode = 1;
                        t1.setLanguage(Locale.CHINESE);
                        String lang = "SELECTED LANGUAGE :CHINESE";
                        b1.setText(lang);
                       /* String toSpeak = ed1.getText().toString();
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        t1.setLanguage(Locale.CHINESE);
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        t1.setSpeechRate(0.3f);*/
                    } else if (mostLikelyThingHeard.toUpperCase().equals("FRENCH")) {
                        langcode = 2;
                        t1.setLanguage(Locale.FRENCH);
                        String lang = "SELECTED LANGUAGE :FRENCH";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("GERMAN")) {
                        langcode = 3;
                        t1.setLanguage(Locale.GERMAN);
                        String lang = "SELECTED LANGUAGE :GERMAN";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("JAPANESE")) {
                        langcode = 4;
                        t1.setLanguage(Locale.JAPANESE);
                        String lang = "SELECTED LANGUAGE :JAPANESE";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("KOREAN")) {
                        langcode = 5;
                        t1.setLanguage(Locale.KOREAN);
                        String lang = "SELECTED LANGUAGE :KOREAN";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("UNITED KINGDOM")) {
                        langcode = 6;
                        t1.setLanguage(Locale.UK);
                        String lang = "SELECTED LANGUAGE :UK";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("UNITED STATES")) {
                        langcode = 7;
                        t1.setLanguage(Locale.US);
                        String lang = "SELECTED LANGUAGE :US";
                        b1.setText(lang);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("ITALIAN")) {
                        t1.setLanguage(Locale.ITALIAN);
                        String lang = "SELECTED LANGUAGE :ITALIAN";
                        b1.setText(lang);
                        langcode = 8;
                    } else if (mostLikelyThingHeard.toUpperCase().equals("SPEAK NOW")&&langcode!=0) {
                        Intent next=new Intent(getApplicationContext(), com.example.milind.texttospeechnotes.readres.class);
                        Bundle bun=new Bundle();
                        String text=ed1.getText().toString();
                        bun.putInt("Code",langcode);
                        bun.putString("Text",text);
                        next.putExtras(bun);
                        startActivity(next);
                    }else {
                        String tospeak="INVALID LANGUAGE";
                        t1.setLanguage(Locale.UK);
                        t1.setSpeechRate(0.5f);
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(getApplicationContext(),"INVALID LANGUAGE" , Toast.LENGTH_SHORT).show();
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
       /*findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent te=new Intent(getApplicationContext(),textadder.class);
                //startActivity(te);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Choose Language");
                startActivityForResult(intent, 1);
            }
        });*/
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.CANADA);
                }
            }
        });
        findViewById(R.id.button4).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#3F51B5"));
                        mSpeechRecognizer.stopListening();
                        break;

                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.button4).setBackgroundColor(Color.parseColor("#06f2ee"));
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        //editText.setText("");
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
            Intent back = new Intent(getApplicationContext(), home.class);
            startActivity(back);
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {
                    // didn't hear anything
                } else {
                    String mostLikelyThingHeard = matches.get(0);
                    // toUpperCase() used to make string comparison equal
                    if(mostLikelyThingHeard.toUpperCase().equals("CHINESE")){
                        langcode=1;
                        t1.setLanguage(Locale.CHINESE);
                        String lang="SELECTED LANGUAGE :CHINESE";
                        b1.setText(lang);
                       /* String toSpeak = ed1.getText().toString();
                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                        t1.setLanguage(Locale.CHINESE);
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        t1.setSpeechRate(0.3f);*/
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("FRENCH"))
                    {
                        langcode=2;
                        t1.setLanguage(Locale.FRENCH);
                        String lang="SELECTED LANGUAGE :FRENCH";
                        b1.setText(lang);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("GERMAN"))
                    {
                        langcode=3;
                        t1.setLanguage(Locale.GERMAN);
                        String lang="SELECTED LANGUAGE :GERMAN";
                        b1.setText(lang);
                    }else if(mostLikelyThingHeard.toUpperCase().equals("JAPANESE"))
                    {
                        langcode=4;
                        t1.setLanguage(Locale.JAPANESE);
                        String lang="SELECTED LANGUAGE :JAPANESE";
                        b1.setText(lang);
                    }else if(mostLikelyThingHeard.toUpperCase().equals("KOREAN"))
                    {
                        langcode=5;
                        t1.setLanguage(Locale.KOREAN);
                        String lang="SELECTED LANGUAGE :KOREAN";
                        b1.setText(lang);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("UNITED KINGDOM"))
                    {
                        langcode=6;
                        t1.setLanguage(Locale.UK);
                        String lang="SELECTED LANGUAGE :UK";
                        b1.setText(lang);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("UNITED STATES"))
                    {
                        langcode=7;
                        t1.setLanguage(Locale.US);
                        String lang="SELECTED LANGUAGE :US";
                        b1.setText(lang);
                    }
                    else if(mostLikelyThingHeard.toUpperCase().equals("ITALIAN"))
                    {
                        t1.setLanguage(Locale.ITALIAN);
                        String lang="SELECTED LANGUAGE :ITALIAN";
                        b1.setText(lang);
                        langcode=8;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"INVALID LANGUAGE",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}