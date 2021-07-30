package com.example.preventattack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Home extends AppCompatActivity {

    TextView name,alert;
    Button sos;
    EditText cpin;
    Button setting;
    Button cancel;
    SharedPreferences sharedPreferences;
    String c = "CANCEL";
    String[] PERMISSIONS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    String message;
    double lat,lng;
    CountDownTimer countDownTimer;

    FusedLocationProviderClient fusedLocationProviderClient;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setvalues();

        String fname = sharedPreferences.getString("fnamekey", null);
        String lname = sharedPreferences.getString("lnamekey", null);
        String no = sharedPreferences.getString("numkey", null);
        String pin = sharedPreferences.getString("pinkey", null);

        name.setText(fname + " " + lname);

        cancel.setText(c);

        getCurrentLocation();

        sos.setOnClickListener(v -> {

            sos.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            setting.setVisibility(View.GONE);
            alert.setVisibility(View.VISIBLE);
            cpin.setVisibility(View.VISIBLE);

            countDownTimer = new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    cancel.setText(c+" (" + millisUntilFinished / 1000+")");
                }

                public void onFinish() {

                    if (checkSelfPermission(PERMISSIONS[0]) == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(PERMISSIONS[1]) == PackageManager.PERMISSION_GRANTED) {

                        message = "Hey, " + fname +" "+ lname + " here. I am in DANGER, I need help. Please URGENTLY reach me out.\nHere are my coordinates\n"
                                + "http://maps.google.com/?q="+lat+","+lng;
                        Toast.makeText(Home.this, "SENDING SOS", Toast.LENGTH_SHORT).show();
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(no , null, message, null, null);
//                            Intent email = new Intent(Intent.ACTION_SEND);
//                            email.putExtra(Intent.EXTRA_EMAIL, "b.manesh.b@gmail.com");
//                            email.putExtra(Intent.EXTRA_SUBJECT, "subject");
//                            email.putExtra(Intent.EXTRA_TEXT, message);
//                            email.setType("text/plain");
//                            try {
//                                startActivity(Intent.createChooser(email, "Send mail..."));
//                                finish();
//                                Log.i("Finished sending email...", "");
//                            } catch (android.content.ActivityNotFoundException ex) {
//                                Toast.makeText(Home.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                            }
                        cancel.setVisibility(View.GONE);
                        setting.setVisibility(View.VISIBLE);
                        sos.setVisibility(View.VISIBLE);
                        alert.setVisibility(View.GONE);
                        cpin.setVisibility(View.GONE);

                    } else {
                        requestPermissions(PERMISSIONS, 10);
                    }
                }
            }.start();

        });

        cancel.setOnClickListener(v -> {

            if(cpin.getText().toString().equals(pin)){
                Toast.makeText(Home.this, "SOS CANCELLED", Toast.LENGTH_SHORT).show();
                cancel.setVisibility(View.GONE);
                sos.setVisibility(View.VISIBLE);
                setting.setVisibility(View.VISIBLE);
                alert.setVisibility(View.GONE);
                cpin.setVisibility(View.GONE);
                countDownTimer.cancel();
            }else {
                Toast.makeText(Home.this, "PLEASE ENTER CORRECT PIN", Toast.LENGTH_SHORT).show();
            }
        });


        setting.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, settingpage.class);
            startActivity(intent);
            finish();


        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Home.this);
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

    private void setvalues(){
        name = (TextView)findViewById(R.id.textView3);
        alert = (TextView)findViewById(R.id.textView4);
        sos = (Button) findViewById(R.id.sos);
        setting = (Button) findViewById(R.id.setting);
        cancel = (Button) findViewById(R.id.cancel);
        cpin = (EditText) findViewById(R.id.cpin);
        sharedPreferences = getSharedPreferences("mypref",MODE_PRIVATE);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 10) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permissions Denied!\n Can't use the App!", Toast.LENGTH_SHORT).show();
//            }else{
//                requestPermissions(PERMISSIONS,10);
//            }
//        }
//    }
}