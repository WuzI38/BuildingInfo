package pl.put.poznan.transformer.calculation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.put.poznan.transformer.logic.Room;
import pl.put.poznan.transformer.logic.Space;

import static org.junit.jupiter.api.Assertions.*;

class AdderTest {
    Room properRoom1 = null;
    Room properRoom2 = null;
    Room properRoom3 = null;
    Room properRoom4 = null;

    Space properLevel1 = null;
    Space properLevel2 = null;

    Space properBuilding = null;

    @BeforeEach
    void setUp() {
        properRoom1 = new Room(10.0f,20.0f,30.0f,40.0f,101, "Room 1");
        properRoom2 = new Room(10.0f,20.0f,30.0f,40.0f,102, "Room 2");
        properRoom3 = new Room(10.0f,20.0f,30.0f,40.0f,103, "Room 3");
        properRoom4 = new Room(10.0f,20.0f,30.0f,40.0f,104, "Room 4");

        properLevel1 = new Space(11,"Level 1");
        properLevel2 = new Space(12,"Level 2");

        properBuilding = new Space(1,"Building 1");
    }

    @AfterEach
    void tearDown() {
        properRoom1 = null;
        properRoom2 = null;
        properRoom3 = null;
        properRoom4 = null;

        properLevel1 = null;
        properLevel2 = null;

        properBuilding = null;
    }

    @Test
    public void TestProperBuildingAllParams(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertEquals(40.0f,Adder.calculate(properBuilding,"Building 1","area"));
        assertEquals(80.0f,Adder.calculate(properBuilding,"Building 1","cube"));
        assertEquals(6.0f,Adder.calculate(properBuilding,"Building 1","heating"));
        assertEquals(16.0f,Adder.calculate(properBuilding,"Building 1","light"));
    }

    @Test
    public void TestProperLevelAllParams(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);

        assertEquals(20.0f,Adder.calculate(properLevel1,"Level 1","area"));
        assertEquals(40.0f,Adder.calculate(properLevel1,"Level 1","cube"));
        assertEquals(3.0f,Adder.calculate(properLevel1,"Level 1","heating"));
        assertEquals(8.0f,Adder.calculate(properLevel1,"Level 1","light"));
    }

    @Test
    public void TestUnknownParamBuilding(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertTrue(Adder.calculate(properBuilding,"Building 1","humidity")<0.0f);
    }

    @Test
    public void TestUnknownNameBuilding(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertEquals(-1,Adder.calculate(properBuilding,"Biedronka","area"));
    }

    @Test
    public void TestUnknownNameLevel(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        assertEquals(-1,Adder.calculate(properLevel1,"Lidl","cube"));
    }


}