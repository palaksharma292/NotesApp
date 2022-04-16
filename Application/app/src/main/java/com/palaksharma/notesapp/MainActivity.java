package com.palaksharma.notesapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CRUD backend=new CRUD();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddNote(View view)
    {
        backend.addNote();
    }
    public void GetList(View view){ List<Map<String, Object>> list= backend.getAllNotes(); }

}