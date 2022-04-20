package com.palaksharma.notesapp;

//TODO Make this the opening activity

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginPage extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button loginbtn;
    private EditText email;
    private EditText password;

    FirebaseAuth mAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);

        register= (TextView) findViewById(R.id.openRegister);
        register.setOnClickListener(this);

        loginbtn= (Button) findViewById(R.id.loginBtn);
        loginbtn.setOnClickListener(this);
        email=(EditText) findViewById(R.id.loginEmail);
        password=(EditText) findViewById(R.id.loginPassword);

    }

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
                                    //TODO updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("Failure", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(UserLoginPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //TODO updateUI(null);
                                }
                            }
                        });
                break;
        }
    }
}