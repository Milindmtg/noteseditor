package com.example.milind.texttospeechnotes;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class textadder extends AppCompatActivity {
    private String text;
    private TextToSpeech t1;
    private int id=0;
    private TextView tv;
    private String cont;
    //private InterstitialAd mInterstitialAd;
    private static final String TAG = "MEDIA";
    private EditText editText;
    private ArrayList<Button> textname=new ArrayList<>();
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    final static String fileName = "Prev_notes.txt";
    //private ListView listview1;
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Voice_Notes/Previous_data/" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_textadder);
        editText = findViewById(R.id.editText);
        checkPermission();
        getSupportActionBar().hide();
        id=1;
        findViewById(R.id.output).setVisibility(View.GONE);
        findViewById(R.id.textView2).setVisibility(View.GONE);
        findViewById(R.id.textView3).setVisibility(View.GONE);
        //MobileAds.initialize(this, "ca-app-pub-1602936449422625~7642925490");
      //  mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-1602936449422625/6934803899");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        Bundle bun=getIntent().getExtras();
        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.output).getVisibility()!=View.VISIBLE) {
                    if (findViewById(R.id.textView2).getVisibility() != View.VISIBLE)
                        findViewById(R.id.textView2).setVisibility(View.VISIBLE);
                    else
                        findViewById(R.id.textView2).setVisibility(View.GONE);
                }
                else {
                    if (findViewById(R.id.textView3).getVisibility() != View.VISIBLE) {
                        findViewById(R.id.display).setVisibility(View.GONE);
                        findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        findViewById(R.id.textView3).setVisibility(View.GONE);
                        findViewById(R.id.display).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        if(bun!=null)
        {
            String Text=bun.getString("Text");
            editText.setText(Text);
        }
        findViewById(R.id.cambutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OcrCaptureActivity.class);
                // query contains search string
                startActivity(intent);
            }
        });
        findViewById(R.id.savebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                    Toast.makeText(getApplicationContext(),"Ad not loaded",Toast.LENGTH_SHORT).show();
                }*/
                String Tdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                text ="Date: "+Tdate+"\n"+editText.getText().toString();
                id++;
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                makeLingEntry(text);
                String tospeak = "Showing all previous records";
                t1.setLanguage(Locale.US);
                t1.setSpeechRate(0.8f);
                t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                findViewById(R.id.input).setVisibility(View.GONE);
                TextView disp = findViewById(R.id.display);
                String show = "";
                String temp;
                ArrayList<String> out = new ArrayList<>();
                out = Data();
                for (int i = 0; i < out.size(); i++) {
                    temp = show + "\n\n text file " + Integer.toString(i) + " \n" + out.get(i);
                    show = temp;
                }
                findViewById(R.id.output).setVisibility(View.VISIBLE);
                disp.setText(show);
            }
        });
        /*mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });*/
        //listview1 = findViewById(R.id.listinflow);
        cont="@#?";
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        tv=findViewById(R.id.disp2);
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
                        if (!matches.get(0).toUpperCase().equals("SAVE")&&!matches.get(0).toUpperCase().equals("SOLVE")&&!matches.get(0).toUpperCase().equals("CLEAR")&& !matches.get(0).toUpperCase().equals("SHOW") && !matches.get(0).toUpperCase().equals("SEARCH") && findViewById(R.id.input).getVisibility() != View.GONE)
                        {
                            text = editText.getText().toString();
                            cont = text;
                            text=cont+" "+matches.get(0);
                            editText.setText(text);
                            cont="@#?";
                        }
                        else if (matches.get(0).toUpperCase().equals("SOLVE")) {
                            try {
                                double ans = eval(editText.getText().toString());
                                String Ans = Double.toString(ans);
                                editText.setText(editText.getText().toString()+"="+Ans);
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                        else if (matches.get(0).toUpperCase().equals("SEARCH")) {
                            String query = editText.getText().toString();
                            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                            intent.putExtra(SearchManager.QUERY, text); // query contains search string
                            startActivity(intent);
                        } else if (matches.get(0).toUpperCase().equals("CLEAR ALL") && findViewById(R.id.output).getVisibility() == View.VISIBLE) {
                            TextView disp = findViewById(R.id.display);
                            disp.setText("");
                            delete();
                            String tospeak = "Clearing all text files " + disp.getText().toString();
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        } else if (matches.get(0).toUpperCase().equals("READ ALL") && findViewById(R.id.output).getVisibility() == View.VISIBLE) {
                            TextView disp = findViewById(R.id.display);
                            String tospeak = "Reading out all text files " + disp.getText().toString();
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                        } else if (matches.get(0).toUpperCase().equals("SAVE AS TEXT") && findViewById(R.id.output).getVisibility() == View.VISIBLE) {
                            TextView disp = findViewById(R.id.display);
                            String tospeak = "saving text files in text";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                saveToFile(disp.getText().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            writeToSDFile(disp.getText().toString());
                        } else if (matches.get(0).toUpperCase().equals("CLEAR")) {
                            editText.setText("");
                        } else if (matches.get(0).toUpperCase().equals("SAVE")) {
                            String Tdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            text ="Date: "+Tdate+"\n"+editText.getText().toString();
                            id++;
                            String tospeak = "Saving  of the file completed successfully. say show to display";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            makeLingEntry(text);
                        } else if (matches.get(0).toUpperCase().equals("SHOW")) {
                            String tospeak = "Showing all previous records";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                            findViewById(R.id.input).setVisibility(View.GONE);
                            TextView disp = findViewById(R.id.display);
                            String show = "";
                            String temp;
                            ArrayList<String> out = new ArrayList<>();
                            out = Data();
                            for (int i = 0; i < out.size(); i++) {
                                temp = show + "\n\n text file " + Integer.toString(i) + " \n" + out.get(i);
                                show = temp;
                            }
                            findViewById(R.id.display).setVisibility(View.VISIBLE);
                            disp.setText(show);
                        } else {
                            String tospeak = "Invalid Command Sir please repeat";
                            t1.setLanguage(Locale.US);
                            t1.setSpeechRate(0.8f);
                            Toast.makeText(getApplicationContext(), tospeak, Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text=editText.getText().toString();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        //editText.setText("");
                        editText.setHint("\n\nListening...");
                        break;
                }
                return false;
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
        findViewById(R.id.button5).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                text=editText.getText().toString();
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
        if(findViewById(R.id.input).getVisibility()!=View.GONE) {
            Intent back = new Intent(getApplicationContext(), home.class);
            startActivity(back);
        }
        else
        {
            if(findViewById(R.id.textView3).getVisibility()==View.VISIBLE)
                findViewById(R.id.textView3).setVisibility(View.GONE);
            findViewById(R.id.input).setVisibility(View.VISIBLE);
            findViewById(R.id.output).setVisibility(View.GONE);
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
        String sql = "INSERT INTO makeLingEntry (Text) VALUES (' " +Text+ " ');";
        mDataBase = openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
        mDataBase.execSQL("CREATE TABLE IF NOT EXISTS makeLingEntry (Text VARCHAR);");
        mDataBase.execSQL(sql);
    }
    public ArrayList<String> Data() {
        ArrayList<String> data = new ArrayList<>();
        try {
            SQLiteDatabase mDb = this.openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
            String a = "SELECT * FROM " + " makeLingEntry ";
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
                    if(mostLikelyThingHeard.toUpperCase().equals("SEARCH")){
                        String query=editText.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, query); // query contains search string
                        startActivity(intent);
                    } else if(mostLikelyThingHeard.toUpperCase().equals("READ ALL"))
                    {
                        TextView disp=findViewById(R.id.display);
                        String tospeak=disp.getText().toString();
                        t1.setLanguage(Locale.US);
                        t1.setSpeechRate(0.8f);
                        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else{
                        String tospeak="Invalid Command ,Sir Please Repeat";
                        t1.setLanguage(Locale.US);
                        t1.setSpeechRate(0.8f);
                        Toast.makeText(getApplicationContext(), tospeak,Toast.LENGTH_SHORT).show();
                        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public void delete(){


        String a="drop table if exists " + "makeLingEntry" + ";";
        SQLiteDatabase mDataBase;
        mDataBase = openOrCreateDatabase("Lingdata", MODE_PRIVATE, null);
        if(mDataBase!=null) {
            mDataBase.execSQL(a);
        }


    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void saveToFile(String data) throws IOException {
            Context context=this;
            File path = context.getFilesDir();
            File file = new File(path, "Voice.txt");
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
           /*
           try {
           new File(path).mkdirs();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
        }  catch(FileNotFoundException ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }  catch(IOException ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }*/
    }
    private void writeToSDFile(String disp){
        id++;
        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal
        File root = android.os.Environment.getExternalStorageDirectory();
        //tv.append("\nExternal file system root: "+root);
        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "File"+Integer.toString(id)+"Data.txt");
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(disp);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tospeak="File written to "+file;
        t1.setLanguage(Locale.US);
        t1.setSpeechRate(0.8f);
        t1.speak(tospeak, TextToSpeech.QUEUE_FLUSH, null);
        tv.setText("File written to "+file);
    }


   // I've written this eval method for arithmetic expressions to answer this question. It does addition, subtraction, multiplication, division, exponentiation (using the ^ symbol), and a few basic functions like sqrt. It supports grouping using (...), and it gets the operator precedence and associativity rules correct.

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('x')||eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.toLowerCase().equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.toLowerCase().equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.toLowerCase().equals("tan")||func.toLowerCase().equals("tangent")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected statement: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
