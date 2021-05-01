package com.prasoonsoni.petapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.prasoonsoni.petapp.MainActivity;
import com.prasoonsoni.petapp.R;

import java.util.HashMap;

import static com.prasoonsoni.petapp.MainActivity.addButton;

public class QueryFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_query, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Please Wait");
        pd.setMessage("Sending your query...");
        TextView queryTitle = root.findViewById(R.id.queryTitle);
        queryTitle.animate().translationXBy(2000f).setDuration(500);
        TextView queryHead = root.findViewById(R.id.queryHead);
        queryHead.animate().translationXBy(2000f).setDuration(600);
        EditText qName = root.findViewById(R.id.qName);
        EditText qEmail = root.findViewById(R.id.qEmail);
        EditText qPhone = root.findViewById(R.id.qPhone);
        EditText qTopic = root.findViewById(R.id.qQueryTopic);
        EditText qQuery = root.findViewById(R.id.qQuery);
        TextInputLayout yName = root.findViewById(R.id.qNameLayout);
        yName.animate().translationXBy(2000f).setDuration(700);
        TextInputLayout yEmail = root.findViewById(R.id.qEmailLayout);
        yEmail.animate().translationXBy(2000f).setDuration(800);
        TextInputLayout yPhone = root.findViewById(R.id.qPhoneLayout);
        yPhone.animate().translationXBy(2000f).setDuration(900);
        TextInputLayout yTopic = root.findViewById(R.id.qQueryTopicLayout);
        yTopic.animate().translationXBy(2000f).setDuration(1000);
        TextInputLayout yQuery = root.findViewById(R.id.qQueryLayout);
        yQuery.animate().translationXBy(2000f).setDuration(1100);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = qName.getText().toString();
                String email = qEmail.getText().toString();
                String phone = qPhone.getText().toString();
                String topic = qTopic.getText().toString();
                String query = qQuery.getText().toString();
                if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || query.isEmpty() || topic.isEmpty()){
                    Toast.makeText(getContext(), "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                } else {
                    pd.show();
                    DatabaseReference addQuery = FirebaseDatabase.getInstance().getReference().child("query").child(userID).child(topic);
                    HashMap<String, String> userQuery = new HashMap<>();
                    userQuery.put("name", name);
                    userQuery.put("email", email);
                    userQuery.put("phone", phone);
                    userQuery.put("topic", topic);
                    userQuery.put("query", query);
                    addQuery.setValue(userQuery).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            qName.setText("");
                            qEmail.setText("");
                            qPhone.setText("");
                            qTopic.setText("");
                            qQuery.setText("");
                            pd.dismiss();
                            Toast.makeText(getContext(), "Query received, We'll contact you soon.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return root;

    }
}
