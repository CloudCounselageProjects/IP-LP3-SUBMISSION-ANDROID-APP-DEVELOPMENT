package com.example.alertapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alertapp.Fragments.SettingsFragment;

public class EditDetailsActivity extends AppCompatActivity {

    public static final String MYPREFERENCES = "MyPref";
    public static final String isName = "userFirstName";
    public static final String isLast = "userLastName";
    public static final String isEmail = "userEmail";
    public static final String isEmgPhone = "emgPhone";
    public static final String isEmgEmail = "emgEmail";

    SharedPreferences sharedPreferences;

    EditText editFirstName, editLastName, editEmail, editEmgPhone, editEmgEmail;
    Button editDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        editFirstName = findViewById(R.id.edit_txtFirstName);
        editLastName = findViewById(R.id.edit_txtLastName);
        editEmail = findViewById(R.id.edit_txtEmail);
        editEmgEmail = findViewById(R.id.editEmgEmail);
        editEmgPhone = findViewById(R.id.editEmgPhone);
        editDetails = findViewById(R.id.editDetails);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        editFirstName.setText(sharedPreferences.getString(isName,"Null"));
        editLastName.setText(sharedPreferences.getString(isLast,"null"));
        editEmail.setText(sharedPreferences.getString(isEmail,"null"));
        editEmgPhone.setText(sharedPreferences.getString(isEmgPhone,"null"));
        editEmgEmail.setText(sharedPreferences.getString(isEmgEmail,"null"));

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDetails();

                Toast.makeText(EditDetailsActivity.this,"Changed Personal Info",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void changeDetails(){
        String fName = editFirstName.getText().toString().trim();
        String lName = editLastName.getText().toString().trim();
        String fEmail = editEmail.getText().toString().trim();
        String emgEmail = editEmgEmail.getText().toString().trim();
        String emgPhone = editEmgPhone.getText().toString().trim();

        sharedPreferences.edit().putString(isName,fName).apply();
        sharedPreferences.edit().putString(isLast,lName).apply();
        sharedPreferences.edit().putString(isEmail,fEmail).apply();
        sharedPreferences.edit().putString(isEmgEmail,emgEmail).apply();
        sharedPreferences.edit().putString(isEmgPhone,emgPhone).apply();

    }

}