package com.focusedpoint.weighttracker;
import com.focusedpoint.weighttracker.User;
public class LogInScreen {
    String username;
    String password;

    public LogInScreen(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean incorrectUsername(String username) {
        if(!this.username.equals(username))
            return false;
        return true;
    }

    public boolean incorrectPassword(String password) {
        if(!this.password.equals(password))
            return false;
        return true;
    }

    // analizes user entered data to see if username and/or password are entered correctly
    public void login(String username, String password) {
        if(incorrectUsername(username) || incorrectPassword(password));
    }

}
