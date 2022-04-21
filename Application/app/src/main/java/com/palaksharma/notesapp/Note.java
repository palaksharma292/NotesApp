package com.palaksharma.notesapp;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

public class Note {
    String DocumentId;
    String Content;
    String Heading;
    Timestamp Date;
    String UserId;



    Note(){}
    public String getUser() {
        return UserId;
    }
    public String getContent() {
        return Content;
    }

    public String getHeading() {
        return Heading;
    }

    public Timestamp getDate() {
        return Date;
    }

    public String getDocumentId() {
        return DocumentId;
    }


    public Note(String content, String heading, com.google.firebase.Timestamp date, String documentId,String userId) {
        this.Content = content;
        this.Heading = heading;
        this.Date = date;
        this.DocumentId = documentId;
        this.UserId = userId;
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

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }

    public void setUser(String userId) {
        UserId = userId;
    }
}
