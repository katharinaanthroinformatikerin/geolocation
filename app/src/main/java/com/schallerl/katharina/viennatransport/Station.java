package com.schallerl.katharina.viennatransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Katharina on 23.09.2017.
 */

//Model-Class erstellt

public class Station {

    public final Long objectId;
    public final String name;
    public final Long divaId;
    public final double lat;
    public final double lng;
    public final Set<String> lines;

    public Station(long objectId, String name, Long divaId, double lat, double lng, Set<String> lines) {
        this.objectId = objectId;
        this.name = name;
        this.divaId = divaId;
        this.lat = lat;
        this.lng = lng;
        this.lines = lines;
    }

    public long getObjectId(){
        return objectId;
    }

    public String getName(){
        return name;
    }

    public Long getDivaId(){
        return divaId;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    public Set<String> getLines(){
        return lines;
    }

    @Override
    public String toString() {
        return "Station{" +
                "objectId=" + objectId +
                ", name='" + name + '\'' +
                ", divaId=" + divaId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", lines=" + lines +
                '}';
    }
}