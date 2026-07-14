package com.example.earthquake_androidapp.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.earthquake_androidapp.R;
import com.example.earthquake_androidapp.adapter.EarthquakeAdapter;
import com.example.earthquake_androidapp.api.EarthquakeAPI;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEarthquakeAPI(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initEarthquakeAPI(View view) {
        new Thread() {
            @Override
            public void run() {
                List<EarthquakeAPI> apiList = Arrays.asList(EarthquakeAPI.getInstance());
                HomeFragment.this.getActivity().runOnUiThread(() -> {
                    RecyclerView recyclerView = view.findViewById(R.id.earthquake_recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new EarthquakeAdapter(apiList));
                });
            }
        }.start();
    }
}