package com.prasoonsoni.petapp.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prasoonsoni.petapp.AddActivity;
import com.prasoonsoni.petapp.MainActivity;
import com.prasoonsoni.petapp.R;
import com.prasoonsoni.petapp.models.ProfileModel;

import java.util.ArrayList;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    ArrayList<ProfileModel> list = new ArrayList<>();
    Context context;

    public ProfileAdapter(ArrayList<ProfileModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProfileModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.animal.setText(model.getAnimal());
        holder.breed.setText(model.getBreed());
        holder.born.setText(model.getBorn());
        holder.weight.setText(model.getWeight()+" KG");
        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .into(holder.image);
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("animal", model.getAnimal());
                intent.putExtra("breed", model.getBreed());
                intent.putExtra("born", model.getBorn());
                intent.putExtra("weight", model.getWeight());
                intent.putExtra("image", model.getImage());

                context.startActivity(intent);

            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(context);
                pd.setIndeterminate(true);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setTitle("Please Wait");
                pd.setMessage("Deleting your pet information...");
                pd.setCancelable(false);
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_info)
                        .setTitle("Are you sure?")
                        .setMessage("Deleting Pet information will cause you data loss. You won't be able to get it again")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("petsinfo")
                                        .child(user)
                                        .child(model.getName())
                                        .removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                context.startActivity(new Intent(context, MainActivity.class));
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image, deleteButton, editButton;
        TextView name, animal, breed, born, weight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteButton = itemView.findViewById(R.id.dltButton);
            editButton = itemView.findViewById(R.id.editButton);
            image = itemView.findViewById(R.id.animalImage);
            name = itemView.findViewById(R.id.animalName);
            animal = itemView.findViewById(R.id.animalType);
            breed = itemView.findViewById(R.id.animalBreed);
            born = itemView.findViewById(R.id.animalDOB);
            weight = itemView.findViewById(R.id.animalWeight);
        }
    }
}
