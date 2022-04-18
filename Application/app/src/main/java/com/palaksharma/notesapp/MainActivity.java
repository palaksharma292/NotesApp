package com.palaksharma.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CRUD backend=new CRUD();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), NotesViewActivity.class);
        startActivity(intent);
        ImageView leftIcon = findViewById(R.id.left_icon);
        leftIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                Toast.makeText(MainActivity.this,"You clicked left icon", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void AddNote(View view)
    {
        backend.addNote();
    }
    //public void GetList(View view){ List<Map<String, Object>> list= backend.getAllNotes(); }

}