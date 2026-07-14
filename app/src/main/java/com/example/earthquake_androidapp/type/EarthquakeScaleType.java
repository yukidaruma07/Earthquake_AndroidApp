package com.example.earthquake_androidapp.type;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public enum EarthquakeScaleType {
    ZERO("震度0", Color.rgb(255, 255, 255)),
    ONE("震度1", Color.rgb(205, 210, 212)),
    TWO("震度2", Color.rgb(46, 198, 232)),
    THREE("震度3", Color.rgb(70, 102, 232)),
    FOUR("震度4", Color.rgb(250, 235, 160)),
    FIVE_LOW("震度5弱", Color.rgb(230, 195, 18)),
    FIVE_HIGH("震度5強", Color.rgb(230, 138, 18)),
    SIX_LOW("震度6弱", Color.rgb(255, 0, 0)),
    SIX_HIGH("震度6強", Color.rgb(99, 38, 34)),
    SEVEN("震度7", Color.rgb(118, 43, 161)),
    UNKNOWN("不明", Color.rgb(255, 255, 255));

    private final String text;
    private final int color;

    EarthquakeScaleType(String text, int color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public int getColorRaw() {
        return color;
    }

    public static EarthquakeScaleType convertP2PAPIType(int rawData) {
        switch (rawData) {
            case 10: { return EarthquakeScaleType.ONE; }
            case 20: { return EarthquakeScaleType.TWO; }
            case 30: { return EarthquakeScaleType.THREE; }
            case 40: { return EarthquakeScaleType.FOUR; }
            case 45: { return EarthquakeScaleType.FIVE_LOW; }
            case 50: { return EarthquakeScaleType.FIVE_HIGH; }
            case 55: { return EarthquakeScaleType.SIX_LOW; }
            case 60: { return  EarthquakeScaleType.SIX_HIGH; }
            case 70: { return EarthquakeScaleType.SEVEN; }
            default: { return  EarthquakeScaleType.UNKNOWN; }
        }
    }

}
