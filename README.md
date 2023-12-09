# BuildingInfo
![example workflow](https://github.com/WuzI38/BuildingInfo/actions/workflows/ci.yml/badge.svg)

## Version
1.3

## Sprint and product backlog
https://docs.google.com/spreadsheets/d/12U_DdNzxybBNd62lcBmuTMfIHWe-wBfjy8yGXYLHakU/edit?usp=sharing

## Building the project
This project is built using Maven and requires both Java and Maven for setup. Here are some brief instructions on how to build the project. Please ensure all commands are executed in the project’s directory:

- Run `mvn -v` to verify your Maven installation.
- Run `maven clean install` to clean any remnants of the last build and install all required packages.
- Run `maven package` to create a .jar file in the `target/` directory.

## Running the project
The application can calculate the sum of a chosen parameter (specified by the `param` argument) for a room or building, as described in a JSON file. Here’s a quick guide on how to launch the application:

- Launch the application using `java -jar target/BuildingInfo-1.3.jar`.
- To get the sum of a chosen parameter, use the following command:
  
```curl -H "Content-Type: application/json" -H "Accept: application/json"  -X POST -d @src/main/resources/testGood.json "http://localhost:8080/2137?name=Lokacja+B&param=area"```

The  `@src/main/resources/testGood.json` file is a path to a JSON file that describes an example location (room or building). You can replace it with a similar file that follows the same structure.
The `name` is the name of your chosen location (which might be a part of the whole location given by the JSON file), and there are four possibilities for `param`:
- `area`: The area of the entire location.
- `cube`: The volume of the entire location.
- `heating`: Energy usage per unit volume.
- `light`: Light usage per unit area.

## Documentation 
Generate the Javadoc by running the command `mvn javadoc:javadoc`. The generated documentation can be viewed by opening `target/site/apidocs/index.html` in your browser.
