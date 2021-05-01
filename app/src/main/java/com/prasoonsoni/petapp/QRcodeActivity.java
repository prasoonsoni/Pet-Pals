package com.prasoonsoni.petapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRcodeActivity extends AppCompatActivity {
    EditText yourName, yourNumber, yourAddress;
    EditText yourPetName, yourPetBreed;
    Button generateButton, saveButton;
    ImageView QRimg, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode);
        yourName = findViewById(R.id.yourName);
        yourNumber = findViewById(R.id.yourNumber);
        yourAddress = findViewById(R.id.yourAddress);
        yourPetName = findViewById(R.id.yourPetName);
        yourPetBreed = findViewById(R.id.yourPetBreed);
        generateButton = findViewById(R.id.generateButton);
        saveButton = findViewById(R.id.saveButton);
        QRimg = findViewById(R.id.QRimg);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            startActivity(new Intent(QRcodeActivity.this, MainActivity.class));
            finish();
        });

        generateButton.setOnClickListener(v -> {
            String yName = yourName.getText().toString();
            String yNumber = yourNumber.getText().toString();
            String yAddress = yourAddress.getText().toString();
            String yPetName = yourPetName.getText().toString();
            String yPetBreed = yourPetBreed.getText().toString();
            String data = "Owner Name: " + yName + "\n"
                    + "Owner Number: " + yNumber + "\n"
                    + "Owner Address: " + yAddress + "\n"
                    + "Pet Name: " + yPetName + "\n"
                    + "Pet Breed: " + yPetBreed + "\n";
            if(yName.isEmpty()||yNumber.isEmpty()||yAddress.isEmpty()||yPetName.isEmpty()||yPetBreed.isEmpty()){
                Toast.makeText(QRcodeActivity.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
            } else {
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE,350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitMap = encoder.createBitmap(matrix);
                    QRimg.setImageBitmap(bitMap);
                    saveButton.setEnabled(true);
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(yourPetBreed.getApplicationWindowToken(),0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fileOutputStream=null;
                File file=getdisc();
                if (!file.exists() && !file.mkdirs())
                {
                    Toast.makeText(getApplicationContext(),"sorry can not make dir",Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymmsshhmmss");
                String date=simpleDateFormat.format(new Date());
                String name="img"+date+".jpeg";
                String file_name=file.getAbsolutePath()+"/"+name; File new_file=new File(file_name);
                try {
                    fileOutputStream =new FileOutputStream(new_file);
                    Bitmap bitmap=viewToBitmap(QRimg,QRimg.getWidth(),QRimg.getHeight());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    Toast.makeText(getApplicationContext(),"QR Code saved to gallery.", Toast.LENGTH_LONG).show();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                catch
                (FileNotFoundException e) {

                } catch (IOException e) {

                } refreshGallery(file);
            } private void refreshGallery(File file)
            { Intent i=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                i.setData(Uri.fromFile(file)); sendBroadcast(i);
            }
            private File getdisc(){
                File file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                return new File(file,"My Image");
            } });
    }
    private static Bitmap viewToBitmap(View view, int width, int height)
    {
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap); view.draw(canvas);
        return bitmap;
    }




}

//        saveButton.setOnClickListener(v ->{
//            BitmapDrawable drawable = (BitmapDrawable) QRimg.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//            FileOutputStream outputStream = null;
//            File filePath = Environment.getExternalStorageDirectory();
//            File dir = new File(filePath.getAbsolutePath()+"/PetQR/");
//            dir.mkdirs();
//            String fileName = String.format("%d.png", System.currentTimeMillis());
//
//            File outFile = new File(dir, fileName);
//            try {
//                outputStream = new FileOutputStream(outFile);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//            Toast.makeText(getApplicationContext(), "Image saved to Internal!", Toast.LENGTH_SHORT).show();
//            try {
//                outputStream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//
//
//    }
//}