package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

/**
 * Represents a space, which can be a building or a floor, as part of a composite structure.
 * This class extends the Location class and is annotated to support JSON deserialization.
 * @version 1.0
 */
@JsonDeserialize
public class Space extends Location {//building or floor Composite
    /**
     * Constructs a new Space with the specified ID and name.
     *
     * @param id The unique identifier for the space.
     * @param name The name of the space.
     */
    public Space(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the list of locations within this space.
     *
     * @return An ArrayList of Location objects contained within this space.
     */
    public ArrayList<Location> getLocations() {
        return locations;
    }

    /**
     * The list of locations contained within this space.
     */
    private ArrayList<Location> locations;
}
