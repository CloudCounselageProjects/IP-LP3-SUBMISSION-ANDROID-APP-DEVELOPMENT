package com.example.prevent_attack_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.security.acl.Permission;

public class ActualMain extends AppCompatActivity {


    Button btnHelp;
    TextView txtTimer;
    CountDownTimer countDownTimer;
    boolean send = false;
    int timer = 10;
    SharedPreferences sharedPreferences;
    ImageView imgSettings;
    Dialog dialog;
    FusedLocationProviderClient fusedLocationProviderClient;
    String location_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.INTERNET)!=PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET}, 1);
            } else {
                getLocation();
            }


        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ActualMain.this);

        sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String ContactNumber = sharedPreferences.getString("emergencyContact", "1000000000");
        String ContactEmail= sharedPreferences.getString("emergencyEmail", "xzy@abc.com");

        btnHelp = findViewById(R.id.btnHelp);
        txtTimer = findViewById(R.id.txtTimer);
        txtTimer.setVisibility(View.INVISIBLE);
        imgSettings = findViewById(R.id.imgSettings);

        //settings clickListener
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwordDialog(0);
                //finish();
            }
        });



        //Timer
        countDownTimer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {

                txtTimer.setText(Integer.toString(timer));
                txtTimer.setTextSize(50);
                timer = timer - 1;


            }

            @Override
            public void onFinish() {

                sendSms(ContactNumber,ContactEmail);
                txtTimer.setTextSize(50);
                txtTimer.setText("SMS Sent");
                timer = 10;
                send = false;
                btnHelp.setText("Help");
                imgSettings.setVisibility(View.VISIBLE);


            }
        };


        //Main button(Help)
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (send) {

                    passwordDialog(1);

                } else {

                    if (ActivityCompat.checkSelfPermission(ActualMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActualMain.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    ) {

                        getLocation();
                    } else {
                        ActivityCompat.requestPermissions(ActualMain.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION},
                                100);
                    }
                    txtTimer.setVisibility(View.VISIBLE);
                    btnHelp.setText("Stop");
                    countDownTimer.start();
                    send = true;
                    int timer = 10;
                    imgSettings.setVisibility(View.INVISIBLE);

                }

            }
        });


    }

    //After accepting or rejecting permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 &&
                (grantResults[0] + grantResults[1] + grantResults[2] + grantResults[3] == PackageManager.PERMISSION_GRANTED)) {
            getLocation();
        } else {
            Toast.makeText(ActualMain.this, "Please tap the help button again", Toast.LENGTH_SHORT).show();
        }
    }


    //Send SMS
    private void sendSms(String number,String email) {
        String message = "This message is not a prank\nI need your help\nI am " + sharedPreferences.getString("Name", "Don")+ "\nHelp me at " + location_address;
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(ActualMain.this, "Address: " + location_address + "\nMessage was sent by " + sharedPreferences.getString("Name", "Don") + " " + number, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ActualMain.this, "Some Error occurred while sending the sms", Toast.LENGTH_SHORT).show();
        }

        //Mail Sent here
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+email));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Help Required");
        intent.putExtra(Intent.EXTRA_TEXT,message);
        startActivity(intent);
    }


    //Dialog Box to enter password
    private void passwordDialog(int x) {
        String pass = sharedPreferences.getString("passcode", "0000");
        dialog = new Dialog(ActualMain.this);
        dialog.setContentView(R.layout.dialog_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        EditText etPassword = dialog.findViewById(R.id.etPassword);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString();
                if (!pass.equals(password)) {
                    Toast.makeText(ActualMain.this, "Please Enter the correct password", Toast.LENGTH_SHORT).show();
                } else {
                    if (x == 1) {

                        txtTimer.setVisibility(View.INVISIBLE);
                        btnHelp.setText("Help");
                        countDownTimer.cancel();
                        send = false;
                        timer = 10;
                        imgSettings.setVisibility(View.VISIBLE);

                    } else {
                        startActivity(new Intent(ActualMain.this, SettingsActivity.class));
                    }
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
    }


    //GET and UPDATE Location details
    @SuppressLint("MissingPermission")
    private void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location != null) {

                        location_address = "Location : https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();


                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                location_address = "Location : https://maps.google.com/?q=" + location1.getLatitude() + "," + location1.getLongitude();


                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }


}