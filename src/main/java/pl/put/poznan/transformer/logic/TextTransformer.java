package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
    }

    public String transform(String text){
        // of course, normally it would do something based on the transforms
        text = test("src/main/resources/test.json");
        Space space = parseJSON(text);
        return text.toUpperCase();
    }

    public String test(String filename){
        File myObj = new File(filename);
        Scanner myReader = null;
        String json = "";
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (myReader.hasNextLine()) {
            json += myReader.nextLine();
        }

        return json;
    }

    public Space parseJSON(String json){
        JSONObject spaceObj = new JSONObject(json);
        JSONArray ja = spaceObj.getJSONArray("locations");

        Integer id = spaceObj.getInt("id");
        String name = spaceObj.getString("name");
        Space space = new Space(id, name);
        space.setLocations(getLocations(ja));

        return space;
    }


    public ArrayList<Location> getLocations(JSONArray ja){
        ArrayList<Location> list = new ArrayList<Location>();
        for (int i = 0; i<ja.length(); i++){
            JSONObject obj = ja.getJSONObject(i);
            if(obj.has("locations")){
                Integer id = obj.getInt("id");
                String name = obj.getString("name");
                ArrayList<Location> locations = new ArrayList<Location>();

                JSONArray nextJA = obj.getJSONArray("locations");
                locations = getLocations(nextJA);
                Space space = new Space(id, name);
                space.setLocations(locations);
                list.add(space);
            } else {
                Float area = obj.getFloat("area");
                Float cube = obj.getFloat("cube");
                Float heating = obj.getFloat("heating");
                Float light = obj.getFloat("light");
                Integer id = obj.getInt("id");
                String name = obj.getString("name");
                Room room = new Room(area,cube,heating,light,id,name);
                list.add(room);
            }
        }
        return list;
    }

    /*public static String prettyPrintJsonUsingDefaultPrettyPrinter(String oneLineJSON) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(oneLineJSON, Object.class);
        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        return prettyJson;
    }*/
}
