package com.focusedpoint.weighttracker.SQLiteDatabase;

import java.io.File;

//Stores all the information of a specific user.
public class DatabaseEntry {
    String username;
    String password;
    File UserFile;
    File VisitorFile;
    public DatabaseEntry(String username,String password, File UserFile, File VisitorFile){
        this.username=username;
        this.password=password;
        this.VisitorFile=VisitorFile;
        this.UserFile = UserFile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getUserFile() {
        return UserFile;
    }

    public void setUserFile(File userFile) {
        UserFile = userFile;
    }

    public File getVisitorFile() {
        return VisitorFile;
    }

    public void setVisitorFile(File visitorFile) {
        VisitorFile = visitorFile;
    }
}
