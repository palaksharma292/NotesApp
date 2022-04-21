package com.palaksharma.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private Button registerUser;
    private TextView openLogin;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

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
                String email= ((EditText)findViewById(R.id.RegisterEmail)).getText().toString();
                String password= ((EditText)findViewById(R.id.registerPassword)).getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i("Success", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if(CheckConnection())
                                    {
                                        User u = new User(user.getUid(),email,password,"","","","");

                                        Map<String, Object> mapUser= new HashMap<>();
                                        mapUser.put("Email", u.Email);
                                        mapUser.put("Address", u.Address);
                                        mapUser.put("FirstName", u.FirstName);
                                        mapUser.put("LastName", u.LastName);
                                        mapUser.put("Password", u.Password);
                                        mapUser.put("UserName", u.UserName);
                                        firestore.collection("Users")
                                                .add(u)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(RegisterPage.this, "User Added",
                                                                Toast.LENGTH_SHORT).show();

                                                        startActivity(new Intent(RegisterPage.this,MainActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(RegisterPage.this, "Could not add user",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                       });
                                    }

                                } else {
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