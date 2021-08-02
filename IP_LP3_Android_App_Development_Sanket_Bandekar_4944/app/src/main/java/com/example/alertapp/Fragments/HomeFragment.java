package com.example.alertapp.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertapp.CheckUserActivity;
import com.example.alertapp.DetailsActivity;
import com.example.alertapp.MainActivity;
import com.example.alertapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class HomeFragment extends Fragment {

    Button sendHelp;
    TextView timer;
    boolean isSend = false;

    Dialog dialog;

    private boolean isPause = false;
    private long timeRemaining = 0;
    CountDownTimer countDownTimer;

    public static final String MYPREFERENCES = "MyPref";
    public static final String isEmgPhone = "emgPhone";
    public static final String isName = "userFirstName";
    public static final String isLast = "userLastName";
    public static final String isPassword = "userPassword";
    public static final String isEmgEmail = "emgEmail";

    public static final String MYDATA = "MyData";
    public static final String isUserMail= "mail";
    public static final String isMailPass = "mailpass";


    public String lati = "";
    public String longi = "";
    public String loca = "";
    public String message = "Please help me";

    SharedPreferences sharedPreferences;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        sharedPreferences = this.getActivity().getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        sendHelp = view.findViewById(R.id.btnHelp);
        timer = view.findViewById(R.id.txtTimer);
        timer.setVisibility(View.INVISIBLE);

        String mobile = sharedPreferences.getString(isEmgPhone, "null").trim();
        String name = sharedPreferences.getString(isName, "null");
        String lastName = sharedPreferences.getString(isLast, "null");
        String sendTo = sharedPreferences.getString(isEmgEmail,"null");
        Log.d("values1", "user1: " + mobile + " " + name);
        //String mobile = "7083915326";

        //timer function
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timer.setText("" + millisUntilFinished / 1000);
                timeRemaining = millisUntilFinished;

            }

            @Override
            public void onFinish() {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.SEND_SMS}, 44);

                }

                //to send message
                sendMessage(mobile, message, loca, name, lastName);

                sendMail(name, loca, sendTo,lastName);

                isSend = false;
                sendHelp.setText("Send Help");
                timer.setVisibility(View.INVISIBLE);

            }
        };

        sendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSend){
                    //open alert
                    popUp();
                }else{

                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(getActivity(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                    }
                    getCurrentLocation();

                    timer.setVisibility(View.VISIBLE);
                    sendHelp.setText("Stop");
                    countDownTimer.start();
                    isSend = true;

                }

            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return view;
    }

    public void popUp(){

        String pa = sharedPreferences.getString(isPassword,"000");

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_gmail_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText check = dialog.findViewById(R.id.txtpass);
        Button btnOK = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = check.getText().toString();

                if(!value.equals(pa)){
                    Toast.makeText(getActivity(),"Incorrect Password", Toast.LENGTH_SHORT).show();
                }else{
                    countDownTimer.cancel();
                    sendHelp.setText("Send Help");
                    timer.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                }
                isSend = false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        }else{
            Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();

                    if(location != null){
                        lati = String.valueOf(location.getLatitude());
                        longi = String.valueOf(location.getLongitude());
                        loca = "https://maps.google.com/?="+ lati +","+ longi;

                        Log.d("latitude",lati);
                        Log.d("longitude", longi);
                        Log.d("link",loca);

                    }else{
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(10000)
                                .setNumUpdates(1);

                        //initialize callback

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                lati = String.valueOf(location1.getLatitude());
                                longi = String.valueOf(location1.getLongitude());

                                loca = "https://maps.google.com/?="+ lati +","+ longi;

                                Log.d("latitude",lati);
                                Log.d("longitude", longi);
                                Log.d("link",loca);


                            }
                        };

                        //Request location updates
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }

                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    private void sendMessage(String mob, String mess,String l, String name, String lname) {

        try {

            String message = mess + " " +"\n"
                    + "I am " + name +" " +lname+"\n"
                    + "My Current Location : " +l +"\n"
                    + "Please send HELP IMMEDIATELY";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mob,null,message,null,null);
            Toast.makeText(getActivity(),"Message Sent", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getActivity(),"Error: " +e, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMail(String name, String l, String send, String lastName){

        // "ultron.developer29@gmail.com" "ultron@32Dev"

        sharedPreferences = this.getActivity().getSharedPreferences(MYDATA, Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString(isUserMail,"null");
        final String password = sharedPreferences.getString(isMailPass,"null");
        String mess = message;

        String message_gmail = mess + " " +"\n"
                + "I am " + name +" " +lastName+"\n"
                + "My Current location: " +l +"\n"
                + "Please send HELP IMMEDIATELY";

        //to send gmail message
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(username));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(send));
            message1.setSubject("In Danger Send Help");
            message1.setText(message_gmail);
            Transport.send(message1);

            Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Log.d("Error", e.toString());
            Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

}