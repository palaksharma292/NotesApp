package com.palaksharma.notesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNewNoteActivity extends AppCompatActivity {
    CRUD backend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        ImageView btnBack = findViewById(R.id.left_icon);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView btnProf = findViewById(R.id.right_icon);
        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewNoteActivity.this,ProfileActivity.class));
            }
        });


        backend = new CRUD();
        //Log.e("NEWACTIVITY",getIntent().getStringExtra("DocumentId")) ;
        String docId = getIntent().getStringExtra("DocumentId");
        TextView t1 = findViewById(R.id.note_add_update_heading);
        TextView t2 = findViewById(R.id.note_add_update_content);

        if(docId!=null)
        {

            FloatingActionButton flb = findViewById(R.id.floatingActionButtonDelete);
            flb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    Log.e("DeleteYesButton","Delete yes btn called");
                                    backend.deleteNoteByID(docId);
                                    Intent i = new Intent(getApplicationContext(),NotesViewActivity.class);
                                    startActivity(i);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });


            Note note = backend.getNoteByID(docId,t1,t2);
        }
        else
        {

            t1.setText("");
            t2.setText("");
            findViewById(R.id.floatingActionButtonDelete).setVisibility(View.GONE);
        }





    }


    //Before destryong the activity we want to save/update data to firebase.
    @Override
    protected void onPause() {
        super.onPause();

        TextView t1 = findViewById(R.id.note_add_update_heading);
        TextView t2 = findViewById(R.id.note_add_update_content);
        String docId = getIntent().getStringExtra("DocumentId");

        if(docId==null&!t1.getText().toString().isEmpty())
            backend.addNote(t1.getText().toString(),t2.getText().toString());
        else if(docId==null&t1.getText().toString().isEmpty())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cannot add an empty title note").show();
        }
        if(docId!=null)
            backend.updateNote(docId,t1.getText().toString(),t2.getText().toString());
    }
}