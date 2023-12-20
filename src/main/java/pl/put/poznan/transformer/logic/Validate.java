package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;

/**
 * The Validate class provides static methods to validate the structure of a given Location object.
 * It checks the integrity and correctness of the hierarchical structure based on the specified depth.
 * @version 1.0
 */
public class Validate {
    private static final Logger logger = LoggerFactory.getLogger(Validate.class);
    /**
     * Validates the structure of the given location object to ensure it conforms to the expected format.
     * The method checks if the location is a Space and not a Room, and verifies that the depth is correct.
     *
     * @param location The location object to validate.
     * @param depth The expected depth of the structure.
     * @return true if the structure is valid, false otherwise.
     */
    public static boolean validateStructure(Location location, Integer depth) {
        logger.debug("Validating structure {}",location.getName());
        if(!findDuplicateIDsAndNames(location))
            return false;

        if(depth < 2 || location.getClass() == Room.class){
            logger.debug("Structure {} is not Space",location.getName());
            return false; //has to be space
        }

        Space space = (Space) location;
        if(space.getLocations() == null || space.getId() == null){
            logger.debug("Location {} is empty or has no ID",location.getName());
            return false;
        }
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
                            room.getHeatingValue() == null || room.getLightValue() == null)
                        return false;//only Rooms are on lowest level
                    // Attributes are positive, area and cube are greater than zero
                    if(room.getArea()<=0.0f || room.getCube()<=0.0f ||
                            room.getHeatingValue() < 0.0f || room.getLightValue() < 0.0f)
                        return false;
                } else return false;
            }
        }
        logger.debug("Structure validation succeed");
        return true;
    }

    /**
     * Function call recursive function to collect list of names and ids in the given location structure.
     * Then it checks if collected lists contain duplicates. If so, function returns true.
     *
     * @param location The location object to validate
     * @return returns true if structure has duplicated names or IDs
     */
    private static boolean findDuplicateIDsAndNames(Location location){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        findDuplicateIDsAndNamesRec(location,names,ids);

        for(int i = 0; i< names.size(); i++){
            for(int j=i+1;j<names.size(); j++){
                if(names.get(i).equals(names.get(j)))
                    return false;
            }
        }

        for(int i = 0; i< ids.size(); i++){
            for(int j=i+1;j<ids.size(); j++){
                if(ids.get(i).equals(ids.get(j)))
                    return false;
            }
        }

        return true;
    }

    /**
     * Recursively append names and IDs to given array lists.
     * In general, function collects all names and IDs from given structure.
     *
     * @param location The Location object
     * @param names Array list of string names to extend
     * @param ids Array list of Integer IDs to extend
     */
    private static void findDuplicateIDsAndNamesRec(Location location, ArrayList<String> names, ArrayList<Integer> ids){
        if(location.getClass() == Room.class) {
            if(location.getName()!=null)
                names.add(location.getName());
            if(location.getId()!=null)
                ids.add(location.getId());
            return;
        }

        Space space = (Space) location;
        ArrayList<Location> locations = space.getLocations();
        for(Location location2 : locations){
            findDuplicateIDsAndNamesRec(location2,names,ids);
        }
    }

}
