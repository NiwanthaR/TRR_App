package com.example.trr_app.model;

public class User {
    public String userName,userCategory,userContact,userEmail;

    public User(String userName, String userCategory, String userContact, String userEmail) {
        this.userName = userName;
        this.userCategory = userCategory;
        this.userContact = userContact;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
