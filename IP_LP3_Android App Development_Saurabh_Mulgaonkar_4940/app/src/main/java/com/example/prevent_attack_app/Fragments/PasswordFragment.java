package com.example.prevent_attack_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prevent_attack_app.ActualMain;
import com.example.prevent_attack_app.R;


public class PasswordFragment extends Fragment {


    EditText etPassword,etConfirmPassword;
    Button btnNext;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_password, container, false);

        //init
        etPassword=view.findViewById(R.id.etPassword);
        etConfirmPassword=view.findViewById(R.id.etConfirmPassword);
        btnNext=view.findViewById(R.id.btnNext);

        sharedPreferences=getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=etPassword.getText().toString(),
                        confirmPassword=etConfirmPassword.getText().toString();
                if(password.equals("") || confirmPassword.equals("") || password.length()<4 || confirmPassword.length()<4)
                {
                    Toast.makeText(getActivity(), "All the above fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getActivity(),"Passwords don't match please type your 4 digit code again",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(getActivity(),"Great",Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit().putString("passcode",password).apply();
                    getFragmentManager().beginTransaction().replace(R.id.Frame,new EmergencyContactFragment()).commit();
                    //g0 to to the switch page
                }
            }
        });


        return view;
    }
}