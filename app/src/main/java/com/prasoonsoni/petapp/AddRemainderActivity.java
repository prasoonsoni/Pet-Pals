package com.prasoonsoni.petapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.annotation.AnnotationFormatError;
import java.util.Calendar;
import java.util.HashMap;

public class AddRemainderActivity extends AppCompatActivity {
    EditText titleText, descriptionText, animalText, timeText;
    int hour, minute;
    TextInputLayout forAnimalLayout;
    TextInputLayout descriptionLayout;
    TextInputLayout titleLayout;
    TextInputLayout timer;
    TextView textView6;
    public static Button goBack, addRemainder;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remainder);
        textView6 = findViewById(R.id.textView6);
        titleText = findViewById(R.id.title);
        descriptionText = findViewById(R.id.description);
        animalText = findViewById(R.id.forAnimal);
        timeText = findViewById(R.id.time);
        timer = findViewById(R.id.timeLayout);
        goBack = findViewById(R.id.goBackk);
        addRemainder = findViewById(R.id.addRemainder);
        forAnimalLayout = findViewById(R.id.forAnimalLayout);
        descriptionLayout = findViewById(R.id.descriptionLayout);
        titleLayout = findViewById(R.id.titleLayout);
        titleLayout.animate().translationXBy(2000f).setDuration(1200);
        forAnimalLayout.animate().translationXBy(2000f).setDuration(1600);
        descriptionLayout.animate().translationXBy(-2000f).setDuration(1400);
        timer.animate().translationXBy(-2000f).setDuration(1800);
        addRemainder.animate().translationXBy(2000f).setDuration(2000);
        goBack.animate().translationXBy(-2000f).setDuration(2000);
        textView6.animate().translationYBy(2000f).setDuration(1000);
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please Wait");
        pd.setMessage("Adding Reminder...");
        pd.setCancelable(false);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRemainderActivity.this, "Empty reminder discarded.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddRemainderActivity.this, MainActivity.class));
                finish();
            }
        });
        addRemainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleText.getText().toString();
                String description = descriptionText.getText().toString();
                String animal = animalText.getText().toString();
                String time = timeText.getText().toString();
                String userID = mAuth.getCurrentUser().getUid().toString();
                if(title.isEmpty() || description.isEmpty() || animal.isEmpty() || time.isEmpty()){
                    Toast.makeText(AddRemainderActivity.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    pd.show();
                    DatabaseReference storeValue = FirebaseDatabase.getInstance().getReference().child("reminders").child(userID).child(title);
                    HashMap<String, String> info = new HashMap<>();
                    info.put("title", title);
                    info.put("description", description);
                    info.put("animal", animal);
                    info.put("time", time);
                    storeValue.setValue(info).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            titleText.setText("");
                            descriptionText.setText("");
                            animalText.setText("");
                            timeText.setText("");
                            Toast.makeText(AddRemainderActivity.this, "Successfully Added!!", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            startActivity(new Intent(AddRemainderActivity.this, MainActivity.class));
                            finish();

                        }
                    });
                }
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddRemainderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,hour,minute);
                                timeText.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        }, 12,0,false
                );
                timePickerDialog.updateTime(hour,minute);
                timePickerDialog.show();
            }
        });
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddRemainderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hour = hourOfDay;
                                minute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,hour,minute);
                                timeText.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        }, 12,0,false
                );
                timePickerDialog.updateTime(hour,minute);
                timePickerDialog.show();
            }
        });
    }
}