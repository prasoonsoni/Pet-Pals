package com.prasoonsoni.petapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DiseaseActivity extends AppCompatActivity {
    CheckBox ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,ch10,ch11,ch12,ch13,ch14,ch15,ch16,ch17,ch18,ch19;
    Button check;
    ImageView backButton, previous;
    TextView diseaseName, medicineName, select;
    CardView diseaseCard;
    LinearLayout header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        ch1 = findViewById(R.id.ch1);
        ch2 = findViewById(R.id.ch2);
        ch3 = findViewById(R.id.ch3);
        ch4 = findViewById(R.id.ch4);
        ch5 = findViewById(R.id.ch5);
        ch6 = findViewById(R.id.ch6);
        ch7 = findViewById(R.id.ch7);
        ch8 = findViewById(R.id.ch8);
        ch9 = findViewById(R.id.ch9);
        ch10 = findViewById(R.id.ch10);
        ch11 = findViewById(R.id.ch11);
        ch12 = findViewById(R.id.ch12);
        ch13 = findViewById(R.id.ch13);
        ch14 = findViewById(R.id.ch14);
        ch15 = findViewById(R.id.ch15);
        ch16 = findViewById(R.id.ch16);
        ch17 = findViewById(R.id.ch17);
        ch18 = findViewById(R.id.ch18);
        ch19 = findViewById(R.id.ch19);
        check = findViewById(R.id.check);
        backButton = findViewById(R.id.backBtn);
        diseaseName = findViewById(R.id.diseaseName);
        medicineName = findViewById(R.id.medicineName);
        diseaseCard = findViewById(R.id.diseaseCard);
        previous = findViewById(R.id.previous);
        header = findViewById(R.id.header);
        select = findViewById(R.id.select);

        header.animate().translationXBy(2000f).setDuration(100);
        ch1.animate().translationXBy(2000f).setDuration(200);
        ch3.animate().translationXBy(2000f).setDuration(300);
        ch5.animate().translationXBy(2000f).setDuration(400);
        ch7.animate().translationXBy(2000f).setDuration(500);
        ch9.animate().translationXBy(2000f).setDuration(600);
        ch11.animate().translationXBy(2000f).setDuration(700);
        ch13.animate().translationXBy(2000f).setDuration(800);
        ch15.animate().translationXBy(2000f).setDuration(900);
        ch17.animate().translationXBy(2000f).setDuration(1000);
        ch19.animate().translationXBy(2000f).setDuration(1100);

        select.animate().translationXBy(-2000f).setDuration(100);
        ch2.animate().translationXBy(-2000f).setDuration(200);
        ch4.animate().translationXBy(-2000f).setDuration(300);
        ch6.animate().translationXBy(-2000f).setDuration(400);
        ch8.animate().translationXBy(-2000f).setDuration(500);
        ch10.animate().translationXBy(-2000f).setDuration(600);
        ch12.animate().translationXBy(-2000f).setDuration(700);
        ch14.animate().translationXBy(-2000f).setDuration(800);
        ch16.animate().translationXBy(-2000f).setDuration(900);
        ch18.animate().translationXBy(-2000f).setDuration(1000);
        check.animate().translationXBy(-2000f).setDuration(1100);

        previous.setOnClickListener(v -> {
            goBack();
        });

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(DiseaseActivity.this, MainActivity.class));
            finish();
        });
        check.setOnClickListener(v -> {
            if (!ch1.isChecked()&&!ch2.isChecked()&&!ch3.isChecked()&&!ch4.isChecked()&&!ch5.isChecked()&&!ch6.isChecked()&&
                    !ch7.isChecked()&&!ch8.isChecked()&&!ch9.isChecked()&&!ch10.isChecked()&&!ch11.isChecked()&&!ch12.isChecked()&&
                    !ch13.isChecked()&&!ch14.isChecked()&&!ch15.isChecked()&&!ch16.isChecked()&&!ch17.isChecked()&&!ch18.isChecked()&&
                    !ch19.isChecked()){
                Toast.makeText(DiseaseActivity.this,"You did not select any symptom",Toast.LENGTH_SHORT).show();
            } else if(ch5.isChecked() && ch7.isChecked() && ch8.isChecked() && ch13.isChecked() && ch14.isChecked() && ch19.isChecked()){
                diseaseName.setText("Anthrax");
                medicineName.setText("1. Penicillin\n" +
                        "2. Doxycycline\n" +
                        "3. Ciprofloxacin");
                getDisease();
            } else if(ch2.isChecked() && ch4.isChecked() && ch5.isChecked() && ch14.isChecked() && ch15.isChecked() && ch16.isChecked() && ch17.isChecked() && ch18.isChecked()){
                diseaseName.setText("Blackquarter");
                medicineName.setText("1. Aluminium Hydroxide Gel\n" +
                        "2. Crystalline penicillin\n" +
                        "3. Non-steroidal anti-inflammatory drugs");
                getDisease();
            } else if(ch5.isChecked() && ch9.isChecked() && ch16.isChecked()){
                diseaseName.setText("Foot & Mouth");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch2.isChecked() && ch4.isChecked() && ch6.isChecked() && ch18.isChecked()){
                diseaseName.setText("Rabbies");
                medicineName.setText("1. Zoetis\n" +
                        "2. Defensor\n" +
                        "3. Nobivac Rabies");
                getDisease();
            } else if(ch3.isChecked() && ch5.isChecked() && ch9.isChecked() && ch10.isChecked() && ch13.isChecked() && ch15.isChecked()){
                diseaseName.setText("Blue Tongue");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch11.isChecked()){
                diseaseName.setText("Brucellosis");
                medicineName.setText("1. Doxycycline\n" +
                        "2. Rifampin\n" +
                        "3. Trimethoprim");
                getDisease();
            } else if(ch11.isChecked() && ch2.isChecked() && ch3.isChecked() && ch14.isChecked() &&ch16.isChecked()){
                diseaseName.setText("Listerosis");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch1.isChecked() && ch11.isChecked() && ch13.isChecked()){
                diseaseName.setText("Vibriosis");
                medicineName.setText("1. Ceftazidime\n" +
                        "2. Cefotaxime\n" +
                        "3. Ceftriaxone");
                getDisease();
            } else if(ch1.isChecked() && ch2.isChecked() && ch6.isChecked() && ch10.isChecked()){
                diseaseName.setText("Mastitis");
                medicineName.setText("1. Cloxacillin\n" +
                        "2. Ampicillin\n" +
                        "3. Penicillin");
                getDisease();
            } else if(ch3.isChecked() && ch6.isChecked() && ch12.isChecked() && ch15.isChecked() && ch16.isChecked()){
                diseaseName.setText("Footrot");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch2.isChecked() && ch4.isChecked() && ch5.isChecked() && ch9.isChecked() && ch13.isChecked() && ch14.isChecked() && ch15.isChecked()){
                diseaseName.setText("Etiology");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch4.isChecked() && ch8.isChecked() && ch13.isChecked()){
                diseaseName.setText("IBR");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch2.isChecked() && ch3.isChecked() && ch4.isChecked() && ch5.isChecked() && ch17.isChecked()){
                diseaseName.setText("Tick Fever");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch5.isChecked() && ch13.isChecked() && ch15.isChecked()){
                diseaseName.setText("East Coast Fever");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch1.isChecked() && ch4.isChecked() && ch6.isChecked()){
                diseaseName.setText("Milk Fever");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch19.isChecked()){
                diseaseName.setText("Blot");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch2.isChecked() && ch4.isChecked() && ch5.isChecked() && ch13.isChecked() && ch15.isChecked()){
                diseaseName.setText("Distemper");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else if(ch4.isChecked() && ch8.isChecked() && ch13.isChecked()){
                diseaseName.setText("Calf Diphtheria");
                medicineName.setText("1. \n" +
                        "2. \n" +
                        "3. ");
                getDisease();
            } else {
                Toast.makeText(this, "Cannot find Disease", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void goBack(){
        diseaseCard.animate().translationYBy(2000f).setDuration(1000);

        header.animate().translationXBy(2000f).setDuration(100);
        ch1.animate().translationXBy(2000f).setDuration(200);
        ch3.animate().translationXBy(2000f).setDuration(300);
        ch5.animate().translationXBy(2000f).setDuration(400);
        ch7.animate().translationXBy(2000f).setDuration(500);
        ch9.animate().translationXBy(2000f).setDuration(600);
        ch11.animate().translationXBy(2000f).setDuration(700);
        ch13.animate().translationXBy(2000f).setDuration(800);
        ch15.animate().translationXBy(2000f).setDuration(900);
        ch17.animate().translationXBy(2000f).setDuration(1000);
        ch19.animate().translationXBy(2000f).setDuration(1100);


        select.animate().translationXBy(-2000f).setDuration(100);
        ch2.animate().translationXBy(-2000f).setDuration(200);
        ch4.animate().translationXBy(-2000f).setDuration(300);
        ch6.animate().translationXBy(-2000f).setDuration(400);
        ch8.animate().translationXBy(-2000f).setDuration(500);
        ch10.animate().translationXBy(-2000f).setDuration(600);
        ch12.animate().translationXBy(-2000f).setDuration(700);
        ch14.animate().translationXBy(-2000f).setDuration(800);
        ch16.animate().translationXBy(-2000f).setDuration(900);
        ch18.animate().translationXBy(-2000f).setDuration(1000);
        check.animate().translationXBy(-2000f).setDuration(1100);
        ch1.setChecked(false);
        ch2.setChecked(false);
        ch3.setChecked(false);
        ch4.setChecked(false);
        ch5.setChecked(false);
        ch6.setChecked(false);
        ch7.setChecked(false);
        ch8.setChecked(false);
        ch9.setChecked(false);
        ch10.setChecked(false);
        ch11.setChecked(false);
        ch12.setChecked(false);
        ch13.setChecked(false);
        ch14.setChecked(false);
        ch15.setChecked(false);
        ch16.setChecked(false);
        ch17.setChecked(false);
        ch18.setChecked(false);
        ch19.setChecked(false);


    }

    public void getDisease(){
        diseaseCard.animate().translationYBy(-2000f).setDuration(1000);
        header.animate().translationXBy(-2000f).setDuration(100);
        ch1.animate().translationXBy(-2000f).setDuration(200);
        ch3.animate().translationXBy(-2000f).setDuration(300);
        ch5.animate().translationXBy(-2000f).setDuration(400);
        ch7.animate().translationXBy(-2000f).setDuration(500);
        ch9.animate().translationXBy(-2000f).setDuration(600);
        ch11.animate().translationXBy(-2000f).setDuration(700);
        ch13.animate().translationXBy(-2000f).setDuration(800);
        ch15.animate().translationXBy(-2000f).setDuration(900);
        ch17.animate().translationXBy(-2000f).setDuration(1000);
        ch19.animate().translationXBy(-2000f).setDuration(1100);

        select.animate().translationXBy(2000f).setDuration(100);
        ch2.animate().translationXBy(2000f).setDuration(200);
        ch4.animate().translationXBy(2000f).setDuration(300);
        ch6.animate().translationXBy(2000f).setDuration(400);
        ch8.animate().translationXBy(2000f).setDuration(500);
        ch10.animate().translationXBy(2000f).setDuration(600);
        ch12.animate().translationXBy(2000f).setDuration(700);
        ch14.animate().translationXBy(2000f).setDuration(800);
        ch16.animate().translationXBy(2000f).setDuration(900);
        ch18.animate().translationXBy(2000f).setDuration(1000);
        check.animate().translationXBy(2000f).setDuration(1100);
    }
}