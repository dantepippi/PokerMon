package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */
public class User {
    private String displayName;
    private String email;
    private String uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String displayName, String email, String uid) {
        this.displayName= displayName;
        this.setEmail(email);
        this.setUid(uid);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
