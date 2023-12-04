package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.transformer.logic.BuildingInfo;


@SpringBootApplication(scanBasePackages = "pl.put.poznan.transformer.rest")
public class BuildingInfoApplication {

    public static void main(String[] args) {
        BuildingInfo tt = new BuildingInfo();

        SpringApplication.run(BuildingInfoApplication.class, args);
    }
}