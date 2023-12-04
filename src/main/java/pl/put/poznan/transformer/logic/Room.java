package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents a room as part of a composite structure.
 * This class extends the Location class and is annotated to support JSON deserialization.
 * It acts as a leaf in the Composite pattern, holding specific attributes related to a room.
 * @version 1.0
 */
@JsonDeserialize
public class Room extends Location {
    private Float area;
    private Float cube;
    private Float heating;
    private Float light;

    /**
     * Constructs a new Room with the specified attributes.
     *
     * @param area The area of the room.
     * @param cube The volume of the room.
     * @param heating The heating value of the room.
     * @param light The light value of the room.
     * @param id The unique identifier for the room.
     * @param name The name of the room.
     */
    public Room(Float area, Float cube, Float heating, Float light, Integer id, String name) {
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieves the area of the room.
     *
     * @return The area of the room.
     */
    public Float getArea() {//Leaf in Composite pattern
        return area;
    }

    /**
     * Sets the area of the room.
     *
     * @param area The new area value to set.
     */
    public void setArea(Float area) {
        this.area = area;
    }

    /**
     * Retrieves the volume of the room.
     *
     * @return The volume of the room.
     */
    public Float getCube() {
        return cube;
    }

    /**
     * Sets the volume of the room.
     *
     * @param cube The new volume value to set.
     */
    public void setCube(Float cube) {
        this.cube = cube;
    }

    /**
     * Retrieves the heating value of the room, normalized by its volume.
     *
     * @return The heating value per unit volume of the room.
     */
    public Float getHeating() {
        return this.heating / this.cube;
    }

    /**
     * Sets the heating value of the room.
     *
     * @param heating The new heating value to set.
     */
    public void setHeating(Float heating) {
        this.heating = heating;
    }

    /**
     * Retrieves the light value of the room, normalized by its area.
     *
     * @return The light value per unit area of the room.
     */
    public Float getLight() {
        return this.light / this.area;
    }

    /**
     * Sets the light value of the room.
     *
     * @param light The new light value to set.
     */
    public void setLight(Float light) {
        this.light = light;
    }
}
