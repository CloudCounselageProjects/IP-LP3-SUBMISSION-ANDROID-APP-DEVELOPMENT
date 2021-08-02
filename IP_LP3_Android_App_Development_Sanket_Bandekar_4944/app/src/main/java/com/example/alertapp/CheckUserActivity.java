package com.example.alertapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class CheckUserActivity extends AppCompatActivity {

    public static final String MYPREFERENCES = "MyPref";
    public static final String isUser = "isActive";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        String isCurrent = sharedPreferences.getString(isUser, "false");

        Intent intent;
        if(isCurrent.equals("false")){
            intent = new Intent(CheckUserActivity.this, DetailsActivity.class);

        }else{
            intent = new Intent(CheckUserActivity.this, MainActivity.class);
        }
        startActivity(intent);
    }
}