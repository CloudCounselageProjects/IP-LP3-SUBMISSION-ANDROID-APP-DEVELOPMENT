package com.example.alertapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    public static final String MYPREFERENCES = "MyPref";
    public static final String isUser = "isActive";
    public static final String isName = "userFirstName";
    public static final String isLast = "userLastName";
    public static final String isEmail = "userEmail";
    public static final String isPassword = "userPassword";
    public static final String isConfirm = "userConfirmPass";
    public static final String isEmgPhone = "emgPhone";
    public static final String isEmgEmail = "emgEmail";


    SharedPreferences sharedPreferences;

    EditText firstName, lastName, email, password, confirmPassword, emergencyPhone, emergencyEmail;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConfirmPass);
        emergencyPhone = findViewById(R.id.txtEmgPhone);
        emergencyEmail = findViewById(R.id.txtEmgEmail);
        btn_save = findViewById(R.id.saveDetails);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f_name = firstName.getText().toString();
                String l_name = lastName.getText().toString();
                String f_email = email.getText().toString();
                String f_pass = password.getText().toString();
                String f_conf = confirmPassword.getText().toString();
                String e_phone = emergencyPhone.getText().toString();
                String e_email = emergencyEmail.getText().toString();

                sharedPreferences.edit().putString(isUser, "true").apply();
                sharedPreferences.edit().putString(isName,f_name).apply();
                sharedPreferences.edit().putString(isLast,l_name).apply();
                sharedPreferences.edit().putString(isEmail,f_email).apply();
                sharedPreferences.edit().putString(isPassword,f_pass).apply();
                sharedPreferences.edit().putString(isConfirm,f_conf).apply();
                sharedPreferences.edit().putString(isEmgPhone,e_phone).apply();
                sharedPreferences.edit().putString(isEmgEmail,e_email).apply();

                //Toast.makeText(DetailsActivity.this,"user "+f_name+l_name+f_email+e_phone, Toast.LENGTH_LONG).show();
                //Log.d("user",f_name);
                Intent intent = new Intent(DetailsActivity.this, GmailActivity.class);
                startActivity(intent);
            }
        });

    }

}