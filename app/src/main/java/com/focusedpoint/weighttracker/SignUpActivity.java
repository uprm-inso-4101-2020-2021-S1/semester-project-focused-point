package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.focusedpoint.weighttracker.User;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText userName;
    EditText password;
    EditText gender;
    EditText age;
    EditText weight;
    EditText heightFT;
    EditText heightIN;

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
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        weight = findViewById(R.id.weight);
        heightFT = findViewById(R.id.Feet);
        heightIN = findViewById(R.id.inches);

        // Creates new user using all the info submitted by user upon sign up;
        new User(userName.getText().toString(), password.getText().toString(), User.sex.MALE, Integer.parseInt(age.getText().toString()), Integer.parseInt(weight.getText().toString()), Integer.parseInt(heightFT.getText().toString()), Integer.parseInt(heightIN.getText().toString()));
        // redirects user to Main Screen;
        startActivity(new Intent(SignUpActivity.this, AppMainScreen.class));
    }

}