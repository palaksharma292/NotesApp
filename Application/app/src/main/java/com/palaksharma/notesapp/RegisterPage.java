package com.palaksharma.notesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private Button registerUser;
    private TextView openLogin;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        openLogin=(TextView) findViewById(R.id.openLogin);
        openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage.this,UserLoginPage.class));
            }
        });

        registerUser=(Button) findViewById(R.id.RegisterBtn);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Fetching Data...");
                progressDialog.show();

                String email= ((EditText)findViewById(R.id.RegisterEmail)).getText().toString();
                String password= ((EditText)findViewById(R.id.registerPassword)).getText().toString();
                String username= ((EditText)findViewById(R.id.RegisterUsername)).getText().toString();
                String firstname= ((EditText)findViewById(R.id.RegisterFirstName)).getText().toString();
                String lastName= ((EditText)findViewById(R.id.RegisterLastName)).getText().toString();
                String address= ((EditText)findViewById(R.id.RegisterAddress)).getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("Success", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if(CheckConnection())
                                    {
                                        User u = new User(username,email,password,firstname,lastName,address,user.getUid(),"");

                                        Map<String, Object> mapUser= new HashMap<>();
                                        mapUser.put("Email", u.Email);
                                        mapUser.put("Address", u.Address);
                                        mapUser.put("FirstName", u.FirstName);
                                        mapUser.put("LastName", u.LastName);
                                        mapUser.put("Password", u.Password);
                                        mapUser.put("UserName", u.UserName);
                                        mapUser.put("UserId", u.UserID);
                                        firestore.collection("Users")
                                                .add(mapUser)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        if(progressDialog.isShowing())
                                                            progressDialog.dismiss();
                                                        Toast.makeText(RegisterPage.this, "User Added",
                                                                Toast.LENGTH_SHORT).show();

                                                        startActivity(new Intent(RegisterPage.this,MainActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        if(progressDialog.isShowing())
                                                            progressDialog.dismiss();
                                                        Toast.makeText(RegisterPage.this, "Could not add user",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                       });
                                    }

                                } else {

                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterPage.this, "Authentication failed. Try logging in if already registered.",
                                            Toast.LENGTH_LONG).show();


                                }
                            }
                        });
            }
        });


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