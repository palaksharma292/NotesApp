package com.palaksharma.notesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Note> notes;

    public NoteViewAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public static int GetRandomColors(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        return color;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading;
        String DocumentId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.note_heading);

            itemView.findViewById(R.id.cardView).setBackgroundColor(GetRandomColors());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("DOCUMENTID",DocumentId);
                    Log.e("HERE",heading.getText().toString());
                    Intent i = new Intent(itemView.getContext(), AddNewNoteActivity.class);
                    i.putExtra("DocumentId",DocumentId);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //add this line
                    itemView.getContext().startActivity(i);




                }
            });
        }
    }
    @NonNull
    @Override
    public NoteViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_view_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewAdapter.MyViewHolder holder, int position) {
        Note n = notes.get(position);
        holder.DocumentId = n.getDocumentId();
        holder.heading.setText(n.getHeading());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
