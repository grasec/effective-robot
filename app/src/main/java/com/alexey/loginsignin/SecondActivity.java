package com.alexey.loginsignin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        String userName = getIntent().getStringExtra(MainActivity.USERNAME);
        if (userName != null){
            TextView lblMessage = findViewById(R.id.lblMessage);
            lblMessage.setText("Welcome " + userName + "!");
        }
    }

    public void btnLogoutClicked(View view) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE);
        prefs.edit().remove(MainActivity.USERNAME).remove(MainActivity.PASSWORD).commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
