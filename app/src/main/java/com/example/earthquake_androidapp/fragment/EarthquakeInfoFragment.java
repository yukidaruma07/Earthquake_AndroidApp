package com.example.earthquake_androidapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.earthquake_androidapp.R;

public class EarthquakeInfoFragment extends Fragment {

    public EarthquakeInfoFragment() {
        // Required empty public constructor
    }

    public static EarthquakeInfoFragment newInstance(String param1, String param2) {
        EarthquakeInfoFragment fragment = new EarthquakeInfoFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_earthquake_info, container, false);
    }
}