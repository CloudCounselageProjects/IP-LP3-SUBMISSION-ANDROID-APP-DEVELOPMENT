package com.example.alertapp.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alertapp.ChangePassword;
import com.example.alertapp.EditDetailsActivity;
import com.example.alertapp.R;

public class SettingsFragment extends Fragment {

    Button editPass, editProf;

    Dialog dialog;

    public static final String MYPREFERENCES = "MyPref";
    public static final String isPassword = "userPassword";

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        editPass = view.findViewById(R.id.edit_password);
        editProf = view.findViewById(R.id.edit_profile);

        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pa = sharedPreferences.getString(isPassword,"000");

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.edit_profile_password);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText check = dialog.findViewById(R.id.txtpass);
                Button btnOK = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String value = check.getText().toString();
                        if(value.equals(pa)){

                            Intent intent = new Intent(getActivity(), EditDetailsActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(getActivity(),"Incorrect password",Toast.LENGTH_SHORT).show();
                        }

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
        });

        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);
            }
        });

        return view;
    }
}