package com.example.geographyquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    private RelativeLayout SoruContainer;
    private LinearLayout ButonContainer;
    private TextView ilerleme_tv,soru_tv;
    private Random random;
    private String DogruCevap;
    private final int TOPLAM_SORU_SAYISI=20;
    private Handler handler;
    private  int Toplam_Cevap_Sayisi,Dogru_Cevap_Sayisi,KacinciDenemedeBildi;
    private int quiz;
    private View.OnClickListener butonDinleyicisi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toplam_Cevap_Sayisi++;
            Button button = (Button) v;
            String verilenCevap = button.getText().toString();

            if (verilenCevap.equals(DogruCevap)){
              //verilen cevap doğruysa
                TebrikEt(KacinciDenemedeBildi);
                KacinciDenemedeBildi = 1;
                ButunButonlariEtkisizlestir();
                Dogru_Cevap_Sayisi++;

                // soru son soruysa (1500 milisaniye sonra) dialog gösterilecek, değilse (2500 milisaniye sonra) diğer soruya geçilecek

                  if (Dogru_Cevap_Sayisi == TOPLAM_SORU_SAYISI)
                {
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            diaolg();
                        }
                    },1500);
                }
                     else
                    {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SonrakiSoru();
                        }
                    },2500);
                }
            }
                else
                {
                //verilen cevap yanlış ise
                KacinciDenemedeBildi++;
                button.setEnabled(false);
                Animasyonlar.TitresimAnimasyonu(SoruContainer);
                }
        }
    };
    private void diaolg() {
        new AlertDialog.Builder(Quiz.this)
                .setTitle("Testi Bitirdiniz")
                .setMessage(getResources().getString(R.string.skor,Toplam_Cevap_Sayisi,(float)(2000/Toplam_Cevap_Sayisi)))
                .setPositiveButton("Testi Tekrar Başlat.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restQuiz();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Şimdilik yeter.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.cancel();
                    }
                }).show();
    }
    private void TebrikEt(int kacinciDenemedeBildi) {
        String TebrikMesaji = "";
        switch (kacinciDenemedeBildi){
            case 1: TebrikMesaji = "Mükemmelsin :)"; break;
            case 2: TebrikMesaji = "İyisin :)"; break;
            case 3: TebrikMesaji = "Fena Değilsin :("; break;
            case 4: TebrikMesaji = "Nihayet :("; break;
        }
        soru_tv.setText(TebrikMesaji);
        soru_tv.setTextColor(Color.GREEN);
        soru_tv.setTextSize(18);
        Animasyonlar.TebrikAnimasyonu(soru_tv);
    }
    private void ButunButonlariEtkisizlestir() {
        for(int satir = 0; satir<ButonContainer.getChildCount();satir++)
        {
            Button button = (Button) ButonContainer.getChildAt(satir);
            button.setEnabled(false);
        }
    }
    private List<String> genelListe, soruListesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quiz = getIntent().getIntExtra(MainActivity.QUIZ_KEY,MainActivity.FIZIKI_COG);
        genelListe = new ArrayList<String>();
        soruListesi = new ArrayList<String>();
        random = new Random();
        handler = new Handler();
        KacinciDenemedeBildi = 1;
        SoruContainer = findViewById(R.id.rl);
        ButonContainer = findViewById(R.id.buton_container);
        ilerleme_tv = findViewById(R.id.ilerleme_tv);
        soru_tv = findViewById(R.id.soru_tv);
        for(int satir = 0; satir<ButonContainer.getChildCount();satir++)
        {
            Button button = (Button) ButonContainer.getChildAt(satir);
            button.setOnClickListener(butonDinleyicisi);
        }
        ilerleme_tv.setText("1 / "+ TOPLAM_SORU_SAYISI);
        switch (quiz){
            case MainActivity.FIZIKI_COG:
                soru_tv.setTextSize(18);
                String [] dizi1 = getResources().getStringArray(R.array.FizikiCog);
                for (String s:dizi1){
                    genelListe.add(s);
                }
                break;
                case  MainActivity.BESERI_COG:
                    soru_tv.setTextSize(18);
                    String [] dizi2 = getResources().getStringArray(R.array.BeseriCog);
                    for (String s:dizi2){
                        genelListe.add(s);
                    }
                    break;
        }
           restQuiz();
    } //create methodu sonu
    private void restQuiz() {
        Toplam_Cevap_Sayisi = 0;
        Dogru_Cevap_Sayisi = 0;
        soruListesi.clear();
        int sorusayisi = 1;
        int ListedekiElemanSayisi =genelListe.size();
        //döngü ile genel listeden rastgele birbirinden farklı 10 tane eleman seçilip soruListesi'ne ekleniyor.
        while (sorusayisi <= TOPLAM_SORU_SAYISI){
            int index = random.nextInt(ListedekiElemanSayisi);
            String soru = genelListe.get(index);

            if (!soruListesi.contains(soru)) {
                soruListesi.add(soru);
                sorusayisi++;
            }
        } //döngü sonu
        SonrakiSoru();
    } //restQuiz methodu sonu
    private void SonrakiSoru() {
        if (quiz == MainActivity.FIZIKI_COG) {
            soru_tv.setTextSize(18);
        }
        soru_tv.setTextColor(Color.parseColor("#444444"));
        ilerleme_tv.setText(Dogru_Cevap_Sayisi + 1 + " / "+TOPLAM_SORU_SAYISI);
        String soru = soruListesi.remove(0);
        soru_tv.setText(getSoru(soru));
        DogruCevap = getCevap(soru);
        // butonlara seçenekler belirlenecek
        Collections.shuffle(genelListe); //genel liste karıştırılıyor
        int DogruCevabinIndisi = genelListe.indexOf(soru);
        //dogru cevabı kesip listenin sonuna ekliyor 
        genelListe.add(genelListe.remove(DogruCevabinIndisi));
        // döngü ile butonlara seçenekler ekleniyor fakat içinde doğru cevap yok
        for (int satir = 0; satir<ButonContainer.getChildCount();satir++){
            Button button = (Button) ButonContainer.getChildAt(satir);
            String secenek = genelListe.get(satir);
            button.setText(getCevap(secenek));
            button.setEnabled(true);
        }
        // doğru cevabı da rastgele bir butona ekleyelim
        int rastgeleIndisi = random.nextInt(4);
        ((Button)ButonContainer.getChildAt(rastgeleIndisi)).setText(DogruCevap);
        SoruAnimasyonu();
    } //sonrakisoru methodu sonu.
    private void SoruAnimasyonu() {
        List<View> butonlar = new ArrayList<View>();
        for (int satir = 0; satir<ButonContainer.getChildCount();satir++){
            Button button = (Button) ButonContainer.getChildAt(satir);
            butonlar.add(button);
        }
        Animasyonlar.yaziAnimasyonu(soru_tv,random.nextInt(29));
        Animasyonlar.butonlarAnimasyonu(butonlar);
    }
    //- işartetinden öncesini kesip alıyoruz
    private String getSoru(String soru) {
        soru = soru.substring(0,soru.indexOf("-"));
        return soru;
    }
    //- işartetinden sonrasını kesip alıyoruz
    private String getCevap(String soru) {
        soru = soru.substring(soru.indexOf("-")+1,soru.length());
        return  soru;
    }




}
