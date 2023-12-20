package pl.put.poznan.transformer.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.logic.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Iterates through all the rooms to calculate the sum of area, cube, heating or lightning
 * @version 1.1
 */
public final class Adder {

    private static final Logger logger = LoggerFactory.getLogger(Adder.class);

    /**
     * Calculates the sum of a specified parameter for a given location.
     * This method searches through buildings, floors, and rooms to find the matching name
     * and then calculates the sum of the specified parameter.
     *
     * @param location The top-level location to search within.
     * @param name The name of the location to find.
     * @param param The parameter to calculate the sum for.
     * @return The sum of the specified parameter for the found location, or -1 if not found.
     */
    public static float calculate(Location location, String name, String param) {
        logger.debug("Processing to calculate {} of {}",param,name);

        Space space = (Space) location;

        ArrayList<Location> locations = space.getLocations();
        // If location is a building
        if (Objects.equals(space.getName(), name)) {
            return calculateLocations(locations, param);
        }

        // For each floor in the building
        for (Location subLoc: locations) {
            // If location is a floor
            if (Objects.equals(subLoc.getName(), name)) {
                ArrayList<Location> l2 = new ArrayList<>(){{add(subLoc);}};
                return calculateLocations(l2, param);
            }

            // If subLoc is a room, do not cast it to Space, like in code below this if
            if(subLoc instanceof Room){
                if (Objects.equals(subLoc.getName(), name)) {
                    return getSingleParam((Room) subLoc, param);
                }
                continue;
            }

            // For each room on the floor
            for (Location room: ((Space)subLoc).getLocations()) {
                // If location is a room
                if (Objects.equals(room.getName(), name)) {
                    return getSingleParam((Room) room, param);
                }
            }
        }
        // If name not found
        return -1;
    }

    /**
     * Retrieves a single parameter value from a Room object using reflection.
     * The method name is dynamically constructed based on the provided parameter name.
     *
     * @param obj The Room object from which to retrieve the parameter.
     * @param name The name of the parameter to retrieve.
     * @return The float value of the parameter, or 0 if an error occurs.
     */
    private static float getSingleParam(Room obj, String name) {
        float result = 0;
        try {

            String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);

            Method method = obj.getClass().getMethod(methodName);

            result = (float) method.invoke(obj);
        }
        catch (NoSuchMethodException e) {
            logger.info("Method not found: {}",e.getMessage());
            return -1.0f;
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("Error invoking method: {}",e.getMessage());
        }

        return result;
    }

    /**
     * Recursively calculates the sum of a specified parameter for a list of locations.
     * This method handles both Room and Space objects, summing the parameter for Rooms
     * and recursively calling itself for Spaces.
     *
     * @param locations The list of locations to calculate the sum for.
     * @param param The parameter to calculate the sum for.
     * @return The sum of the specified parameter for all locations in the list.
     */
    private static float calculateLocations(ArrayList<Location> locations, String param) {
        float sum = 0;
        for (Location location : locations) {
            logger.debug("Processing location {}",location.getName());
            if (location instanceof Room) {
                sum += getSingleParam((Room)location, param);
            }
            else {
                Space space = (Space) location;
                ArrayList<Location> loc = space.getLocations();
                sum += calculateLocations(loc, param);
            }
        }
        return sum;
    }
}
