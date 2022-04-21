package com.palaksharma.notesapp;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CRUD
{
    FirebaseFirestore firestore;

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String userID;

    {
        assert user != null;
        userID = user.getUid();
    }

    Note addUpdate;
    public  void CRUD()
    {

    }
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

    public void addNote(String Heading, String Content)
    {
        try
        {
            if(CheckConnection()) {
                Map<String, Object> note = new HashMap<>();
                note.put("Heading", Heading);
                note.put("Date", Calendar.getInstance().getTime());
                note.put("Content", Content);
                note.put("UserId",userID);
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

    public List<Note> getAllNotes(NoteViewAdapter noteViewAdapter, ProgressDialog p,ArrayList<Note> notes)
    {
        //List<Map<String, Object>> NotesList= new ArrayList<>();

        //List<String>
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
                                    note.putAll(Objects.requireNonNull(document.getData()));
                                    note.put("DocumentName", document.getId());
                                    //NotesList.add(note);

                                    Note n = new Note();
                                    n =Helper(note);
                                    notes.add(n);
                                    //UserId
                                    Log.e("LISTITEM", document.getData().toString());
                                }
                                noteViewAdapter.notifyDataSetChanged();
                                if(p.isShowing())  p.dismiss();
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
        return notes;
    }


    public void deleteNoteByID(String documentID)
    {
        try
        {
            if(CheckConnection())
            {
                firestore.collection("Notes").document(documentID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i("Success", "Note Deleted");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Failure", "Note deletion failed");
                            }
                        });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public Note getNoteByID(String documentID, TextView t1,TextView t2)
    {
        try
        {
            if(CheckConnection())
            {

                Map<String, Object> finalNote= new HashMap<>();

                firestore.collection("Notes").document(documentID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                finalNote.put("Heading", documentSnapshot.get("Heading"));
                                finalNote.put("Content", documentSnapshot.get("Content"));
                                finalNote.put("Date", documentSnapshot.get("Date"));
                                finalNote.put("DocumentName", documentID);
                                finalNote.put("UserId", documentSnapshot.get("UserId"));

                                if(finalNote!=null)
                                {

                                    addUpdate = new Note();
                                    addUpdate = Helper(finalNote);

                                    t1.setText(addUpdate.getHeading());
                                    t2.setText(addUpdate.getContent());
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Failure", "Note deletion failed");
                            }
                        });
                return addUpdate;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void updateNote(String DocumentID, String Heading, String Content) {
        try
        {
            Map<String, Object> note= new HashMap<>();
            note.put("Heading", Heading);
            note.put("Content", Content);
            note.put("Date", Calendar.getInstance().getTime());
            note.put("UserId",userID);
            if(CheckConnection())
            {
                firestore.collection("Notes").document(DocumentID)
                        .update(note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i("Success", "Note updated Successfully ");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("Failure", "Note update failed ");
                            }
                        });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void EventChangeListener1(NoteViewAdapter noteViewAdapter, ProgressDialog p,ArrayList<Note> notes)
    {

        if(CheckConnection())
        {
            firestore.collection("Notes").orderBy("Heading", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error!=null)
                            {
                                if(p.isShowing())
                                    p.dismiss();
                                Log.e("Error", error.getMessage());
                                return;
                            }
                            assert value != null;
                            for(DocumentChange dc:value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED||dc.getType()== DocumentChange.Type.MODIFIED)
                                {
                                    //String docId = dc.getDocument().get("DocumentName").toString();
                                    notes.add(dc.getDocument().toObject(Note.class));

                                   // Log.e("DOCUMMENT",docId);
                                }
                                noteViewAdapter.notifyDataSetChanged();
                                if(p.isShowing())  p.dismiss();
                            }

                        }
                    });
        }
    }

    public void EventChangeListener(NoteViewAdapter noteViewAdapter, ProgressDialog p,ArrayList<Note> notes)
    {

        if(CheckConnection())
        {
            firestore.collection("Notes").orderBy("Heading", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            assert value != null;
                            for(DocumentSnapshot documentSnapshot: value)
                            {
                                if(documentSnapshot.exists())
                                {
                                    //notes.add(documentSnapshot.toObject(Note.class));
                                    Note n = Helper(Objects.requireNonNull(documentSnapshot.getData()));
                                    notes.add(n);
                                    Log.e("QUERYDOC", Objects.requireNonNull(documentSnapshot.get("Heading")).toString());
                                }
                            }
                            noteViewAdapter.notifyDataSetChanged();
                            if(p.isShowing())  p.dismiss();
                        }
                    });
        }
    }
    public Note Helper(Map<String,Object> note){
        Note n = new Note();

        n.setDocumentId(note.get("DocumentName")!=null ? Objects.requireNonNull(note.get("DocumentName")).toString():"");
        n.setHeading(Objects.requireNonNull(note.get("Heading")).toString());
        n.setContent(Objects.requireNonNull(note.get("Content")).toString());
        Timestamp t =(Timestamp) note.get("Date");
        n.setDate(t);
        n.setUser(Objects.requireNonNull(note.get("UserId")).toString());

        return n;
    }

}
