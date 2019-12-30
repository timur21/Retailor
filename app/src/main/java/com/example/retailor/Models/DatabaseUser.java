package com.example.retailor.Models;

public class DatabaseUser {

    private String passcode;
    private boolean isActivated;

    public DatabaseUser(String passcode, boolean isActivated){
        this.passcode = passcode;
        this.isActivated = isActivated;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
