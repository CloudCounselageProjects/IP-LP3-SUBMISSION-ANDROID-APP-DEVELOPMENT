package com.example.preventattack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settingpage extends AppCompatActivity {

    EditText oldpin,newpin,rnewpin;
    EditText firstname,lastname;
    Button changepin,changedetils,save;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpage);
        setvalues();
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);


        changepin.setOnClickListener(v -> {
            save.setVisibility(View.VISIBLE);
            changepin.setVisibility(View.GONE);
            changedetils.setVisibility(View.GONE);
            oldpin.setVisibility(View.VISIBLE);
            newpin.setVisibility(View.VISIBLE);
            rnewpin.setVisibility(View.VISIBLE);
        });

        changedetils.setOnClickListener(v -> {
            save.setVisibility(View.VISIBLE);
            changedetils.setVisibility(View.GONE);
            firstname.setVisibility(View.VISIBLE);
            lastname.setVisibility(View.VISIBLE);
            changedetils.setVisibility(View.GONE);
            changepin.setVisibility(View.GONE);
        });

        save.setOnClickListener(v -> {
            Intent intent = new Intent(settingpage.this,Home.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()){
                editor.putString("fnamekey", firstname.getText().toString());
                editor.putString("lnamekey",lastname.getText().toString());
                editor.apply();
                startActivity(intent);
                finish();
            }
            if(!oldpin.getText().toString().isEmpty() &&
                    !newpin.getText().toString().isEmpty() &&
                    !rnewpin.getText().toString().isEmpty()){
                String pin = sharedPreferences.getString("pinkey", null);

                if(oldpin.getText().toString().equals(pin)){
                    editor.putString("pinkey",newpin.getText().toString());
                    editor.apply();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this, "Please Enter Same Old Pin", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "ENTER ALL DETAILS", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setvalues(){
        oldpin = findViewById(R.id.editTextNumberPassword);
        newpin = findViewById(R.id.editTextNumberPassword2);
        rnewpin = findViewById(R.id.editTextNumberPassword3);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        changedetils = (Button)findViewById(R.id.changedetails);
        changepin = (Button)findViewById(R.id.changepin);
        save = (Button)findViewById(R.id.save);

    }
}