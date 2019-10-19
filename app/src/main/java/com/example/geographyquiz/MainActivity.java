package com.example.geographyquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String QUIZ_KEY = "key";
    public static final int  BESERI_COG = 2;
    public static final int  FIZIKI_COG = 1;
    TextView tv;
    private  static final int DIALOG_HAKKINDA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
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
                dialog.setTitle("HAKKINDA");
                dialog.setContentView(R.layout.hakkinda);
                break;

            default:
                dialog=null;

        }
        return dialog;
    }

}
