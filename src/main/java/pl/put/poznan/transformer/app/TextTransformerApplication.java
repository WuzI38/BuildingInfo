package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.TextTransformer;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        TextTransformer tt = new TextTransformer();
        tt.getParam("src/main/resources/testGood.json", "Budynek", "light");

        // SpringApplication.run(TextTransformerApplication.class, args);
    }
}

/*

"name": "Podlokacja A1",
          "area": 20.0,
          "cube": 15.2,
          "heating": 10.0,
          "light":
 */