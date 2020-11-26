package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.focusedpoint.weighttracker.User;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    String userName;
    String password;
    String gender;
    String age;
    String weight;
    String heightFT;
    String heightIN;
    static User MainUser;

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
        userName = ((EditText)findViewById(R.id.username)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        gender = ((EditText)findViewById(R.id.gender)).getText().toString().trim();
        age = ((EditText)findViewById(R.id.age)).getText().toString().trim();
        weight = ((EditText)findViewById(R.id.weight)).getText().toString().trim();
        heightFT = ((EditText)findViewById(R.id.Feet)).getText().toString().trim();
        heightIN = ((EditText)findViewById(R.id.inches)).getText().toString().trim();

        // Creates new user using all the info submitted by user upon sign up;
        try {
            if(userName.length()==0||password.length()==0||gender.length()==0||age.length()==0||weight.length()==0||heightFT.length()==0||heightIN.length()==0){
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            MainUser = new User(userName, password, User.sex.MALE, Integer.parseInt(age), Integer.parseInt(weight), Integer.parseInt(heightFT), Integer.parseInt(heightIN));
            userName = null;
            password = null;
            gender = null;
            age = null;
            weight = null;
            heightFT = null;
            heightIN =null;
            System.gc();
            // redirects user to Main Screen;
            startActivity(new Intent(SignUpActivity.this, AppMainScreen.class));
            finish();
        }catch (NullPointerException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG);
            toast.show();
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill the fields (Age,Height,Weight) with numbers not characters", Toast.LENGTH_LONG);
            toast.show();
        }

    }

}