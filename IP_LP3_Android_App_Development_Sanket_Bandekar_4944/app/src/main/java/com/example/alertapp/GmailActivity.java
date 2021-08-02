package com.example.alertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GmailActivity extends AppCompatActivity {

    Button btnGmail;

    public static final String MYDATA = "MyData";
    public static final String isUserMail= "mail";
    public static final String isMailPass = "mailpass";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);

        btnGmail = findViewById(R.id.saveGmailDetails);

        sharedPreferences = getSharedPreferences(MYDATA, Context.MODE_PRIVATE);

        EditText userName = findViewById(R.id.txtusername);
        EditText userpass = findViewById(R.id.txtpassword);



        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = userName.getText().toString().trim();
                String userpas = userpass.getText().toString().trim();

                sharedPreferences.edit().putString(isUserMail,userid).apply();
                sharedPreferences.edit().putString(isMailPass,userpas).apply();

                Intent intent = new Intent(GmailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}