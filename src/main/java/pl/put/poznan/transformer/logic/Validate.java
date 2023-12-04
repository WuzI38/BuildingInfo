package pl.put.poznan.transformer.logic;

import java.util.ArrayList;

/**
 * The Validate class provides static methods to validate the structure of a given Location object.
 * It checks the integrity and correctness of the hierarchical structure based on the specified depth.
 * @version 1.0
 */
public class Validate {
    /**
     * Validates the structure of the given location object to ensure it conforms to the expected format.
     * The method checks if the location is a Space and not a Room, and verifies that the depth is correct.
     *
     * @param location The location object to validate.
     * @param depth The expected depth of the structure.
     * @return true if the structure is valid, false otherwise.
     */
    public static boolean validateStructure(Location location, Integer depth) {
        if(depth < 2 || location.getClass() == Room.class) return false; //has to be space

        Space space = (Space) location;
        if(space.getLocations() == null || space.getId() == null)
            return false;
        ArrayList<Location> locations = space.getLocations();

        return validateLevel(locations, depth-1);
    }

    /**
     * Recursively validates each level of the location structure.
     * It ensures that each location has an ID and that the structure is consistent with the expected depth.
     * Spaces are only allowed at higher levels, while Rooms are only at the lowest level.
     *
     * @param locations The list of location objects at the current level to validate.
     * @param depth The depth remaining to validate.
     * @return true if the current level is valid, false otherwise.
     */
    public static boolean validateLevel(ArrayList<Location> locations, Integer depth) {
        for (Location location : locations) {
            if (location.getId() == null) return false;
            if (depth > 1) {//Space
                if (location.getClass() == Space.class) {//only Spaces are on higher levels
                    Space space = (Space) location;
                    ArrayList<Location> nextLocations = space.getLocations();
                    if (nextLocations != null)
                        if (!validateLevel(nextLocations, depth - 1))
                            return false;
                } else return false;
            } else {//Room
                if (location.getClass() == Room.class) {
                    Room room = (Room) location;
                    if (room.getArea() == null || room.getCube() == null || //has all needed attributes
                            room.getHeating() == null || room.getLight() == null)
                        return false;//only Rooms are on lowest level
                } else return false;
            }
        }
        return true;
    }
}
