package com.example.xmum_app;

import java.util.Date;

public class User {
    String id;
    String fullName;
    String role;
    Date sessionExpiryDate;

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}


