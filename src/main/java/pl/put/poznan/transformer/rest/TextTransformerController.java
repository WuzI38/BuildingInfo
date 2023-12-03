package pl.put.poznan.transformer.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.Room;
import pl.put.poznan.transformer.logic.Space;
import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;

import static java.lang.String.valueOf;


@RestController
@RequestMapping
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);



    @RequestMapping(method = RequestMethod.POST, produces = "application/json", params = {"name","param"}, value = "/{id}")
    public String post(@RequestBody Space space, @RequestParam(value = "name") String name,
                       @RequestParam(value = "param") String param, @PathVariable String id) {

        // log the parameters
        logger.debug(name);
        logger.debug(param);

        TextTransformer transformer = new TextTransformer();
        return valueOf(transformer.getParam(space, name, param));
    }



}


