package com.schallerl.katharina.viennatransport;

import android.util.JsonReader;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Katharina on 01.10.2017.
 */
public class StationsParserTest {


    @Test
    public void readStationsArray() throws Exception {
        JsonReader reader = new JsonReader(new FileReader(new File("OEFFHALTESTOGD.json")));

        List<Station> stations = new StationsParser().readStations(reader);

        assertNotNull(stations);
        assertTrue(stations.size() > 0);

        //assertEquals("id passt nicht", 696, stations.get(0).getDivaId());
        assertEquals("station name passt nicht", "Kohlgasse", stations.get(0).getName());
        assertEquals("latitude passt nicht", 16.35137513152039, stations.get(0).getLat(), 0.00001);
        assertEquals("longitude passt nicht", 48.187797944464066, stations.get(0).getLng(), 0.00001);
        assertTrue("lines passen nicht",  stations.get(0).getLines().contains("12A"));
    }

    @Test
    public void readStation() throws Exception {
        String json = "{\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"id\": \"OEFFHALTESTOGD.793\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "          16.35137513152039,\n" +
                "          48.187797944464066\n" +
                "        ]\n" +
                "      },\n" +
                "      \"geometry_name\": \"SHAPE\",\n" +
                "      \"properties\": {\n" +
                "        \"OBJECTID\": 793,\n" +
                "        \"HTXT\": \"Kohlgasse\",\n" +
                "        \"HTXTK\": \"Kohlgasse\",\n" +
                "        \"HLINIEN\": \"12A\",\n" +
                "        \"DIVA_ID\": 696,\n" +
                "        \"SE_ANNO_CAD_DATA\": null\n" +
                "      }\n" +
                "    }";

        JsonReader reader = new JsonReader(new StringReader(json));

        Station station = new StationsParser().readStation(reader);

        assertNotNull(station);

        //assertArrayEquals("id passt nicht", 696, station.getDivaId());
        assertEquals("station name passt nicht", "Kohlgasse", station.getName());
        assertEquals("latitude passt nicht", 16.35137513152039, station.getLat(), 0.00001);
        assertEquals("longitude passt nicht", 48.187797944464066, station.getLng(), 0.00001);
        assertTrue("lines passen nicht", station.getLines().contains("12A"));
    }

}