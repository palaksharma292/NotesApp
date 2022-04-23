package com.palaksharma.notesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmailOrPasswordActivity extends AppCompatActivity {

    EditText txtPassword;
    Boolean PasswordChanged = false;
    private FirebaseFirestore firestore;
    User userObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email_or_password);

        ImageView btnProf = findViewById(R.id.right_icon);
        btnProf.setVisibility(View.GONE);

        txtPassword = findViewById(R.id.txtUpdatePassword);
        userObj = (User) getIntent().getSerializableExtra("USER_OBJ");

        Button btnUpdateEmailPassword = findViewById(R.id.btnUpdateEmailPassord);
        btnUpdateEmailPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateEmailPassword();
            }
        });

        ImageView btnBack = findViewById(R.id.left_icon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void UpdateEmailPassword() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(PasswordChanged== true)
        {
            Toast.makeText(UpdateEmailOrPasswordActivity.this, "Password already updated please come back again.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(txtPassword.length()>0)
        {
            PasswordChanged = true;
            user.updatePassword(txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        if(CheckConnection())
                        {
                            userObj.setPassword(txtPassword.getText().toString());
                            Map<String,Object> mapUser = new HashMap<>();
                            mapUser.put("Email", userObj.getEmail());
                            mapUser.put("Address", userObj.getAddress());
                            mapUser.put("Firstname", userObj.getFirstName());
                            mapUser.put("LastName", userObj.getLastName());
                            mapUser.put("Password", userObj.getPassword());
                            mapUser.put("UserName", userObj.getUserName());
                            firestore.collection("Users").document(userObj.DocumentId)
                                    .update(mapUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("Success", "user updated Successfully ");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("Failure", "user update failed ");
                                        }
                                    });

                        }

                        Toast.makeText(UpdateEmailOrPasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(UpdateEmailOrPasswordActivity.this, "Password update failed. Please try later", Toast.LENGTH_SHORT).show();
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
}