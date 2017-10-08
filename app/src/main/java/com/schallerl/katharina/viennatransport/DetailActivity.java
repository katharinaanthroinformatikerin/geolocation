package com.schallerl.katharina.viennatransport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String stationName = getIntent().getStringExtra("Stationsname");
        Double longitude =  getIntent().getDoubleExtra("StationsLongitude", 0);
        Double latitude = getIntent().getDoubleExtra("StationsLatitude", 0);
        Double.toString(longitude);
        Double.toString(latitude);

        ArrayList<String> lines = getIntent().getStringArrayListExtra("Stationslinien");

        TextView tvCoordinates = (TextView) findViewById(R.id.station_coordinates);
        TextView tvStationName = (TextView) findViewById(R.id.station_title);
        TextView tvLines = (TextView) findViewById(R.id.station_lines2);

        tvCoordinates.setText(longitude + "/" + latitude);
        tvStationName.setText(stationName);
        String linesString = TextUtils.join(", ", lines);
        tvLines.setText(linesString);
    }
}
