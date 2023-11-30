package pl.put.poznan.transformer.calculation;

import pl.put.poznan.transformer.logic.Location;
import pl.put.poznan.transformer.logic.Room;
import pl.put.poznan.transformer.logic.Space;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class Adder {
    // Returns sum of area, cube, light/area or heating/cube
    public static float calculate(Location location, String param) {
        float sum = 0;
        if (location instanceof Room) {
            sum = getSingleParam((Room)location, param);
        }
        else {
            Space space = (Space) location;
            ArrayList<Location> loc = space.getLocations();
            sum += calculateLocations(loc, param);
        }
        return sum;
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
