package pl.put.poznan.buildinginfo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.buildinginfo.calculation.Adder;
import pl.put.poznan.buildinginfo.calculation.Inspector;

import java.util.ArrayList;

/**
 * This class contains methods for calculating various building parameters such as area, volume, etc.
 * It utilizes the Adder class for calculations and the Validate class for structure validation.
 *
 * @version 1.3
 */
public class BuildingInfo {
    private static final Logger logger = LoggerFactory.getLogger(Validate.class);

    /**
     * Returns a specific parameter of a given location.
     *
     * @param location The location to calculate the parameter for.
     * @param name The name of the location.
     * @param param The parameter to calculate.
     * @return The calculated parameter. Returns -1 if the structure validation fails. Returns -2 if the parameter cannot be calculated.
     */
    public float getParam(Location location, String name, String param) {
        logger.info("Calculating {} of {} ",param,name);

        if(!Validate.validateStructure(location,3)) return -1; // Zmienic na !
        logger.info("Validation of {} succeed",location.getName());

        try {
            float calculatedParam = Adder.calculate(location, name, param);
            logger.info("{} has {} equal {}",name,param,calculatedParam);
            return calculatedParam;
        } catch (Exception e) {
            logger.error("Error calculating param", e);
            return -2;
        }
    }
    /**
     * Returns a list of rooms that exceed given limit.
     *
     * @param location The location to calculate the parameter for.
     * @param limit Limit of param above which rooms are added to list.
     * @param param The parameter to calculate.
     * @return The ArrayList of Rooms with value of param above limit. Returns null if the structure validation fails.
     */
    public ArrayList<Room> roomsAboveParamLimit(Location location, Float limit, String param) {
        logger.info("Searching rooms with {} above {} ",param,limit);

        if(!Validate.validateStructure(location,3)) return null;
        logger.info("Validation of {} succeed",location.getName());

        try {
            ArrayList<Room> resultRooms = Inspector.searchRoomsAboveLimit(location, limit, param);
            if (resultRooms!=null)
                logger.info("Found {} rooms above {} {}",resultRooms.size(),limit,param);
            return resultRooms;
        } catch (Exception e) {
            logger.error("Error calculating param", e);
            return null;
        }
    }
}
