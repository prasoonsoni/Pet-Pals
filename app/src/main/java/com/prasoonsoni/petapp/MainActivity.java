package com.prasoonsoni.petapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prasoonsoni.petapp.fragments.QueryFragment;
import com.prasoonsoni.petapp.fragments.ProfileFragment;
import com.prasoonsoni.petapp.fragments.HomeFragment;
import com.prasoonsoni.petapp.fragments.RemainderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.prasoonsoni.petapp.R.drawable.ic_baseline_lock_open_24;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @BindView(R.id.bnv) BottomNavigationView bmv;
    public static FloatingActionButton addButton;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    @BindView(R.id.cd) CoordinatorLayout cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        addButton = findViewById(R.id.addButton);
        AnimationDrawable animationDrawable = (AnimationDrawable) cd.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        loadFragment(new HomeFragment());

        bmv.setBackground(null);
        bmv.getMenu().getItem(2).setEnabled(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                finish();
            }
        });
        bmv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_fragment:
                        loadFragment(new HomeFragment());
                        addButton.setImageResource(R.drawable.fab_btn);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, AddActivity.class));
                                finish();
                            }
                        });
                        return true;
                    case R.id.profile_fragment:
                        loadFragment(new ProfileFragment());
                        addButton.setImageResource(R.drawable.ic_logout);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAuth.signOut();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                        return true;
                    case R.id.chat_fragment:
                        loadFragment(new QueryFragment());
                        addButton.setImageResource(R.drawable.ic_send);
                        return true;
                    case R.id.remainder_fragment:
                        loadFragment(new RemainderFragment());
                        addButton.setImageResource(R.drawable.ic_add_reminder);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, AddRemainderActivity.class));
                                finish();
                            }
                        });
                        return true;

                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}