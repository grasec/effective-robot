package com.alexey.loginsignin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends Activity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PREFS = "prefs";
    public static final String USERSASSTRING = "users";
    private EditText txtUser, txtPassword;
    private Users users;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        users = Users.getUsers();

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        if (prefs.contains(USERSASSTRING)) {
            Set<String> usersAsString = prefs.getStringSet(USERSASSTRING, null);
            users.loadUsers(usersAsString);
        }
        if (prefs.contains(USERNAME)) {
            String userName = prefs.getString(USERNAME, null);
            String password = prefs.getString(PASSWORD, null);
            user = new User(userName, password);
            txtUser.setText(user.getUserName());
            txtPassword.setText(user.getPassword());
            //goToSecondActivity();
            loginOrSignup(true);
        }

    }

    private void goToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(USERNAME, user.getUserName());
        startActivity(intent);
        finish();
    }

    public void btnSignupClicked(View view) {
        loginOrSignup(false);
    }

    public void btnLoginClicked(View view) {
        loginOrSignup(true);
    }

    private void loginOrSignup(boolean isLogin) {
        user = getUserFromUI();
        if (user == null)
            return;
        String message = null;
        if (isLogin) {
            if (!users.login(user)) {
                message = "username or password incorrect";
            }
        } else {// signup
            if (!users.singup(user)) {
                message = "username taken";
            }
        }
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(USERNAME, user.getUserName())
                    .putString(PASSWORD, user.getPassword());
            if (!isLogin) {
                Set<String> usersAsString = users.getUsersAsString();
                editor.putStringSet(USERSASSTRING, usersAsString);
            }

            editor.commit();
            goToSecondActivity();
        }
    }
    

    private User getUserFromUI() {
        String userName = txtUser.getText().toString();
        String password = txtPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "both username and password are mandatory!",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        return new User(userName, password);
    }
}
