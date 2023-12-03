package pl.put.poznan.transformer.logic;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
@JsonDeserialize(using = CustomDeserializer.class)
public abstract class Location {//Component in Composite pattern
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Integer id = null;
    String name = null;


}
