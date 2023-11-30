package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONHandle {
    public static String readFile(String filename) {//for reading json file
        File myObj = new File(filename);
        Scanner myReader = null;
        StringBuilder json = new StringBuilder();
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (myReader.hasNextLine()) {
            json.append(myReader.nextLine());
        }

        return json.toString();
    }

    public static Space parseJSON(String json){//we parse json to objects (check test*.json in resources for sample json files)
        JSONObject mainSpaceObj = new JSONObject(json);//main object in json - it should be building in our structure
        JSONArray ja = mainSpaceObj.getJSONArray("locations");

        //assign attributes
        Integer id = mainSpaceObj.getInt("id");
        String name = mainSpaceObj.getString("name");
        Space mainSpace = new Space(id, name);
        mainSpace.setLocations(getLocations(ja));

        return mainSpace;
    }

    private static ArrayList<Location> getLocations(JSONArray ja){//recursive function
        ArrayList<Location> list = new ArrayList<Location>();

        for (int i = 0; i<ja.length(); i++){
            JSONObject obj = ja.getJSONObject(i);
            if(obj.has("locations")){//obj = space
                Integer id = obj.getInt("id");//assign attributes
                String name = obj.getString("name");
                ArrayList<Location> locations = new ArrayList<Location>();

                JSONArray nextJA = obj.getJSONArray("locations");
                locations = getLocations(nextJA);
                Space space = new Space(id, name);
                space.setLocations(locations);
                list.add(space);
            } else {//obj = room
                Float area = obj.getFloat("area");//assign attributes
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

    public static String prettyPrintJson(String oneLineJSON) throws JsonProcessingException {//uses jackson (3 dependencies - 1st should load others but in this project it does not work (: )
        //return prettier string with json (normally it is all in one line)
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(oneLineJSON, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}
