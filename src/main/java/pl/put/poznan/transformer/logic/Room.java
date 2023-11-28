package pl.put.poznan.transformer.logic;

public class Room extends Location {
    Float area;
    Float cube;
    Float heating;
    Float light;

    public Room(Float area, Float cube, Float heating, Float light, Integer id, String name) {
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
        this.id = id;
        this.name = name;
    }
    public void showRoom(){
        System.out.println();
    }
}
