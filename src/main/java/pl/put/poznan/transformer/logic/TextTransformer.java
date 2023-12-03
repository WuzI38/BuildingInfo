package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.put.poznan.transformer.calculation.Adder;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {
    /* private final String[] transforms;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
    }*/

    // I think this might be redundant
    public float getParam(Location location, String name, String param) {
        //String text = JSONHandle.readFile(path);

        //Location location = generateLocation(text);

        if(!Validate.validateStructure(location,3)) return -1;

        return Adder.calculate(location, name, param);
    }


}
