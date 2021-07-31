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

public class EmergencyContactFragment extends Fragment {

    EditText etEmergencyNumber,getEtEmergencyEmail;
    Button btnSave;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_emergency_contact, container, false);

        sharedPreferences=getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

        etEmergencyNumber=view.findViewById(R.id.etEmergencyNumber);
        getEtEmergencyEmail=view.findViewById(R.id.etEmergencyEmail);
        btnSave=view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number= etEmergencyNumber.getText().toString();
                String email=getEtEmergencyEmail.getText().toString();

                if(number.length()<10 || number.isEmpty() || email.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Enter the proper details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sharedPreferences.edit().putString("emergencyEmail",email).apply();
                    sharedPreferences.edit().putString("emergencyContact", number).apply();
                    startActivity(new Intent(getActivity(), ActualMain.class));
                    getActivity().finish();

                }


            }
        });


        return view;
    }
}