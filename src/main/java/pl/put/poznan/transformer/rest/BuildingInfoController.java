package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Space;
import pl.put.poznan.transformer.logic.BuildingInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping
public class BuildingInfoController {
    private static final Logger logger = LoggerFactory.getLogger(BuildingInfoController.class);

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", params = {"name","param"})
    public Map<String, Object> post(@RequestBody Space space, @RequestParam(value = "name") String name,
                                    @RequestParam(value = "param") String param) {

        // log the parameters
        logger.debug(name);
        logger.debug(param);

        Map<String, Object> map = new LinkedHashMap<>();

        BuildingInfo transformer = new BuildingInfo();
        float result = transformer.getParam(space, name, param);

        //decide what response to give
        if ((int) result == -1) {
            map.put("error", "Wrong JSON structure");
        }
        else if ((int) result == -2) {
            map.put("error", "The chosen parameter cannot be calculated");
        }
        else {
            map.put("result", result);
        }

        return map;
    }
}


