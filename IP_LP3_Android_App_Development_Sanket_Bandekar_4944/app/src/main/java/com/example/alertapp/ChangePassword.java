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

public class ChangePassword extends AppCompatActivity {

    public static final String MYPREFERENCES = "MyPref";
    public static final String isPassword = "userPassword";
    public static final String isConfirm = "userConfirmPass";

    SharedPreferences sharedPreferences;

    EditText oldPassword, newPassword, confNewPass;
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.txt_oldPassword);
        newPassword = findViewById(R.id.txt_newPassword);
        confNewPass = findViewById(R.id.txtConfirmNewPass);
        changePassword = findViewById(R.id.editPassword);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();

                Toast.makeText(ChangePassword.this,"Password Saved Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void changePass(){
        String oldP = oldPassword.getText().toString().trim();
        String newP =  newPassword.getText().toString().trim();
        String conf = confNewPass.getText().toString().trim();

        String getOld = sharedPreferences.getString(isPassword,"null");

        if(getOld.equals(oldP)){
            sharedPreferences.edit().putString(isPassword,newP).apply();
            sharedPreferences.edit().putString(isConfirm,conf).apply();
        }else{
            Toast.makeText(ChangePassword.this, "Incorrect Old Password no changes saved", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChangePassword.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}