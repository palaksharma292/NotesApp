package com.palaksharma.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    EditText UserName;
    EditText Address;
    EditText Email;
    EditText FirstName;
    EditText LastName;
    EditText Password;
    User u;

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
        disableEditText(UserName);
        disableEditText(Email);
        disableEditText(Password);
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

        findViewById(R.id.btnOpenUpdatePassowrd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, UpdateEmailOrPasswordActivity.class);

                i.putExtra("USER_OBJ",u);
                startActivity(i);
            }
        });

        findViewById(R.id.btnSaveProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfile();
            }
        });
}

    private void UpdateProfile() {

        if(CheckConnection())
        {
            Map<String,Object> mapUser = new HashMap<>();
            String DocumentID = u.getDocumentId();
            Log.i("DOCUMENTID", DocumentID);
            u = new User(UserName.getText().toString(),Email.getText().toString(),Password.getText().toString(),FirstName.getText().toString(),LastName.getText().toString(),Address.getText().toString(),userID,DocumentID);
            mapUser.put("DocumentId",DocumentID);
            mapUser.put("UserId", u.getUserID());
            mapUser.put("Email", u.getEmail());
            mapUser.put("Address", u.getAddress());
            mapUser.put("FirstName", u.getFirstName());
            mapUser.put("LastName", u.getLastName());
            mapUser.put("Password", u.getPassword());
            mapUser.put("UserName", u.getUserName());
            firestore.collection("Users").document(DocumentID)
                    .update(mapUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProfileActivity.this,"Profile updated!",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this,"Could not update profile",Toast.LENGTH_LONG).show();
                        }
                    });

        }
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
                                    user.put("DocumentId", document.getId());
                                    //NotesList.add(note);


                                    if(user.get("UserId").equals(userID)) {
                                        UserName.setText(user.get("UserName").toString());
                                        Address.setText(user.get("Address").toString());
                                        Email.setText(user.get("Email").toString());
                                        FirstName.setText(user.get("FirstName").toString());
                                        LastName.setText(user.get("LastName").toString());
                                        Password.setText(user.get("Password").toString());


                                        u = new User(UserName.getText().toString(),Email.getText().toString(),Password.getText().toString(),FirstName.getText().toString(),LastName.getText().toString(),Address.getText().toString(),user.get("UserId").toString(),user.get("DocumentId").toString());
                                    }
                                    Log.e("PROFILE",document.get("UserName").toString());
                                    Log.e("PROFILEUSER",userID);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Could not get user", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Log.i("Error getting list", e.getMessage());
        }

    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

}