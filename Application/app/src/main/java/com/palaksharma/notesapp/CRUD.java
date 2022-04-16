package com.palaksharma.notesapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUD
{
    FirebaseFirestore firestore;

    public boolean CheckConnection()
    {
        try
        {
            firestore= FirebaseFirestore.getInstance();
            Log.i("Connection success", "Connected successfully to the firebase");
            return true;
        }
        catch(Exception e)
        {
            Log.i("Connection Error", e.getMessage());
            return false;
        }
    }

    public void addNote()
    {
        try
        {
            if(CheckConnection()) {
                Map<String, Object> note = new HashMap<>();
                note.put("Heading", "Note 2");
                note.put("Date", Calendar.getInstance().getTime());
                note.put("Content", "Testing addition of notes");
                firestore.collection("Notes")
                        .add(note)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.i("Success", "note added successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Failure", "note could not be added");
                            }
                        });
                }
            }
        catch(Exception e)
        {
            Log.i("Error Adding Note", e.getMessage());
        }
    }

    public List<Map<String,Object>> getAllNotes()
    {
        List<Map<String, Object>> NotesList= new ArrayList<>();
        try
        {
            if(CheckConnection())
            {
                firestore.collection("Notes")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot document: queryDocumentSnapshots)
                                {
                                    Map<String, Object> note= new HashMap<>();
                                    note.putAll(document.getData());
                                    note.put("DocumentName", document.getId());
                                    NotesList.add(note);
                                    Log.i("List item", note.toString());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Failure List item", "Could not get the list");
                            }
                        });
            }
        }
        catch (Exception e)
        {
            Log.i("Error getting list", e.getMessage());
        }
        return NotesList;
    }
}
