package com.palaksharma.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

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

        ArrayList<Note> notes = new ArrayList<Note>();

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
        ArrayList<Note> notes = new ArrayList<Note>();
        //backend.EventChangeListener(noteViewAdapter,progressDialog,notes);

        //notes = new ArrayList<>();
        FirebaseFirestore firestore= FirebaseFirestore.getInstance();
        firestore.collection("Notes").orderBy("Heading", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null)
                        {
                            Log.e("Error","Could not retrieve newly added data");
                            return;
                        }
                        for(DocumentSnapshot documentSnapshot: value)
                        {
                            if(documentSnapshot.exists())
                            {
                                //notes.add(documentSnapshot.toObject(Note.class));
                                Note n = new Note();
                                n.setDocumentId(documentSnapshot.getId()!=null ? documentSnapshot.getId():"");
                                n.setHeading(documentSnapshot.get("Heading").toString());
                                n.setContent(documentSnapshot.get("Content").toString());
                                Timestamp t =(Timestamp) documentSnapshot.get("Date");
                                n.setDate(t);
                                n.setUser(documentSnapshot.get("UserId").toString());
                                notes.add(n);
                                Log.e("QUERYDOC",documentSnapshot.get("Heading").toString());
                            }
                        }

                        noteViewAdapter.SwapList(notes);
                        if(progressDialog.isShowing())  progressDialog.dismiss();
                    }
                });

        //backend.getAllNotes(noteViewAdapter,progressDialog,notes);
    }


}