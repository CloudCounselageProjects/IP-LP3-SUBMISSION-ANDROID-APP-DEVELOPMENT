package com.example.prevent_attack_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    RelativeLayout Personal,Password,Emergency;
    Dialog dialog;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences=getSharedPreferences("userData",MODE_PRIVATE);
        dialog=new Dialog(SettingsActivity.this);


        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,ActualMain.class));
                finish();
            }
        });

        Personal=findViewById(R.id.relPersonal);
        Password=findViewById(R.id.relPassword);
        Emergency=findViewById(R.id.relEmergency);

        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setPersonal();

            }
        });

        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setPassword();
            }
        });

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEmergency();
            }
        });


    }

    private void setPersonal()
    {
        String Name=sharedPreferences.getString("Name","Fname Lname");
        String Email=sharedPreferences.getString("Email","xyz@gmail.com");
        String Address=sharedPreferences.getString("Address","Home Address");

        dialog.setContentView(R.layout.dialog_personal_details);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnCancel=dialog.findViewById(R.id.btnCancel);
        Button btnSave=dialog.findViewById(R.id.btnSave);
        EditText etName=dialog.findViewById(R.id.etName);
        etName.setText(Name);
        EditText etEmail=dialog.findViewById(R.id.etEmail);
        etEmail.setText(Email);
        EditText etAddress=dialog.findViewById(R.id.etAddress);
        etAddress.setText(Address);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("Name",etName.getText().toString()).apply();
                sharedPreferences.edit().putString("Email",etEmail.getText().toString()).apply();
                sharedPreferences.edit().putString("Address",etAddress.getText().toString()).apply();
                dialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setPassword()
    {

        String password=sharedPreferences.getString("passcode","0000");

        dialog.setContentView(R.layout.dialog_pasword_change);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnCancel=dialog.findViewById(R.id.btnCancel);
        Button btnChange=dialog.findViewById(R.id.btnChange);
        EditText etOldPassword=dialog.findViewById(R.id.etOldPassword);
        EditText etNewPassword=dialog.findViewById(R.id.etNewPassword);
        EditText etConfirmPassword=dialog.findViewById(R.id.etConfirmPassword);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=etOldPassword.getText().toString();
                if(!pass.equals(password))
                {
                    Toast.makeText(SettingsActivity.this,"Old password not correct",Toast.LENGTH_SHORT).show();
                }
                if(!etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
                {
                    Toast.makeText(SettingsActivity.this,"Passwords don't match",Toast.LENGTH_SHORT).show();
                }

                if(pass.equals(password) && etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString()))
                {
                    sharedPreferences.edit().putString("passcode",etNewPassword.getText().toString()).apply();
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setEmergency()
    {
        String emergencyEmail=sharedPreferences.getString("emergencyEmail","abc@gmail.com");
        String emergemcyNumber=sharedPreferences.getString("emergencyContact","1234567890");

        dialog.setContentView(R.layout.dialog_emergency_details);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnCancel=dialog.findViewById(R.id.btnCancel);
        Button btnSave=dialog.findViewById(R.id.btnSave);
        EditText etEmergecyContact=dialog.findViewById(R.id.etEmergencyContact);
        etEmergecyContact.setText(emergemcyNumber);
        EditText etEmergencyEmail=dialog.findViewById(R.id.etEmergencyEmail);
        etEmergencyEmail.setText(emergencyEmail);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("emergencyEmail",etEmergencyEmail.getText().toString()).apply();
                sharedPreferences.edit().putString("emergencyContact",etEmergecyContact.getText().toString()).apply();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}