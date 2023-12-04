package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public class Room extends Location {
    public Float getArea() {//Leaf in Composite pattern
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Float getCube() {
        return cube;
    }

    public void setCube(Float cube) {
        this.cube = cube;
    }

    public Float getHeating() {
        return this.heating / this.cube;
    }

    public void setHeating(Float heating) {
        this.heating = heating;
    }

    public Float getLight() {
        return this.light / this.area;
    }

    public void setLight(Float light) {
        this.light = light;
    }

    private Float area;
    private Float cube;
    private Float heating;
    private Float light;

    public Room(Float area, Float cube, Float heating, Float light, Integer id, String name) {
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
        this.id = id;
        this.name = name;
    }
}
