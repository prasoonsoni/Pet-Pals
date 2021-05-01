package com.prasoonsoni.petapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class TrainActivity extends AppCompatActivity {
    Switch switchButton;
    CardView catCardView, dogCardView;
    TextView dog, cat;
    ImageView backBtn;
    LinearLayout dgs, header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        switchButton = findViewById(R.id.switchButton);
        catCardView = findViewById(R.id.catCardView);
        dogCardView = findViewById(R.id.dogCardView);
        dog = findViewById(R.id.dog);
        cat = findViewById(R.id.cat);
        backBtn = findViewById(R.id.backBtn);
        dgs = findViewById(R.id.dgs);
        header = findViewById(R.id.header);
        header.animate().translationXBy(2000f).setDuration(500);
        dgs.animate().translationXBy(2000f).setDuration(600);
        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(TrainActivity.this, MainActivity.class));
            finish();
        });
        dogCardView.animate().translationXBy(2000f).setDuration(700);
        switchButton.setOnClickListener(v -> {
            if(switchButton.isChecked()){
                dog.setTextColor(Color.parseColor("#00B1FF"));
                cat.setTextColor(Color.parseColor("#0046FF"));
                dogCardView.animate().translationXBy(-2000f).setDuration(700);
                catCardView.animate().translationXBy(-2000f).setDuration(700);
            } else {
                cat.setTextColor(Color.parseColor("#00B1FF"));
                dog.setTextColor(Color.parseColor("#0046FF"));
                catCardView.animate().translationXBy(2000f).setDuration(700);
                dogCardView.animate().translationXBy(2000f).setDuration(700);
            }
        });


    }
}