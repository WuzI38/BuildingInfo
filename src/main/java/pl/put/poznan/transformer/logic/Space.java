package pl.put.poznan.transformer.logic;

import java.util.ArrayList;

public class Space extends Location {
    public Space(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    ArrayList<Location> locations;
}
