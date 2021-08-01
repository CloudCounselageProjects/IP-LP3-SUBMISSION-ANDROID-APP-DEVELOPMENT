package com.kritin.attackpreventerpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    EditText oldpin,newpin,rnewpin;
    EditText fname2,lname2,email2,phone2;
    Button changepin,changedetils,save;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setValue();
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
            fname2.setVisibility(View.VISIBLE);
            lname2.setVisibility(View.VISIBLE);
            changedetils.setVisibility(View.GONE);
            email2.setVisibility(View.VISIBLE);
            changedetils.setVisibility(View.GONE);
            phone2.setVisibility(View.VISIBLE);
            changedetils.setVisibility(View.GONE);
            changepin.setVisibility(View.GONE);

        });

        save.setOnClickListener(v -> {
            Intent intent = new Intent(Setting.this,Activity2.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(!fname2.getText().toString().isEmpty() && !lname2.getText().toString().isEmpty() && !email2.getText().toString().isEmpty() && !phone2.getText().toString().isEmpty()){
                editor.putString("fname", fname2.getText().toString());
                editor.putString("lname",lname2.getText().toString());
                editor.putString("email",email2.getText().toString());
                editor.putString("ephone",phone2.getText().toString());
                editor.apply();
                startActivity(intent);
                finish();
            }
            if(!oldpin.getText().toString().isEmpty() &&
                    !newpin.getText().toString().isEmpty() &&
                    !rnewpin.getText().toString().isEmpty()){
                String pin = sharedPreferences.getString("epin", null);

                if(oldpin.getText().toString().equals(pin)){
                    editor.putString("epin",newpin.getText().toString());
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

    private void setValue() {

        oldpin = findViewById(R.id.editTextNumberPassword);
        newpin =  findViewById(R.id.editTextNumberPassword2);
        rnewpin = findViewById(R.id.editTextNumberPassword3);
        fname2= findViewById(R.id.fName2);
        lname2 = findViewById(R.id.lName2);
        email2 = findViewById(R.id.Email2);
        phone2 = findViewById(R.id.Phone2);
        changedetils = (Button)findViewById(R.id.changedetails);
        changepin = (Button)findViewById(R.id.changepin);
        save = (Button)findViewById(R.id.save);

    }
}