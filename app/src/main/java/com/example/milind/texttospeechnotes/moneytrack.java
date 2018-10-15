package com.example.milind.texttospeechnotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class moneytrack extends AppCompatActivity {
    private EditText input;
    private int tag;
    private int amount;
    private String text;
    private String reason;
    private TextToSpeech t1;
    //private InterstitialAd mInterstitialAd;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_moneytrack);
        tag=1;
        getSupportActionBar().hide();
        findViewById(R.id.show).setVisibility(View.GONE);
        //MobileAds.initialize(this, "ca-app-pub-1602936449422625~7642925490");
      //  mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-1602936449422625/7922799548");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
                    if(tag==1)
                    {
                        text=matches.get(0);
                        if(text.trim().matches("\\d+(?:\\.\\d+)?"))
                        {
                            amount=Integer.parseInt(text.trim());
                            String tospeak="Amount added successfully ,please enter the details of the transaction";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                            tag=2;
                        }
                        else
                        {
                            String tospeak="Invalid amount ,Sir Please Re-enter amount";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                            tag=1;
                        }
                    }
                    else if(tag==2){
          /*              if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }*/
                        text=matches.get(0);
                        reason=text;
                        String Tdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        String tospeak="\n\nDate: "+Tdate+"\namount="+Integer.toString(amount)+"\n Reason:"+reason;
                        t1.setLanguage(Locale.US);
                        t1.setSpeechRate(0.8f);
                        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        tag=1;
                        makeLingEntry(tospeak);
                    }
                    if(matches.get(0).toUpperCase().equals("SHOW"))
                    {
                        /*if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }*/
                        String tospeak="Showing all previous records";
                        t1.setLanguage(Locale.US);
                        t1.setSpeechRate(0.8f);
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        findViewById(R.id.show).setVisibility(View.VISIBLE);
                        TextView disp=findViewById(R.id.disp);
                        String show="";
                        String temp;
                        ArrayList<String> out=new ArrayList<>();
                        out=Data();
                        for(int i=0;i<out.size();i++)
                        {
                            temp=show+out.get(i);
                            show=temp;
                        }
                        disp.setText(show);
                        tag=0;
                    }else if(matches.get(0).toUpperCase().equals("CLEAR ALL"))
                    {
                        /*if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }*/
                        String tospeak="Clearing all previous records";
                        t1.setLanguage(Locale.US);
                        t1.setSpeechRate(0.8f);
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        delete();
                        tag=0;
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
        /*mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
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
        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.textView2).getVisibility()!=View.VISIBLE)
                    findViewById(R.id.textView2).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.textView2).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.imageButton).setOnTouchListener(new View.OnTouchListener() {
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
    }
    @Override
    public void onBackPressed() {
        if(findViewById(R.id.show).getVisibility()==View.GONE) {
            Intent back = new Intent(getApplicationContext(), home.class);
            startActivity(back);
        }
        else
        {
            findViewById(R.id.show).setVisibility(View.GONE);
            String tospeak="Sir, Please Enter amount";
            t1.setLanguage(Locale.US);
            t1.setSpeechRate(0.8f);
            Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
            tag=1;
        }
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
    public void makeLingEntry(String Text){

        SQLiteDatabase mDataBase;
        String sql = "INSERT INTO makeAmountEntry (Text) VALUES (' " +Text+ " ');";
        mDataBase = openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
        mDataBase.execSQL("CREATE TABLE IF NOT EXISTS makeAmountEntry (Text VARCHAR);");
        mDataBase.execSQL(sql);
    }
    @Override
    protected void onStart() {
        super.onStart();
        String tospeak="Sir, Please Enter amount";
        t1.setLanguage(Locale.US);
        t1.setSpeechRate(0.8f);
        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
        tag=1;
    }
    public ArrayList<String> Data() {
        ArrayList<String> data = new ArrayList<>();
        try {
            SQLiteDatabase mDb = this.openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
            String a = "SELECT * FROM " + " makeAmountEntry ";
            //if(table.equals("makeExpenseOutflowCategory") || table.equals("makeIncomeInflowCategory") || table.equals("makeIncomeOutflowCategory")) {
            Cursor c = mDb.rawQuery(a, null);
            int seq = c.getColumnIndex("Text");
            c.moveToFirst();
            if (c.getCount() != 0) {
                while (c != null) {
                    String newData;
                    newData = c.getString(seq);
                    data.add(newData);
                    c.moveToNext();
                }
            }
        }catch (Exception e)
        {

        }
        return data;
    }
    public void delete(){


        String a="drop table if exists " + "makeAmountEntry" + ";";
        SQLiteDatabase mDataBase;
        mDataBase = openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
        if(mDataBase!=null) {
            mDataBase.execSQL(a);
        }


    }
}