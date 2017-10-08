package com.schallerl.katharina.viennatransport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Katharina on 23.09.2017.
 */

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {
    //member-Variable für die Stationen:
    private List<Station> mStations;

    //Stationsliste im Konstruktor mitgeben:
    public StationsAdapter(List<Station> stations) {
        mStations = stations;
    }

    //um das item_layout zu inflaten und den viewholder zu erschaffen
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View stationView = inflater.inflate(R.layout.item_station, parent, false);
        ViewHolder viewHolder = new ViewHolder(stationView);

        return viewHolder;
    }

    //via viewholder Daten ins row-item bringen:
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Datenmodell nach Position ermitteln:
        Station station = mStations.get(position);

        //item-views basierend auf eigenen views und Datenmodell setzen:
        TextView textView = holder.mNameTextView;
        textView.setText(station.getName());
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    //ViewHolder ermöglicht Zugriff auf die Views

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Referenz auf jede View eines Daten-Items:
        private TextView mNameTextView;
        private TextView mCoordinatesTextView;
        private TextView mLinesTextView;

        //Konstruktor, der gesamte item-Zeile übernimmt.
        //macht view-lookup, um jede subview zu finden.
        public ViewHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.station_name);
            mCoordinatesTextView = (TextView) itemView.findViewById(R.id.station_coordinates);
            mLinesTextView = (TextView) itemView.findViewById(R.id.station_lines);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), DetailActivity.class);

            int position = getAdapterPosition();

            intent.putExtra("Stationsname", mStations.get(position).getName());
            intent.putExtra("StationsLongitude", mStations.get(position).getLng());
            intent.putExtra("StationsLatitude", mStations.get(position).getLat());

            ArrayList<String> lines = new ArrayList<>();
            lines.addAll(mStations.get(position).getLines());

            intent.putStringArrayListExtra("Stationslinien", lines);

            v.getContext().startActivity(intent);
        }
    }
}