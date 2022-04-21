package com.palaksharma.notesapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserLoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);

        TextView register = findViewById(R.id.openRegister);
        register.setOnClickListener(this);

        Button loginbtn = findViewById(R.id.loginBtn);
        loginbtn.setOnClickListener(this);
        email= findViewById(R.id.loginEmail);
        password= findViewById(R.id.loginPassword);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.openRegister:
                startActivity(new Intent(this, RegisterPage.class));
                break;
            case R.id.loginBtn:
                String emailText= email.getText().toString();
                String passwordText= password.getText().toString();
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i("Success", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    startActivity(new Intent(UserLoginPage.this,MainActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(UserLoginPage.this, "Invalid username or password..",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
        }
    }
}