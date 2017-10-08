package com.schallerl.katharina.viennatransport;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Katharina on 01.10.2017.
 */

public class StationsParser {

    public List<Station> readStations(JsonReader reader) throws IOException {

        Map<String, Station> allStationsMap = new HashMap<>();

        reader.beginObject();

        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "features":
                    reader.beginArray();

                    while (reader.hasNext()){
                        //allStations.add(readStation(reader));

                        Station tempStation = readStation(reader);
                        if(allStationsMap.containsKey(tempStation.getName())){
                            Station mapStation = allStationsMap.get(tempStation.getName());
                            mapStation.getLines().addAll(tempStation.getLines());
                            //System.out.println("LINIEN HINZUGEFÜGT");
                        } else {
                            allStationsMap.put(tempStation.getName(), tempStation);
                            //System.out.println("KEINE  LINIEN HINZUGEFÜGT");
                        }
                    }

                    reader.endArray();
                    break;

                default:
                    reader.skipValue();
            }
        }

        reader.endObject();
        List<Station> allStations = new ArrayList<>();

        allStations.addAll(allStationsMap.values());
        List<Station> uSStations = new ArrayList<>();

        for(Station station : allStations){
            for(String line : station.getLines()) {
                if( line.contains("U")|| line.contains("S")){
                    uSStations.add(station);
                    break;
                    //System.out.println("Station " + station.getName() + "hinzugefügt.");
                } else {
                    //System.out.println("Station " + station.getName() + " nicht hinzugefügt.");
                }
            }
        }

        //uSStations.sort(Comparator.comparing(Station::getName));
        /*public static Comparator<Station> StationNameComparator = new Comparator<Station>() {
            public int compare(Station station1, Station station2) {
                String stationName1 = station1.getName().toUpperCase();
                String stationName2 = station2.getName().toUpperCase();
                stationName1.compareTo(stationName2); //descending order
                return stationName2.compareTo(stationName1);
            }
        }*/

        return uSStations;
    }

    public Station readStation(JsonReader reader) throws IOException {
        Long id = -1L;
        String name = null;
        Long divaId = null;
        Set<String> lines = new HashSet<>();
        Double lat = null, lng = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String key = reader.nextName();

            switch (key) {
                case "geometry":
                    reader.beginObject();

                    while (reader.hasNext()) {
                        String gKey = reader.nextName();
                        switch (gKey) {
                            case "coordinates":
                                reader.beginArray();
                                lat = reader.nextDouble();
                                lng = reader.nextDouble();
                                reader.endArray();
                                break;

                            default:
                                reader.skipValue();
                        }
                    }

                    reader.endObject();
                    break;
                case "properties":
                    reader.beginObject();

                    while (reader.hasNext()) {
                        String pKey = reader.nextName();

                        switch (pKey) {
                            case "OBJECTID":
                                id = reader.nextLong();
                                break;

                            case "HTXT":
                                name = reader.nextString();
                                break;

                            case "HLINIEN":
                                lines.addAll(Arrays.asList(reader.nextString().split(", ")));
                                break;

                            case "DIVA_ID":
                                JsonToken diva = reader.peek();
                                if (diva == JsonToken.NUMBER) {
                                    divaId = reader.nextLong();
                                } else {
                                    reader.skipValue();
                                }
                                break;

                            default:
                                reader.skipValue();
                        }

                    }

                    reader.endObject();
                    break;
                default:
                    reader.skipValue();
            }
        }

        reader.endObject();

        return new Station(id, name, divaId, lat, lng, lines);
    }
}
