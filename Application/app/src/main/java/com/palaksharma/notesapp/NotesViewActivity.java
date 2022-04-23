package com.palaksharma.notesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesViewActivity extends AppCompatActivity {

    CRUD backend;
    RecyclerView recyclerView;
    NoteViewAdapter noteViewAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_view);

        //Hiding the back button as it is not required at this stage.
        findViewById(R.id.left_icon).setVisibility(View.GONE);
        ImageView btnProf = findViewById(R.id.right_icon);
        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesViewActivity.this,ProfileActivity.class));
            }
        });
        //Showing progress bar while data is being fetched.
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        //Creating firestore object.
        backend = new CRUD();

        //Calling recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Creating notes array list

        ArrayList<Note> notes = new ArrayList<>();

        //creating custom adapter class object
        noteViewAdapter = new NoteViewAdapter(NotesViewActivity.this,notes);
        //Setting the adapter to recyclerView
        recyclerView.setAdapter(noteViewAdapter);

        //Calling backend method and listening for changes in notes array.
        //backend.EventChangeListener(noteViewAdapter,progressDialog,notes);
        backend.getAllNotes(noteViewAdapter,progressDialog,notes);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FLOAT","Button clicked");
                Intent i = new Intent(getApplicationContext(),AddNewNoteActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        ArrayList<Note> notes = new ArrayList<>();
        //Added
        backend.EventChangeListener(noteViewAdapter,progressDialog,notes);
        //Added
    }


}