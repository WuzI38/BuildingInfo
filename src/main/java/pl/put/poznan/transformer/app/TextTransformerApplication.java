package pl.put.poznan.transformer.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.Location;
import pl.put.poznan.transformer.logic.JSONHandle;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        TextTransformer tt = new TextTransformer();
        Location loc1 = tt.generateLocation(tt.getText("src/main/resources/testGood.json"));
        System.out.println(loc1.getName());



        try {
            System.out.println(JSONHandle.prettyPrintJson(tt.getText("src/main/resources/testGood.json")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        //         SpringApplication.run(TextTransformerApplication.class, args);
    }
}

/*

"name": "Podlokacja A1",
          "area": 20.0,
          "cube": 15.2,
          "heating": 10.0,
          "light":
 */