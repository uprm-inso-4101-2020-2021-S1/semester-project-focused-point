package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.focusedpoint.weighttracker.SQLiteDatabase.SQLite;
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
    SQLite myDataBase;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.addUser();
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
        signIn = findViewById(R.id.button2);

        // Creates new user using all the info submitted by user upon sign up;
        new User(userName.getText().toString(), password.getText().toString(), User.sex.MALE, Integer.parseInt(age.getText().toString()), Integer.parseInt(weight.getText().toString()), Integer.parseInt(heightFT.getText().toString()), Integer.parseInt(heightIN.getText().toString()));
        // redirects user to Main Screen;
        startActivity(new Intent(SignUpActivity.this, AppMainScreen.class));
    }

    //Adds the new user to the Data Base;
    public void addUser(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted =  myDataBase.insertData(userName.getText().toString(),password.getText().toString());
               if(isInserted = true){
                   //If the insertion on database was successful, it will display "User Created!" in the screen;
                   Toast.makeText(SignUpActivity.this,"User Created!", Toast.LENGTH_LONG).show();
               }
               else{
                   //If the insertion on database was successful, it will display "User Not Created!" in the screen;
                   Toast.makeText(SignUpActivity.this,"User Not Created!", Toast.LENGTH_LONG).show();
               }
            }
        });
    }

}