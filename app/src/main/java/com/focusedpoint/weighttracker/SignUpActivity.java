package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    // Defines what happens when back button is clicked;
    public void goback(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

    }

    // Defines what happens when user clicks on "Sign Up Button"
    public void createUser(View view) {
        startActivity(new Intent(SignUpActivity.this, AppMainScreen.class));
    }

}