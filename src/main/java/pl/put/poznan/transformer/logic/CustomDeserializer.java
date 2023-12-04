package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Custom deserializer for Location objects.
 * This class extends StdDeserializer to provide custom deserialization logic that
 * distinguishes between Space and Room objects from input JSON.
 * @version 1.0
 */
public class CustomDeserializer extends StdDeserializer<Location> {//helps spring properly read json (distinguishes Space and Room objects from input JSON)

    /**
     * Constructs a new CustomDeserializer instance for Location class.
     */
    protected CustomDeserializer() {
        super(Location.class);
    }

    /**
     * Deserializes JSON content into a Location object.
     * It determines whether to create a Space or Room object based on the presence of the "locations" property.
     *
     * @param p The JsonParser reading the JSON content.
     * @param ctxt The DeserializationContext that can be used to obtain a Java object given a JSON node.
     * @return A deserialized Location object, either a Space or Room.
     * @throws IOException If an I/O error occurs during parsing.
     * @throws JsonProcessingException If the JSON content cannot be processed.
     */
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