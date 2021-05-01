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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.rEmailLayout) TextInputLayout rEmailLayout;
    @BindView(R.id.rPasswordlayout)TextInputLayout rPasswordlayout;
    @BindView(R.id.rEmail) EditText rEmail;
    @BindView(R.id.rPassword) EditText rPassword;
    @BindView(R.id.rLogIn) Button logInBtn;
    @BindView(R.id.rSignUp) Button signUpBtn;
    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    @BindView(R.id.textView2) TextView textView2 ;
    @BindView(R.id.catAnimation) LottieAnimationView catAnimation;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        catAnimation.animate().alpha(1f).setDuration(1750);
        mAuth = FirebaseAuth.getInstance();
        signUpBtn.animate().translationXBy(2000f).setDuration(1500);
        logInBtn.animate().translationXBy(-2000f).setDuration(1750);
        rEmailLayout.animate().translationXBy(2000f).setDuration(1000);
        rPasswordlayout.animate().translationXBy(-2000f).setDuration(1250);
        linearLayout.animate().alpha(1f).setDuration(1000);
        textView2.animate().translationYBy(1500f).setDuration(800);
        pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please Wait");
        pd.setMessage("Signing up...");
        pd.setCancelable(false);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = rEmail.getText().toString();
                String password = rPassword.getText().toString();
                rEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        rEmailLayout.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().isEmpty()){
                            rEmailLayout.setError("E-Mail cannot be empty.");
                        }
                    }
                });
                rPassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        rPasswordlayout.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().isEmpty()){
                            rPasswordlayout.setError("Password cannot be empty");
                        }
                    }
                });
                if(email.isEmpty() && password.isEmpty()){
                    rEmailLayout.setError("E-Mail cannot be empty.");
                    rPasswordlayout.setError("Password cannot be empty");
                } else if(email.isEmpty()){
                    rEmailLayout.setError("E-Mail cannot be empty.");
                } else if(password.isEmpty()){
                    rPasswordlayout.setError("Password cannot be empty");
                } else {
                    pd.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration Successful, You can login now", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration Failed : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}