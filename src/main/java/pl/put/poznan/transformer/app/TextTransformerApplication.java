package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.TextTransformer;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {

    public static void main(String[] args) {
        TextTransformer tt = new TextTransformer();
        System.out.println(tt.getParam("src/main/resources/testGood.json", "Podlokacja B2", "area"));

        // SpringApplication.run(TextTransformerApplication.class, args);
    }
}