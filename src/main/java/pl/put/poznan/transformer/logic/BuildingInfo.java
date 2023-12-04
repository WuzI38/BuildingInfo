package pl.put.poznan.transformer.logic;

import pl.put.poznan.transformer.calculation.Adder;

public class BuildingInfo {

    public float getParam(Location location, String name, String param) {

        if(!Validate.validateStructure(location,3)) return -1;

        return Adder.calculate(location, name, param);
    }
}
