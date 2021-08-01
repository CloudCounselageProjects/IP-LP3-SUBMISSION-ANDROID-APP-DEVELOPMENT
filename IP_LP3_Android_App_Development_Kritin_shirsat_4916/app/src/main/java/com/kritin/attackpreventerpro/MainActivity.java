package com.kritin.attackpreventerpro;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText fName,lName,eMail,ePhone,ePin,eRePin;
    Button save;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EPHONE = "ephone";
    private static final String KEY_EPIN = "epin";
    private static final String KEY_EREPIN = "erepin";

    String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fName =  findViewById(R.id.fName);
        lName =  findViewById(R.id.lName);
        eMail =  findViewById(R.id.eMail);
        ePhone = findViewById(R.id.ePhone);
        ePin =   findViewById(R.id.ePin);
        eRePin = findViewById(R.id.eRePin);
        save = findViewById(R.id.Save);

        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE );

        if(fName != null ){

            Intent intent = new Intent(MainActivity.this,Activity2.class);
            startActivity(intent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {


            }else {
                requestPermissions(PERMISSIONS, 10);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fname",fName.getText().toString());
                editor.putString("lname",lName.getText().toString());
                editor.putString("email",eMail.getText().toString());
                editor.putString("ephone",ePhone.getText().toString());
                editor.putString("epin",ePin.getText().toString());
                editor.putString("erepin",eRePin.getText().toString());
                editor.apply();

                Intent intent = new Intent(MainActivity.this,Activity2.class);   //goes to activity 2..
                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                startActivity(intent);



            }
        });



    }


    }
