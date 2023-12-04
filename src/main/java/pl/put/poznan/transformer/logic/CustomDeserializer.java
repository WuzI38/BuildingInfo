package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomDeserializer extends StdDeserializer<Location> {//helps spring properly read json (distinguishes Space and Room objects from input JSON)

    protected CustomDeserializer() {
        super(Location.class);
    }

    @Override
    public Location deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode node = p.readValueAsTree();

        // Select the concrete class based on the existence of a property
        if (node.get("locations") != null) {
            return p.getCodec().treeToValue(node, Space.class);
        }
        return p.getCodec().treeToValue(node, Room.class);
    }
}