package com.example.xmum_app;

import java.util.Date;

public class User {
    String id;
    String fullName;
    Date sessionExpiryDate;

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}


