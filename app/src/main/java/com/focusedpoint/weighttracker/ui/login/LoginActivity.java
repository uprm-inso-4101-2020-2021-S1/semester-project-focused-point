package com.focusedpoint.weighttracker.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.focusedpoint.weighttracker.AppMainScreen;
import com.focusedpoint.weighttracker.R;
import com.focusedpoint.weighttracker.SQLiteDatabase.SQLite;
import com.focusedpoint.weighttracker.SignUpActivity;
import com.focusedpoint.weighttracker.Stats_Activity;
import com.focusedpoint.weighttracker.User;
import com.focusedpoint.weighttracker.VisitorActivity;
import com.focusedpoint.weighttracker.ui.login.LoginViewModel;
import com.focusedpoint.weighttracker.ui.login.LoginViewModelFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;
    SQLite myDB;
    public static User user;

    public static Context context;
    //File that contains the information regarding the user of the application
    static File UserFile;
    //File that contains the information regarding the user of the application

    static File VisitorFile;
    public static String UserFileName = "UserData.txt";
    public static String VisitorFileName = "VisitorData.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=getApplicationContext();
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        myDB = new SQLite(this);
        try{
            user =  new User();
            if(AppMainScreen.signout==false&&LoadDataHelper(UserFileName)!=""){
            startActivity(new Intent(LoginActivity.this, AppMainScreen.class));
            finish();}
            else{
                user = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button VisitorButton = findViewById(R.id.Visit);

        VisitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreen();
                finish();
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this,"Please fill all fields.", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean checkUserPass = myDB.checkUsernamePassword(username,password);
                    if(checkUserPass == true){
                        Toast.makeText(LoginActivity.this,"Log In Successful!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, AppMainScreen.class));
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void changeScreen() {
        System.gc();
        startActivity(new Intent(this, VisitorActivity.class));
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void OpenSignupPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

    public void signIn(View view) {
        startActivity(new Intent(LoginActivity.this, AppMainScreen.class));
        finish();

    }

    //Method responsible for loading the data present in a file of the specified name.
    // It currently supports (UserFileName,VisitorFileName).
    //To add support for another file
    public String LoadDataHelper(String FileName) throws IOException {
        String result = "";
        try{
            user.clear();
        }catch (NullPointerException e){
            throw new IOException();
        }

        boolean foodsfound=false;
        try {
            InputStream inputStream = openFileInput(FileName);
            Scanner scan = new Scanner(inputStream);
            //reads the contents of a file line by line
            while(scan.hasNextLine()) {
                String TempLine = scan.nextLine();
                Log.println(Log.INFO,"debug","\n"+TempLine);
                //if the string TempLine contains "Visitor Code: "
                if(TempLine.contains("Visitor Code: ")){
                    user.setVisitorCode(Integer.parseInt(TempLine.substring(14,TempLine.length())));
                }
                else if(TempLine.contains("Name: ")){
                    user.setName(TempLine.substring(6,TempLine.length()));
                }
                //if the string TempLine contains "Username:
                else if(TempLine.contains("Username: ")){
                    user.setUsername(TempLine.substring(10,TempLine.length()));
                }
                //if the string TempLine contains "Password: "
                else if(TempLine.contains("Password: ")){
                    user.setPassword(TempLine.substring(10,TempLine.length()));
                }
                //if the string TempLine contains "Age: "
                else if(TempLine.contains("Age: ")){
                    user.setAge(Integer.valueOf(TempLine.substring(5,TempLine.length())));
                }
                //if the string TempLine contains "Sex: "
                else if(TempLine.contains("Sex: ")){
                    String sex = TempLine.substring(5,TempLine.length());
                    if(sex == "MALE")
                        user.setSex(User.sex.MALE);
                    if(sex == "FEMALE")
                        user.setSex(User.sex.FEMALE);
                }
                //if the string TempLine contains "Height Feet: "
                else if(TempLine.contains("Height Feet: "))
                    user.setHeightFT(Integer.parseInt(TempLine.substring(13,TempLine.length())));
                    //if the string TempLine contains "Height inches: "
                else if(TempLine.contains("Height inches: "))
                    user.setHeightIN(Integer.parseInt(TempLine.substring(15,TempLine.length())));
                    //if the string TempLine contains "Weights with Dates:"
                else if(TempLine.contains("Weights with Dates:")){
                    while(scan.hasNextLine()){
                        TempLine = scan.nextLine();
                        //if the string TempLine contains "Foods with Calories:"
                        if(TempLine.contains("Foods with Calories:")|(foodsfound == true)){
                            foodsfound = true;
                            if(TempLine.contains("Foods with Calories:")){
                                if(scan.hasNextLine())
                                    TempLine = scan.nextLine();
                                else break;
                            }
                            int location= TempLine.indexOf(":");
                            if(location==-1)
                                break;
                            String NameOfFood = TempLine.substring(0,location);
                            String Calories = "";
                            Boolean foundCal= false;
                            Boolean foundEaten = false;
                            String TimeEaten ="";
                            com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String> TimesEaten = new com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String>(30);
                            for(int i = TempLine.indexOf("Calories");i<TempLine.length();i++){
                                if(TempLine.charAt(i)=='('){
                                    foundCal = true;
                                    i++;
                                }
                                if(foundCal==true){
                                    if(TempLine.charAt(i)==')'){
                                        foundCal=false;
                                    }else{
                                        Calories=Calories+TempLine.charAt(i);
                                    }

                                }
                                if(TempLine.charAt(i)=='['){
                                    foundEaten=true;
                                    i++;
                                }
                                if(foundEaten==true){
                                    if(TempLine.charAt(i)==']'){
                                        foundCal=false;
                                    }else if(!(TempLine.charAt(i)==',')){
                                        TimeEaten=TimeEaten+TempLine.charAt(i);
                                    }else{
                                        TimesEaten.add(TimeEaten);
                                        TimeEaten="";
                                    }

                                }
                            }
                            if(!(Calories==""))
                                user.addMeal(NameOfFood,Float.valueOf(Calories),TimesEaten);
                        }
                        else{
                            int location= TempLine.indexOf(":");
                            if(location == -1)
                                break;
                            user.setWeight(Integer.parseInt(TempLine.substring(0,location)),TempLine.substring(location+1,TempLine.length()));
                        }}

                }

                result+=TempLine+"\n";

            }
        }


        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: "+FileName+" " + e.toString());
            return "";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ FileName+" "+ e.toString());
            return "";
        }
        if(FileName==UserFileName)
            UserFile = new File(getFilesDir(),FileName);
        else if(FileName==VisitorFileName)
            VisitorFile = new File(getFilesDir(),FileName);
        return result;
    }
}