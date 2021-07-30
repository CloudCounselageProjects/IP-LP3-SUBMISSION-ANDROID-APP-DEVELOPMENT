package com.example.preventattack;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText email;
    EditText pin;
    EditText repin;
    EditText ephone;
    EditText ememail;
    Button save;
    TextView altert;
    boolean login = false;
    String name;
    String[] PERMISSIONS= {
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED ){
        }
        else{
            requestPermissions(PERMISSIONS,10);
        }
        
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);

        setvalues();

        name = sharedPreferences.getString("fnamekey",null );



        if(name != null ){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();

        }

        save.setOnClickListener(v -> {

            if(!fname.getText().toString().isEmpty() &&
                    !lname.getText().toString().isEmpty() &&
                    !email.getText().toString().isEmpty() &&
                    !pin.getText().toString().isEmpty() &&
                    !repin.getText().toString().isEmpty() &&
                    !ephone.getText().toString().isEmpty() &&
                    !ememail.getText().toString().isEmpty()){
                validpin();
                setprefvalues();
            }
            else {
                Toast.makeText(MainActivity.this, "Please Enter all Details", Toast.LENGTH_SHORT).show();
            }


            if(login){
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void setvalues(){

        fname = (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        email = (EditText)findViewById(R.id.emailid);
        pin = (EditText)findViewById(R.id.pin);
        repin = (EditText)findViewById(R.id.repin);
        ephone = (EditText)findViewById(R.id.ephone);
        ememail = (EditText)findViewById(R.id.ememail);
        save = (Button)findViewById(R.id.save);
        altert = (TextView)findViewById(R.id.altert);

    }


    private void validpin(){

        if(pin.getText().toString().length()>0 &&
                pin.getText().toString().length()<5) {

            if (!pin.getText().toString().equals(repin.getText().toString())) {
                altert.setVisibility(View.VISIBLE);
            } else {
                altert.setVisibility(View.INVISIBLE);
                login = true;
            }
        }else{
            Toast.makeText(getApplicationContext(),"Enter 4 digits PIN only",Toast.LENGTH_SHORT).show();
            altert.setVisibility(View.INVISIBLE);
        }

    }
    private void setprefvalues(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fnamekey", fname.getText().toString());
        editor.putString("lnamekey",lname.getText().toString());
        editor.putString("emailkey",email.getText().toString());
        editor.putString("pinkey",pin.getText().toString());
        editor.putString("numkey",ephone.getText().toString());
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}