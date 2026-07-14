package com.example.earthquake_androidapp.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class EarthquakeAPI {

    public static EarthquakeAPI[] getInstance() {
        try {
            URL url = new URL("https://api.p2pquake.net/v2/history?codes=551");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            return new Gson().fromJson(builder.toString(), EarthquakeAPI[].class);

        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Earthquake earthquake;
    private Point[] points;

    public Earthquake getEarthquake() { return earthquake; }
    public Point[] getPoints() { return points; }

    public static class Earthquake {
        private String time;
        private int maxScale;
        private String domesticTsunami;
        private String foreignTsunami;
        private Hypocenter hypocenter;

        public String getRawTime() { return time; }
        public int getRawScale() { return maxScale; }
        public String getDomesticTsunami() { return domesticTsunami; }
        public String getForeignTsunami() { return foreignTsunami; }
        public Hypocenter getHypocenter() { return hypocenter; }

        public static class Hypocenter {
            private String name;
            private float latitude;
            private float longitude;
            private float depth;
            private float magnitude;

            public String getName() { return name; }
            public float getLatitude() { return latitude; }
            public float getLongitude() { return longitude; }
            public float getDepth() { return depth; }
            public float getMagnitude() { return magnitude; }

        }
    }

    public static class Point {
        private String pref;
        private String addr;
        private int scale;


    }

}
