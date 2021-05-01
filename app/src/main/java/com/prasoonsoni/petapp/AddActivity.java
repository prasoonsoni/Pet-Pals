package com.prasoonsoni.petapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    EditText name, animal, breed, born, weight;
    Button addButton, goBack, chooseButton;
    ImageView petImage;
    @BindView(R.id.NameLayout) TextInputLayout NameLayout;
    @BindView(R.id.BreedLayout)TextInputLayout BreedLayout;
    @BindView(R.id.AnimalLayout) TextInputLayout AnimalLayout;
    @BindView(R.id.BornLayout) TextInputLayout BornLayout;
    @BindView(R.id.WeigthLayout) TextInputLayout WeigthLayout;
    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.textView5) TextView textView5;

    boolean imageChanged;

    private Uri filePath;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        name = findViewById(R.id.name);
        animal = findViewById(R.id.animal);
        breed = findViewById(R.id.breed);
        born = findViewById(R.id.born);
        weight = findViewById(R.id.weigth);
        addButton = findViewById(R.id.addButton);
        goBack = findViewById(R.id.goBack);
        chooseButton = findViewById(R.id.chooseButton);
        petImage = findViewById(R.id.petImage);
        ////////////////////////////////////////////////
        boolean dataChanged = false;
        imageChanged = false;
        try {
            Bundle bundle = getIntent().getExtras();
            String eName = bundle.getString("name");
            String eAnimal = bundle.getString("animal");
            String eBreed = bundle.getString("breed");
            String eBorn = bundle.getString("born");
            String eWeight = bundle.getString("weight");
            String eImage = bundle.getString("image");
            if(!eName.isEmpty() && !eAnimal.isEmpty() && !eBreed.isEmpty() && !eBorn.isEmpty()
                    && !eWeight.isEmpty() && !eImage.isEmpty()){
                name.setText(eName);
                animal.setText(eAnimal);
                breed.setText(eBreed);
                born.setText(eBorn);
                weight.setText(eWeight);
                //Glide.with(petImage.getContext()).load(eImage).into(petImage);
                dataChanged = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dataChanged)
            addButton.setText("Update");


        NameLayout.animate().translationXBy(2000f).setDuration(1250);
        AnimalLayout.animate().translationXBy(2000f).setDuration(1250);
        BornLayout.animate().translationXBy(2000f).setDuration(1500);
        BreedLayout.animate().translationXBy(-2000f).setDuration(1250);
        WeigthLayout.animate().translationXBy(-2000f).setDuration(1500);
        goBack.animate().translationXBy(-2000f).setDuration(1750);
        addButton.animate().translationXBy(2000f).setDuration(1750);
        chooseButton.animate().translationXBy(-2000f).setDuration(1150);
        cardView.animate().translationXBy(2000f).setDuration(1150);
        textView5.animate().translationYBy(2000f).setDuration(750);


        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please Wait");
        pd.setCancelable(false);

        chooseButton.setOnClickListener(v -> chooseImage());

        goBack.setOnClickListener(v -> {
            Toast.makeText(AddActivity.this, "Empty details discarded.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddActivity.this, MainActivity.class));
            finish();
        });

        addButton.setOnClickListener(v -> {
            pd.setMessage("Adding Pet...");
            pd.show();

            String petName = name.getText().toString();
            String petType = animal.getText().toString();
            String petBreed = breed.getText().toString();
            String petBorn = born.getText().toString();
            String petWeight = weight.getText().toString();
            String userID = mAuth.getCurrentUser().getUid();




            if(petName.isEmpty() || petType.isEmpty() || petBreed.isEmpty() ||
                    petBorn.isEmpty() || petWeight.isEmpty() || userID.isEmpty() || filePath == null){
                Toast.makeText(AddActivity.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            } else {
                HashMap<String, String> info = new HashMap<>();
                info.put("name", petName);
                info.put("animal",petType);
                info.put("breed", petBreed);
                info.put("born", petBorn);
                info.put("weight",petWeight);
                pd.setMessage("Processing...");
                if(imageChanged)
                    uploadImage(userID, petName, info);
                else
                    uploadChangedData(userID, petName, info);
            }
        });
    }

    private void uploadChangedData(String userID, String petName, HashMap<String, String> info) {
        DatabaseReference storeValue = FirebaseDatabase.getInstance().getReference().child("petsinfo").child(userID).child(petName);
        storeValue.setValue(info).addOnCompleteListener(task1 -> {
            Toast.makeText(AddActivity.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddActivity.this, MainActivity.class));
            finish();
            pd.dismiss();
        });
    }

    private void uploadImage(String userID, String petName, HashMap<String, String> info) {
        StorageReference folder = FirebaseStorage.getInstance().getReference();
        StorageReference imageName = folder.child("pets").child("image" + filePath.getLastPathSegment());
        imageName.putFile(filePath).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                pd.dismiss();
                Log.e("info", "line170");
                throw task.getException();

            }
            return imageName.getDownloadUrl();
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    String downloadUrl = task.getResult().toString();
                    info.put("image", downloadUrl);
                    DatabaseReference storeValue = FirebaseDatabase.getInstance().getReference().child("petsinfo").child(userID).child(petName);
                    storeValue.setValue(info).addOnCompleteListener(task1 -> {
                        Toast.makeText(AddActivity.this, "Added Successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this, MainActivity.class));
                        finish();
                        pd.dismiss();
                    });
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityIfNeeded(Intent.createChooser(intent, "Select Image"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                petImage.setImageBitmap(bitmap);
                imageChanged = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}