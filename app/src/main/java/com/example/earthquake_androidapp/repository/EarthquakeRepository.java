package com.example.earthquake_androidapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.earthquake_androidapp.api.EarthquakeAPI;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

/**
 * 地震データの取得を管理するリポジトリクラス
 */
public class EarthquakeRepository {

    private static EarthquakeRepository instance;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Gson gson = new Gson();
    private final MutableLiveData<List<EarthquakeAPI>> earthquakeList = new MutableLiveData<>();

    private EarthquakeRepository() {}

    public static synchronized EarthquakeRepository getInstance() {
        if (instance == null) {
            instance = new EarthquakeRepository();
        }
        return instance;
    }

    public LiveData<List<EarthquakeAPI>> getEarthquakeList() {
        return earthquakeList;
    }

    public interface Callback<T> {
        void onResult(T result);
        void onError(Exception e);
    }

    /**
     * 最新の地震リストを取得し、LiveDataを更新する
     */
    public void fetchEarthquakes(Callback<List<EarthquakeAPI>> callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL("https://api.p2pquake.net/v2/history?codes=551");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    EarthquakeAPI[] response = gson.fromJson(builder.toString(), EarthquakeAPI[].class);
                    if (response != null) {
                        List<EarthquakeAPI> list = Arrays.asList(response);
                        earthquakeList.postValue(list);
                        if (callback != null) callback.onResult(list);
                    } else {
                        if (callback != null) callback.onError(new Exception("取得に失敗しました"));
                    }
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                if (callback != null) callback.onError(e);
            }
        });
    }
}
