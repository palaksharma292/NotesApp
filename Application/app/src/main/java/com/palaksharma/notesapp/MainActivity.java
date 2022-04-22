package com.palaksharma.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView leftIcon = findViewById(R.id.left_icon);
        leftIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Toast.makeText(MainActivity.this,"You clicked left icon", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        startActivity(new Intent(MainActivity.this, UserLoginPage.class));
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseAuth.getInstance().signOut();
//
//        //Log.e("EMAIL",user.getEmail());
//        if(user==null)
//        {
//            startActivity(new Intent(MainActivity.this,UserLoginPage.class));
//        }
//        else{
//            Intent intent = new Intent(getApplicationContext(), NotesViewActivity.class);
//            startActivity(intent);
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}