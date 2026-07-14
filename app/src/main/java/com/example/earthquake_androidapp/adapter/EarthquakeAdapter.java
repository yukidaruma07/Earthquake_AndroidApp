package com.example.earthquake_androidapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earthquake_androidapp.R;
import com.example.earthquake_androidapp.api.EarthquakeAPI;
import com.example.earthquake_androidapp.type.EarthquakeScaleType;

import org.w3c.dom.Text;

import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {

    private List<EarthquakeAPI> data;

    public EarthquakeAdapter(List<EarthquakeAPI> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public EarthquakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_earthqaukeinfo, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EarthquakeAdapter.ViewHolder holder, int position) {
        EarthquakeAPI data = this.data.get(position);
        EarthquakeAPI.Earthquake earthquake = data.getEarthquake();
        EarthquakeAPI.Earthquake.Hypocenter hypocenter = earthquake.getHypocenter();;
        EarthquakeAPI.Point[] points = data.getPoints();
        EarthquakeScaleType scaleType = EarthquakeScaleType.convertP2PAPIType(earthquake.getRawScale());

        holder.nameText.setText(hypocenter.getName());
        holder.dateText.setText(earthquake.getRawTime());
        holder.scaleText.setText("震度\n" + scaleType.getText());
        holder.scaleText.setBackgroundColor(scaleType.getColorRaw());
        holder.magnitudeText.setText(String.valueOf(hypocenter.getMagnitude()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView dateText;
        private TextView magnitudeText;
        private TextView scaleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameText = itemView.findViewById(R.id.earthquakeName_Text);
            this.dateText = itemView.findViewById(R.id.earthquakeDate_Text);
            this.magnitudeText = itemView.findViewById(R.id.magnitude_text);
            this.scaleText = itemView.findViewById(R.id.eartqhauke_scaleText);
        }
    }
}
