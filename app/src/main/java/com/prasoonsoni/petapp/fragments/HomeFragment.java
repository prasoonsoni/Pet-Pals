package com.prasoonsoni.petapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasoonsoni.petapp.DiseaseActivity;
import com.prasoonsoni.petapp.LoginActivity;
import com.prasoonsoni.petapp.QRcodeActivity;
import com.prasoonsoni.petapp.TrainActivity;
import com.prasoonsoni.petapp.adapters.PetAdapter;
import com.prasoonsoni.petapp.models.PetModel;
import com.prasoonsoni.petapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView allPets;

    public HomeFragment() {
    }

    private FirebaseAuth mAuth;
    PetAdapter petAdapter;
    ConstraintLayout notAvailable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        allPets = root.findViewById(R.id.allPetsRecyclerView);
        notAvailable = root.findViewById(R.id.noAvailable);
        FirebaseUser base = mAuth.getCurrentUser();
        TextView HomeTitle = root.findViewById(R.id.homeTitle);
        HomeTitle.animate().translationXBy(2000f).setDuration(500);
        CardView myPets = root.findViewById(R.id.myPets);
        myPets.animate().translationXBy(2000f).setDuration(600);
        LinearLayout ourServices = root.findViewById(R.id.ourServices);
        ourServices.animate().translationXBy(2000f).setDuration(700);
        CardView cv1 = root.findViewById(R.id.qrCardView);
        cv1.animate().translationXBy(2000f).setDuration(800);
        CardView cv2 = root.findViewById(R.id.trainCardView);
        cv2.animate().translationXBy(-2000f).setDuration(800);
        CardView cv3 = root.findViewById(R.id.knowAnimalCardView);
        cv3.animate().translationXBy(2000f).setDuration(900);
        CardView cv4 = root.findViewById(R.id.diseaseCardView);
        cv4.animate().translationXBy(-2000f).setDuration(900);
       if(mAuth.getCurrentUser()!=null){
           String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
           DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("petsinfo").child(currentUser);
           ArrayList<PetModel> list = new ArrayList<>();
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
           linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
           allPets.setLayoutManager(linearLayoutManager);
           PetAdapter petAdapter = new PetAdapter(list, getContext());
           allPets.setAdapter(petAdapter);
           cv4.setOnClickListener(v -> {
               startActivity(new Intent(getContext(), DiseaseActivity.class));
           });
           cv1.setOnClickListener(v -> startActivity(new Intent(getContext(), QRcodeActivity.class)));
           cv2.setOnClickListener(v -> {
               startActivity(new Intent(getContext(), TrainActivity.class));
           });
           db.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                       PetModel model = dataSnapshot.getValue(PetModel.class);
                       list.add(model);
                   }
                   petAdapter.notifyDataSetChanged();
                   if (list.isEmpty()){
                       allPets.setVisibility(View.INVISIBLE);
                       notAvailable.setVisibility(View.VISIBLE);
                   } else {
                       allPets.setVisibility(View.VISIBLE);
                       notAvailable.setVisibility(View.INVISIBLE);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       } else {
           startActivity(new Intent(getContext(), LoginActivity.class));

       }


        return root;
    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser base = mAuth.getCurrentUser();
//        if(base!=null){
//            petAdapter.startListening();
//        }
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        FirebaseUser base = mAuth.getCurrentUser();
//        if(base!=null){
//            petAdapter.stopListening();
//        }
//    }
}
