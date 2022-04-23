package com.palaksharma.notesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Note> notes;

    public NoteViewAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public static int GetColor(){
        return Color.parseColor("#FAD775");
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading;
        String DocumentId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.note_heading);

            itemView.findViewById(R.id.cardView).setBackgroundColor(GetColor());
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

    public void SwapList(ArrayList<Note> notes)
    {
        this.notes = notes;
        notifyDataSetChanged();
    }
}
