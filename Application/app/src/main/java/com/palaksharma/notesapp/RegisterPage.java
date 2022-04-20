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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class RegisterPage extends AppCompatActivity {
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private Button registerUser;
    private TextView openLogin;
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
                                    //TODO updateUI(user);
                                    //Open login page again
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("Failure", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                     //TODO updateUI(null);
                                    //Close UI or ???
                                }
                            }
                        });
            }
        });


    }
}