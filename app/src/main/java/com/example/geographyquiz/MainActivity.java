package com.example.geographyquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    public static final String QUIZ_KEY = "key";
    public static final int  BESERI_COG = 2;
    public static final int  FIZIKI_COG = 1;
    private TextView tv;
    private Button demo;
    private  static final int DIALOG_HAKKINDA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.Mactionbar));
        demo =findViewById(R.id.btn);
        MobileAds.initialize(MainActivity.this,
                "ca-app-pub-7249150109836276~7127985050");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7249150109836276/2454845514");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        tv = findViewById(R.id.tv);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
    }
    public  void  testClick(View view){
        Intent intent = new Intent(getApplicationContext(),Quiz.class);
     switch (view.getId()){
         case R.id.fab1:
             intent.putExtra(QUIZ_KEY,FIZIKI_COG);
             startActivity(intent);
             break;
         case R.id.fab2:
             intent.putExtra(QUIZ_KEY,BESERI_COG);
             startActivity(intent);
             break;
     }
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id;
        id = item.getItemId();
        switch (id){
            case R.id.hakkinda:
                showDialog(DIALOG_HAKKINDA);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id){
            case DIALOG_HAKKINDA:
                dialog = new Dialog(MainActivity.this);
                dialog.setTitle(getResources().getString(R.string.Hakinda));
                dialog.setContentView(R.layout.hakkinda);
                break;
            default:
                dialog=null;

        }
        return dialog;
    }

}
