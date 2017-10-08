package com.schallerl.katharina.viennatransport;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import javax.net.ssl.HttpsURLConnection;

public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Station>> {

    RecyclerView rvStations;

    private static final int LOADER_ID = 1;
    private static final String LOG_TAG = DetailActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        //recyclerview im layout heraussuchen:
        rvStations = (RecyclerView) findViewById(R.id.rvStations);

        //Stationen initialisieren:
        //stations = Station.createStationsList(30);

        //Layoutmanager setzen, um die Items zu positionieren:
        rvStations.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected");
        Context context = getApplicationContext();

        int itemId = item.getItemId();
        if (itemId == R.id.action_start) {
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<List<Station>> loader = loaderManager.getLoader(LOADER_ID);
            try {
                if (loader == null) {
                    loaderManager.initLoader(LOADER_ID, null, this);
                    Log.d(LOG_TAG, "Loader initialized.");

                } else {
                    loaderManager.restartLoader(LOADER_ID, null, this);
                    Log.d(LOG_TAG, "Loader restarted.");
                }
            } catch (Exception e) {
                CharSequence text1 = "Stationenliste konnte nicht neu geladen werden.";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text1, duration).show();
                e.printStackTrace();
            }

            CharSequence text2 = "Stationenliste wird neu geladen.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text2, duration).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Station>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Station>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public List<Station> loadInBackground() {
                Log.d(LOG_TAG, "Starting work...");
                URL url = null;
                HttpsURLConnection urlConnection = null;
                List<Station> stations = null;

                try {
                    url = new URL("https://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:OEFFHALTESTOGD&srsName=EPSG:4326&outputFormat=json");
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");

                    JsonReader reader = new JsonReader(isr);

                    StationsParser sp = new StationsParser();
                    stations = sp.readStations(reader);
                    reader.close();
                    urlConnection.disconnect();
                    return stations;

                } catch (Exception e) {
                    e.printStackTrace();
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }

                Log.d(LOG_TAG, "Work finished");
                return stations;
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<List<Station>> loader, List<Station> data) {
        Log.d(LOG_TAG, "onLoadFinished before data");
        //Adapter mit Daten erstellen.
        StationsAdapter adapter = new StationsAdapter(data);

        Log.d(LOG_TAG, "onLoadFinished after data");

        //Adapter an den RecyclerView hängen:
        rvStations.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Station>> loader) {
        Log.d(LOG_TAG, "onLoaderReset");
    }
}
