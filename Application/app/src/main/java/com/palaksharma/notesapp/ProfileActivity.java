package com.palaksharma.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String userID = user.getUid();
    TextView UserName;
    EditText Address;
    TextView Email;
    EditText FirstName;
    EditText LastName;
    TextView Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserName = findViewById(R.id.username);
        Address = findViewById(R.id.Address);
        Email = findViewById(R.id.Email);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Password = findViewById(R.id.Password);

        ImageView btnProf = findViewById(R.id.right_icon);
        //btnProf.setVisibility(View.GONE);

        btnProf.setImageResource(R.drawable.ic_logout);

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

        ImageView btnBack = findViewById(R.id.left_icon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        GetUserProfile();
    }

    public boolean CheckConnection()
    {
        try
        {
            firestore= FirebaseFirestore.getInstance();
            Log.i("Connection success", "Connected successfully to the firebase");
            return true;
        }
        catch(Exception e)
        {
            Log.i("Connection Error", e.getMessage());
            return false;
        }
    }

    public void GetUserProfile()
    {
        try
        {
            if(CheckConnection())
            {
                firestore.collection("Users")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot document: queryDocumentSnapshots)
                                {
                                    Map<String, Object> user= new HashMap<>();
                                    user.putAll(document.getData());
                                    user.put("DocumentName", document.getId());
                                    //NotesList.add(note);


                                    if(user.get("userName").equals(userID)) {
                                        UserName.setText(user.get("userName").toString());
                                        Address.setText(user.get("address").toString());
                                        Email.setText(user.get("email").toString());
                                        FirstName.setText(user.get("firstName").toString());
                                        LastName.setText(user.get("lastName").toString());
                                        Password.setText(user.get("password").toString());
                                    }
                                    Log.e("PROFILE",document.get("userName").toString());
                                    Log.e("PROFILEUSER",userID);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Coult not get user", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Log.i("Error getting list", e.getMessage());
        }

    }
}