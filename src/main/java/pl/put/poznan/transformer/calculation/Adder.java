package pl.put.poznan.transformer.calculation;

import pl.put.poznan.transformer.logic.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public final class Adder {
    // Returns sum of area, cube, light/area or heating/cube
    public static float calculate(Location location, String name, String param) {
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

    // Invokes method using name param
    private static float getSingleParam(Room obj, String name) {
        float result = 0;
        try {

            String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);

            Method method = obj.getClass().getMethod(methodName);

            result = (float) method.invoke(obj);
        }
        catch (NoSuchMethodException e) {
            System.out.println("Method not found: " + e.getMessage());
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Error invoking method: " + e.getMessage());
        }

        return result;
    }

    // Recursive fun to calculate sum of desired param
    private static float calculateLocations(ArrayList<Location> locations, String param) {
        float sum = 0;
        for (Location location : locations) {
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
