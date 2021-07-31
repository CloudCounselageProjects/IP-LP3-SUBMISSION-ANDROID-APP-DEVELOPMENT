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

public class EnterDetails extends Fragment {

    EditText etFname,etLname,etAddress,etEmail;
    Button btnNext;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_enter_details, container, false);

        //initialization
        etAddress=view.findViewById(R.id.etAddress);
        etFname=view.findViewById(R.id.etFname);
        etLname=view.findViewById(R.id.etLname);
        etEmail=view.findViewById(R.id.etEmail);
        btnNext=view.findViewById(R.id.btnNext);
        sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("user",false))
        {
            //getFragmentManager().beginTransaction().replace(R.id.Frame,new PasswordFragment()).commit();
            Intent intent =new Intent(getActivity(), ActualMain.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                String name=etFname.getText().toString()+" "+etLname.getText().toString();
                String address=etAddress.getText().toString();

                if(email.equals("") || etFname.getText().toString().equals("")  ||
                        etLname.getText().toString().equals("")  || address.equals("") )
                {
                    Toast.makeText(getActivity(),"All the Above fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //data getting saved in shared preferences
                    sharedPreferences.edit().putBoolean("user",true).apply();
                    sharedPreferences.edit().putString("Name",name).apply();
                    sharedPreferences.edit().putString("Email",email).apply();
                    sharedPreferences.edit().putString("Address",address).apply();
                    //goes to the password fragment
                    getFragmentManager().beginTransaction().replace(R.id.Frame,new PasswordFragment()).commit();

                }
            }
        });


        return view;
    }
}