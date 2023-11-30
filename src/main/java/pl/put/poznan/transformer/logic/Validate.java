package pl.put.poznan.transformer.logic;

import org.json.JSONArray;
import org.json.JSONObject;

public class Validate {
    public static boolean validateJSON(String text, Integer depth) {//checks if format of json is correct
        //depth - depth of structure
        if(depth < 2) return false;//has to be space
        JSONObject mainSpaceObj = new JSONObject(text);//main object in json - it should be building in our structure

        //check attributes
        if(!mainSpaceObj.has("id"))//id is required
            return false;

        if(!mainSpaceObj.has("locations"))
            return false;

        if(!mainSpaceObj.has("name"))
            return false;

        else {
            JSONArray ja = mainSpaceObj.getJSONArray("locations");
            return validateLevel(ja, depth - 1);
        }
    }

    public static boolean validateLevel(JSONArray ja, Integer depth) {
        if(ja == null) return false;
        for (int i = 0; i<ja.length(); i++) {
            JSONObject obj = ja.getJSONObject(i);
            if(!obj.has("id"))//id is required
                return false;
            if(depth > 1){//space
                if(obj.has("locations")){//only Spaces are on higher levels
                    JSONArray newJA = obj.getJSONArray("locations");
                    if(!validateLevel(newJA,depth-1))
                        return false;
                } else return false;
            } else {//room
                if(!obj.has("area") || !obj.has("cube") || //has all needed attributes
                        !obj.has("heating") || !obj.has("light") ||
                        obj.has("locations"))//does not have more locations
                    return false;//only Rooms are on higher levels
            }
        }
        return true;
    }
}
