package com.palaksharma.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NotesViewActivity extends AppCompatActivity {

    CRUD backend;
    RecyclerView recyclerView;
    ArrayList<Note> notes;
    NoteViewAdapter noteViewAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);

        findViewById(R.id.left_icon);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        backend = new CRUD();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notes = new ArrayList<Note>();
        noteViewAdapter = new NoteViewAdapter(NotesViewActivity.this,notes);
        recyclerView.setAdapter(noteViewAdapter);
        backend.EventChangeListener(noteViewAdapter,progressDialog,notes);
    }


}