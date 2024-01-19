package pl.put.poznan.buildinginfo.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.buildinginfo.logic.Location;
import pl.put.poznan.buildinginfo.logic.Room;
import pl.put.poznan.buildinginfo.logic.Space;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Iterates through all the rooms to check which exceed value of param
 * @version 1.0
 */
public final class Inspector {

    private static final Logger logger = LoggerFactory.getLogger(Inspector.class);

    /**
     * Searches for and returns rooms in location which exceed limit value of param and returns
     *
     * @param location The top-level location to search within.
     * @param limit Limit of param above which rooms are added to list.
     * @param param The parameter to calculate the sum for.
     * @return The ArrayList of Rooms in location with value of param above limit. Returns null if method for getting value of param per unit does not exist.
     */
    public static ArrayList<Room> searchRoomsAboveLimit(Location location, Float limit, String param) {
        logger.debug("Processing to search for rooms with {} above {}",param,limit);
        Space space = (Space) location;
        ArrayList<Room> roomsAboveLimit = new ArrayList<>();

        ArrayList<Location> locations = space.getLocations();

        String methodName = "get" + Character.toUpperCase(param.charAt(0)) + param.substring(1) + "PerUnit";

        try {
            Method method = Room.class.getMethod(methodName);
            // For each floor in the building
            for (Location subLoc: locations) {
                roomsAboveLimit.addAll(getRooms((Space)subLoc,limit,method));
            }
            return roomsAboveLimit;
        }
        catch (NoSuchMethodException e) {
            logger.info("Method not found: {}",e.getMessage());
            return null;
        }
    }

    /**
     * Returns rooms on floor which exceed limit value of param and returns
     *
     * @param space Floor in which we search for rooms.
     * @param limit Limit of param above which rooms are added to list.
     * @param method The method of Room class that corresponds to given parameter.
     * @return The ArrayList of Rooms in location with value of param above limit.
     */
    private static ArrayList<Room> getRooms(Space space, Float limit, Method method){
        ArrayList<Room> roomsAboveLimit = new ArrayList<>();
        ArrayList<Location> locations = space.getLocations();

        // For each room on the floor
        for (Location room: locations) {
            try {
                if( (float) method.invoke((Room)room) > limit ){
                    roomsAboveLimit.add((Room)room);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.info("Error invoking method: {}",e.getMessage());
            }
        }
        return roomsAboveLimit;
    }

}
