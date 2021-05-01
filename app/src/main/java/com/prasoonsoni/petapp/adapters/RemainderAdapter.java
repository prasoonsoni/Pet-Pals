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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prasoonsoni.petapp.MainActivity;
import com.prasoonsoni.petapp.R;
import com.prasoonsoni.petapp.models.RemainderModel;
import java.util.ArrayList;

public class RemainderAdapter extends RecyclerView.Adapter<RemainderAdapter.MyViewHolder>{
    ArrayList<RemainderModel> list;
    Context context;

    public RemainderAdapter(ArrayList<RemainderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.remainder_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RemainderModel model = list.get(position);
        holder.Animal.setText(model.getAnimal());
        holder.Description.setText(model.getDescription());
        holder.Time.setText(model.getTime());
        holder.Title.setText(model.getTitle());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog  pd = new ProgressDialog(context);
                pd.setIndeterminate(true);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setTitle("Please Wait");
                pd.setMessage("Deleting Reminder...");
                pd.setCancelable(false);
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_info)
                        .setTitle("Are you sure?")
                        .setMessage("Deleting Reminder will cause you data loss. You won't be able to get it again")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance()
                                        .getReference()
                                        .child("reminders")
                                        .child(user)
                                        .child(model.getTitle())
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Animal, Description, Time, Title;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Animal = itemView.findViewById(R.id.rAnimal);
            Description = itemView.findViewById(R.id.rDescription);
            Time = itemView.findViewById(R.id.rTime);
            Title = itemView.findViewById(R.id.rTitle);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
