package com.example.prevent_attack_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.prevent_attack_app.Fragments.EnterDetails;

public class MainActivity extends AppCompatActivity {

    FrameLayout Frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Frame=findViewById(R.id.Frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new EnterDetails()).commit();





    }
}