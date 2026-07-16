package com.example.earthquake_androidapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.earthquake_androidapp.api.EarthquakeAPI;
import com.example.earthquake_androidapp.repository.EarthquakeRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final EarthquakeRepository repository = EarthquakeRepository.getInstance();

    public LiveData<List<EarthquakeAPI>> getLiveData() {
        return repository.getEarthquakeList();
    }

    public void initEarthquakeData() {
        repository.fetchEarthquakes(null);
    }
}
