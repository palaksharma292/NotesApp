package com.palaksharma.notesapp;

import java.io.Serializable;

public class User implements Serializable {
    String UserName;
    String Email;
    String Password;
    String FirstName;
    String LastName;
    String Address;
    String DocumentId;

    public User(){}
    public User(String userName, String email, String password, String firstName, String lastName, String address,String documentId) {
        UserName = userName;
        Email = email;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        DocumentId = documentId;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }
}
