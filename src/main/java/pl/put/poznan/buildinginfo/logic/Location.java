package pl.put.poznan.buildinginfo.logic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a generic location as part of a composite structure.
 * This abstract class serves as a component in the Composite pattern.
 * It is annotated to support custom JSON deserialization.
 * @version 1.0
 */
@JsonDeserialize(using = CustomDeserializer.class)
public abstract class Location { //Component in Composite pattern
    protected Integer id = null;

    protected String name = null;

    /**
     * Retrieves the unique identifier of the location.
     *
     * @return The ID of the location.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the location.
     *
     * @param id The new ID value to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the location.
     *
     * @param name The new name value to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
