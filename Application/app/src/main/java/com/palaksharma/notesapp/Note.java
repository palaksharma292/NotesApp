package com.palaksharma.notesapp;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Note {
    String Content;
    String Heading;
    Timestamp Date;
    Note(){}

    public String getContent() {
        return Content;
    }

    public String getHeading() {
        return Heading;
    }

    public Timestamp getDate() {
        return Date;
    }

    public Note(String content, String heading, com.google.firebase.Timestamp date) {
        this.Content = content;
        this.Heading = heading;
        this.Date = date;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public void setDate(Timestamp date) {
        Date = date;
    }
}
