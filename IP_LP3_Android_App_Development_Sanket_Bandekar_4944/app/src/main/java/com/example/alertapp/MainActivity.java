package com.example.alertapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.alertapp.Fragments.DetailsFragment;
import com.example.alertapp.Fragments.HomeFragment;
import com.example.alertapp.Fragments.SettingsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {


    MeowBottomNavigation bottomNavigation;

    private String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PERMISSIONS = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET
        };

        if(!hasPermission(MainActivity.this,PERMISSIONS)){
            ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS,1);
        }


        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_set));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch (item.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new SettingsFragment();
                        break;
                }

                loadFragment(fragment);
            }
        });

        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //display toast when clicked
                Toast.makeText(getApplicationContext(), "You Clicked " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "Rechecked " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean hasPermission(Context context, String... PERMISSIONS){
        if(context != null && PERMISSIONS != null){
            for(String permission: PERMISSIONS){
               if(ActivityCompat.checkSelfPermission(context,permission)
               != PackageManager.PERMISSION_GRANTED){
                   return false;
               }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"SMS Permission Granted",Toast.LENGTH_SHORT).show();
            }
            if(grantResults[1]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Location Permission Granted",Toast.LENGTH_SHORT).show();
            }
            if(grantResults[2]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Internet Permission Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}