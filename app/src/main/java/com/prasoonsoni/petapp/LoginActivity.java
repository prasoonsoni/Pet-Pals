package com.prasoonsoni.petapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.EmailLayout) TextInputLayout EmailLayout;
    @BindView(R.id.Passwordlayout)TextInputLayout Passwordlayout;
    @BindView(R.id.Email) EditText Email;
    @BindView(R.id.Password) EditText Password;
    @BindView(R.id.logIn) Button logInBtnn;
    @BindView(R.id.signUp) Button signUpBtnn;
    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    @BindView(R.id.textView4) TextView textView4 ;
    @BindView(R.id.dogAnimation) LottieAnimationView dogAnimation;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        signUpBtnn.animate().translationXBy(-2000f).setDuration(1750);
        logInBtnn.animate().translationXBy(2000f).setDuration(1500);
        EmailLayout.animate().translationXBy(2000f).setDuration(1000);
        Passwordlayout.animate().translationXBy(-2000f).setDuration(1250);
        linearLayout.animate().alpha(1f).setDuration(1750);
        textView4.animate().translationYBy(1500f).setDuration(800);
        dogAnimation.animate().alpha(1f).setDuration(1750);
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please Wait");
        pd.setMessage("Logging in...");
        pd.setCancelable(false);
        signUpBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        logInBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                Email.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        EmailLayout.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().isEmpty()){
                            EmailLayout.setError("E-Mail cannot be empty.");
                        }
                    }
                });
                Password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Passwordlayout.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().isEmpty()){
                            Passwordlayout.setError("Password cannot be empty");
                        }
                    }
                });
                if(email.isEmpty() && password.isEmpty()){
                    EmailLayout.setError("E-Mail cannot be empty.");
                    Passwordlayout.setError("Password cannot be empty");
                } else if(email.isEmpty()){
                    EmailLayout.setError("E-Mail cannot be empty.");
                } else if(password.isEmpty()){
                    Passwordlayout.setError("Password cannot be empty");
                } else {
                    pd.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Successfully Logged in.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Failed : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}