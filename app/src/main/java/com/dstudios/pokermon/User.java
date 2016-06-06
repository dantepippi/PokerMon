package com.dstudios.pokermon;

/**
 * Created by dante on 29/05/2016.
 */
class User {
    private String displayName;
    private String email;
    private String uid;

    Boolean isAnonymous() {
        return anonymous;
    }

    void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    private Boolean anonymous;

    User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    User(String displayName, String email, String uid) {
        this.displayName= displayName;
        this.setEmail(email);
        this.setUid(uid);
    }

    String getDisplayName() {
        return displayName;
    }

    void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getUid() {
        return uid;
    }

    void setUid(String uid) {
        this.uid = uid;
    }
}
