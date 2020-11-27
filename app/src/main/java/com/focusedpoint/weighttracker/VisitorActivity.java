package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VisitorActivity extends AppCompatActivity {

    static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        Button ButtonDone = findViewById(R.id.Done);
        MainActivity.user=null;
        ButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreen();
            }
        });
    }

    private void changeScreen() {
        String Code = ((EditText)findViewById(R.id.VisitorField)).getText().toString().trim();
        if(Code.length() != 0){
            //if the code is in one of the entries of the database
            //        startActivity(new Intent(VisitorActivity.this, Stats_Activity.class));
            //        finish();
            //else Toast.makeText(getApplicationContext(), "Please enter a valid code.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Please enter a code in the field.", Toast.LENGTH_LONG).show();
        }
    }


}