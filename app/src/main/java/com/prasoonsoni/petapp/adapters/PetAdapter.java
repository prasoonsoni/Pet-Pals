package com.prasoonsoni.petapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.prasoonsoni.petapp.R;
import com.prasoonsoni.petapp.models.PetModel;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {
    ArrayList<PetModel> list = new ArrayList<>();
    Context context;

    public PetAdapter(ArrayList<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PetModel model = list.get(position);
        holder.pName.setText(model.getName());
        holder.pAnimal.setText(model.getAnimal());
        holder.pBreed.setText(model.getBreed());
        holder.pBorn.setText(model.getBorn());
        holder.pWeight.setText(model.getWeight()+" KG");
        Glide.with(holder.pImage.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .into(holder.pImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView pImage;
        TextView pName, pAnimal, pBreed, pBorn, pWeight;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.pImage);
            pName = itemView.findViewById(R.id.pName);
            pAnimal = itemView.findViewById(R.id.pAnimal);
            pBreed = itemView.findViewById(R.id.pBreed);
            pBorn = itemView.findViewById(R.id.pDOB);
            pWeight = itemView.findViewById(R.id.pWeight);
        }
    }

}
