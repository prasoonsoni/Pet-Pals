package com.prasoonsoni.petapp.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasoonsoni.petapp.R;
import com.prasoonsoni.petapp.adapters.ProfileAdapter;
import com.prasoonsoni.petapp.adapters.RemainderAdapter;
import com.prasoonsoni.petapp.models.ProfileModel;
import com.prasoonsoni.petapp.models.RemainderModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView profileTitle = root.findViewById(R.id.profileTitle);
        profileTitle.animate().translationXBy(2000f).setDuration(500);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("petsinfo").child(currentUser);
        RecyclerView petRecyclerView = root.findViewById(R.id.petRecyclerView);
        petRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<ProfileModel> list = new ArrayList<>();
        ProfileAdapter profileAdapter = new ProfileAdapter(list, getContext());
        petRecyclerView.setAdapter(profileAdapter);
        //petRecyclerView.animate().translationXBy(2000f).setDuration(600);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProfileModel profileModel = dataSnapshot.getValue(ProfileModel.class);
                    list.add(profileModel);
                }
                profileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return root;
    }
}
