package pl.put.poznan.transformer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.calculation.Adder;

public class BuildingInfo {
    private static final Logger logger = LoggerFactory.getLogger(Validate.class);
    public float getParam(Location location, String name, String param) {
        logger.info("Calculating {} of {} ",param,name);

        if(!Validate.validateStructure(location,3)) return -1;
        logger.info("Validation of {} succeed",location.getName());


        float calculatedParam = Adder.calculate(location, name, param);
        logger.info("{} has {} equal {}",name,param,calculatedParam);
        return calculatedParam;
    }
}
