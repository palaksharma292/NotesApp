package com.palaksharma.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AddNewNoteActivity extends AppCompatActivity {
    CRUD backend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        backend = new CRUD();
        //Log.e("NEWACTIVITY",getIntent().getStringExtra("DocumentId")) ;
        String docId = getIntent().getStringExtra("DocumentId");
        TextView t1 = findViewById(R.id.note_add_update_heading);
        TextView t2 = findViewById(R.id.note_add_update_content);

        if(docId!=null)
        {
            Note note = backend.getNoteByID(docId,t1,t2);
        }



    }

    @Override
    protected void onPause() {
        super.onPause();

        TextView t1 = findViewById(R.id.note_add_update_heading);
        TextView t2 = findViewById(R.id.note_add_update_content);
        String docId = getIntent().getStringExtra("DocumentId");
        if(docId==null)
            backend.addNote(t1.getText().toString(),t2.getText().toString());
        else
            backend.updateNote(docId,t1.getText().toString(),t2.getText().toString());
    }
}