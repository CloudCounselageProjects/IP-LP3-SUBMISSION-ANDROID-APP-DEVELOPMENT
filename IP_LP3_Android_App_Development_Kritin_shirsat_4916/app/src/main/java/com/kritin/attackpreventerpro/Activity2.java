package com.kritin.attackpreventerpro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Activity2 extends AppCompatActivity {


    private EditText number;
    private Button prevent;
    String message;
    TextView VName,VLastName,VEmail,VPhone;
    Button logOut;
    Button Setting;
    Button cancle;
    EditText cpin;

    SharedPreferences sharedPreferences;
    String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    double lat,lng;
    CountDownTimer countDownTimer;

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        number = findViewById(R.id.number);
        prevent = findViewById(R.id.prevent);
        VName = findViewById(R.id.VName);
        VLastName = findViewById(R.id.VLastName);
        VEmail = findViewById(R.id.VEmail);
        VPhone = findViewById(R.id.VPhone);
        logOut = findViewById(R.id.logOut);
        Setting = findViewById(R.id.Setting);
        cancle = findViewById(R.id.cancel);
        cpin = findViewById(R.id.cpin);

        //for getting data
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);

        String fname = sharedPreferences.getString("fname",null);
        String lname = sharedPreferences.getString("lname",null);
        String email = sharedPreferences.getString("email",null);
        String ephone = sharedPreferences.getString("ephone",null);
        String pin = sharedPreferences.getString("epin",null);
        String repin = sharedPreferences.getString("erepin",null);

        if (fname !=null || lname !=null || email !=null || ephone !=null){
            //data on text view
            VName.setText("Name:- "+fname);
            VLastName.setText("Last Name:- "+lname);
            VEmail.setText("Email:- "+email);
            VPhone.setText("Phone NO:-"+ephone);
        }

        getCurrentLocation();

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cpin.getText().toString().equals(pin)) {
                    countDownTimer.cancel();

                    prevent.setVisibility(View.VISIBLE);
                    cancle.setVisibility(View.GONE);
                    cpin.setVisibility(View.GONE);

                }else {
                    Toast.makeText(Activity2.this, "ENTER CORRECT PIN", Toast.LENGTH_SHORT).show();
                }

            }
        });

        prevent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                prevent.setVisibility(View.GONE);
                cancle.setVisibility(View.VISIBLE);
                cpin.setVisibility(View.VISIBLE);

                countDownTimer = new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        cancle.setText("CANCLE (" + millisUntilFinished / 1000+")");
                    }

                    public void onFinish() {

                        if (checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                                checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {

                            String phoneNO = number.getText().toString().trim();
                            message = "HELP!!!"+fname+" PLEASE HELP!!!,Am in Danger , Please call him , His NO:-"+ ephone +".\nHere is his Current Location\n" +"http://maps.google.com/?q="+lat+","+lng;
                            Toast.makeText(Activity2.this, "ALERTING", Toast.LENGTH_SHORT).show();
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phoneNO , null, message, null, null);
                        } else {
                            requestPermissions(PERMISSIONS, 10);
                        }
                    }
                }.start();
                

                if (checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {
                    String phoneNO = number.getText().toString().trim();

                    try {

                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(phoneNO , null, message, null, null);
                        Toast.makeText(Activity2.this,"the Number is Alerted About the situation ", Toast.LENGTH_SHORT).show();
                    } catch (Exception e ){
                        e.printStackTrace();
                        Toast.makeText(Activity2.this,"Faild to send message!!!",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    requestPermissions(PERMISSIONS, 10);
                }

            }
        });

        //log out
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                finish();
                Toast.makeText(Activity2.this,"Log out Successful",Toast.LENGTH_SHORT).show();


            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });

    }

    private void openSetting() {

        Intent intent = new Intent(this,Setting.class);
        startActivity(intent);


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Activity2.this);
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Location> task) {
                    Location location = task.getResult();
                    if( location != null){
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        Toast.makeText(Activity2.this, String.valueOf(lng) , Toast.LENGTH_SHORT).show();
                    }else{
                        //init location
                        LocationRequest locationRequest =new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        //init call back location
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();
                                lat = location1.getLatitude();
                                lng = location1.getLongitude();
                            }
                        };
                        //req lacation update
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest
                                ,locationCallback, Looper.getMainLooper());
                    }
                }
            });

        }else{
            //when location not enable open toggle setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }
}