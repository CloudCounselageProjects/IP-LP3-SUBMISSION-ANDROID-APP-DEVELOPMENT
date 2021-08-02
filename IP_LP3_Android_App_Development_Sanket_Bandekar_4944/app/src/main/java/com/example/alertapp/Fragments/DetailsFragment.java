package com.example.alertapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alertapp.R;


public class DetailsFragment extends Fragment {

    public static final String MYPREFERENCES = "MyPrefs";
    public static final String isUser = "false";

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);

        return view;
    }
}