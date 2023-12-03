package pl.put.poznan.transformer.logic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Validate {
    public static boolean validateStructure(Location location, Integer depth) {//checks if format of json is correct
        //depth - depth of structure
        if(depth < 2 || location.getClass() == Room.class) return false;//has to be space

        Space space = (Space) location;
        if(space.getLocations() == null || space.getId() == null)
            return false;
        ArrayList<Location> locations = space.getLocations();

        return validateLevel(locations, depth-1);
    }

    public static boolean validateLevel(ArrayList<Location> locations, Integer depth) {

        for (Location location : locations) {
            if (location.getId() == null) return false;
            if (depth > 1) {//space
                if (location.getClass() == Space.class) {//only Spaces are on higher levels
                    Space space = (Space) location;
                    ArrayList<Location> nextLocations = space.getLocations();
                    if (nextLocations != null)
                        if (!validateLevel(nextLocations, depth - 1))
                            return false;
                } else return false;
            } else {//room
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
