package com.prasoonsoni.petapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasoonsoni.petapp.R;
import com.prasoonsoni.petapp.adapters.RemainderAdapter;
import com.prasoonsoni.petapp.models.RemainderModel;

import java.util.ArrayList;

public class RemainderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_remainder, container, false);
        TextView remainderTitle = root.findViewById(R.id.remainderTitle);
        remainderTitle.animate().translationXBy(2000f).setDuration(500);
        LottieAnimationView noDataAnime = root.findViewById(R.id.noDataAnime);
        TextView noDataText = root.findViewById(R.id.noDataText);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("reminders").child(currentUser);
        RecyclerView remainderList = root.findViewById(R.id.remainderList);
        remainderList.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<RemainderModel> list = new ArrayList<>();
        RemainderAdapter remainderAdapter = new RemainderAdapter(list, getContext());
        remainderList.setAdapter(remainderAdapter);
        remainderList.animate().translationXBy(2000f).setDuration(600);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        RemainderModel remainderModel = dataSnapshot.getValue(RemainderModel.class);
                        list.add(remainderModel);
                    }
                    remainderAdapter.notifyDataSetChanged();
                    if(list.isEmpty()){
                        noDataAnime.setVisibility(View.VISIBLE);
                        noDataText.setVisibility(View.VISIBLE);
                        remainderList.setVisibility(View.INVISIBLE);
                    } else {
                        noDataAnime.setVisibility(View.INVISIBLE);
                        noDataText.setVisibility(View.INVISIBLE);
                        remainderList.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        return root;
    }
}
