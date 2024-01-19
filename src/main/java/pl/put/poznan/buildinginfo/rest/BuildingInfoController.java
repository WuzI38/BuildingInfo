package pl.put.poznan.buildinginfo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.buildinginfo.logic.Location;
import pl.put.poznan.buildinginfo.logic.Space;
import pl.put.poznan.buildinginfo.logic.Room;
import pl.put.poznan.buildinginfo.logic.BuildingInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping
public class BuildingInfoController {
    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", params = {"name","param"}, value = "/eval")
    public Map<String, Object> post(@RequestBody Space space, @RequestParam(value = "name") String name,
                                    @RequestParam(value = "param") String param) {

        // log the parameters
        logger.debug(name);
        logger.debug(param);

        Map<String, Object> map = new LinkedHashMap<>();

        BuildingInfo transformer = new BuildingInfo();
        float result = transformer.getParam(space, name, param);

        float resultBuilding = transformer.getParam(space, space.getName(), param);

        //decide what response to give
        if ((int) result == -1) {
            map.put("error", "Wrong JSON structure");
        }
        else if ((int) result == -2) {
            map.put("error", "The chosen parameter cannot be calculated");
        }
        else {
            map.put(param, result);
            switch (param){
                case "area":
                case "cube":
                    map.put("building %", 0.01 * Math.round(10000 * result / resultBuilding));
                    break;
                case "light":
                case "heating":
                    map.put("building value", resultBuilding);
                    break;
            }
        }

        return map;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", params = {"param", "limit"}, value = "/exceed")
    public ArrayList<Map<String, Object>>  post(@RequestParam(value = "param") String param,
                                    @RequestBody Space space,
                                    @RequestParam(value = "limit") String limit) {

        // log the parameters
        logger.debug(String.valueOf(limit));
        logger.debug(param);
        float limitValue;
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            limitValue = Float.parseFloat(limit);
        } catch(NumberFormatException e){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("error", "limit not a number");
            result.add(map);
            return result;
        }

        if(!Objects.equals(param, "light") && !Objects.equals(param, "heating")){
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("error", "Wrong param");
            result.add(map);
            return result;
        }
        BuildingInfo transformer = new BuildingInfo();
        ArrayList<Room> roomsAboveLimit = transformer.roomsAboveParamLimit(space, limitValue, param);

        if (roomsAboveLimit == null) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("error", "Wrong JSON structure");
            result.add(map);
        }
        else {
            int i = 1;
            for (Room room: roomsAboveLimit){
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("name", room.getName());
                switch (param){
                    case "light":
                        map.put("lightPerUnit", room.getLightPerUnit());
                        break;
                    case "heating":
                        map.put("heatingPerUnit", room.getHeatingPerUnit());
                        break;
                }
                result.add(map);
            }
        }

        return result;
    }
}


