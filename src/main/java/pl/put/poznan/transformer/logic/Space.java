package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

@JsonDeserialize
public class Space extends Location {//building or floor Composite
    public Space(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    private ArrayList<Location> locations;
}
