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
    public float getParam(String path, String name, String param) {
        String text = JSONHandle.readFile(path);

        Location location = generateLocation(text);

        return Adder.calculate(location, name, param);
    }

    public String getText(String path) {
        return JSONHandle.readFile(path);
    }

    public Location generateLocation(String text) {
        try {
            text = JSONHandle.prettyPrintJson(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Space space;
        if(Validate.validateJSON(text, 3)){
            space = JSONHandle.parseJSON(text);
        }
        else {
            throw new RuntimeException("Invalid JSON object");
        }
        return space;
    }
}
