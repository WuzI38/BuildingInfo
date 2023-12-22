package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateTest {
    Room properRoom1 = null;
    Room properRoom2 = null;
    Room properRoom3 = null;
    Room properRoom4 = null;

    Space properLevel1 = null;
    Space properLevel2 = null;

    Space properBuilding = null;

    @BeforeEach
    void setUp() {
        properRoom1 = new Room(10.0f,10.0f,10.0f,10.0f,101, "Room 1");
        properRoom2 = new Room(10.0f,10.0f,10.0f,10.0f,102, "Room 2");
        properRoom3 = new Room(10.0f,10.0f,10.0f,10.0f,103, "Room 3");
        properRoom4 = new Room(10.0f,10.0f,10.0f,10.0f,104, "Room 4");

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
    public void TestProperBuilding(){
        Room mockRoom = Mockito.mock(Room.class);
        Space mockLevel = Mockito.mock(Space.class);
        Space mockBuilding = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRoom);
        rooms.add(mockRoom);

        ArrayList<Location> levels = new ArrayList<>();
        levels.add(mockLevel);
        levels.add(mockLevel);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);
        Mockito.when(mockBuilding.getLocations()).thenReturn(levels);

        assertTrue(Validate.validateStructure(mockBuilding, 3));
    }

    @Test
    public void TestDepth4(){
        Room mockRoom = Mockito.mock(Room.class);
        Space mockLevel = Mockito.mock(Space.class);
        Space mockBuilding = Mockito.mock(Space.class);
        Space mockTwoBuildings = Mockito.mock(Space.class);

        ArrayList<Location> locations = new ArrayList<>();
        locations.add(mockRoom);
        locations.add(mockRoom);

        ArrayList<Location> levels = new ArrayList<>();
        locations.add(mockLevel);
        locations.add(mockLevel);

        ArrayList<Location> buildings = new ArrayList<>();
        locations.add(mockBuilding);
        locations.add(mockBuilding);

        Mockito.when(mockLevel.getLocations()).thenReturn(locations);
        Mockito.when(mockBuilding.getLocations()).thenReturn(levels);
        Mockito.when(mockTwoBuildings.getLocations()).thenReturn(buildings);

        assertTrue(Validate.validateStructure(mockBuilding, 4));
    }


    /*@Test
    public void TestDepth2(){
        Space mockLevel = Mockito.mock(Space.class);
        Space mockBuilding = Mockito.mock(Space.class);

        ArrayList<Location> levels = new ArrayList<>();
        locations.add(mockLevel);
        locations.add(mockLevel);

        assertTrue(Validate.validateStructure(properLevel1, 2));
    }*/

    @Test
    public void TestDepth1(){
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);

        Space twoBuildings = new Space(1234,"area");
        twoBuildings.addLocation(properBuilding);
        twoBuildings.addLocation(properBuilding);
        assertFalse(Validate.validateStructure(properLevel1, 1));
    }

    @Test
    public void TestNullIDRoom(){
        Room roomNoID = new Room(10.0f,10.0f,10.0f,10.0f,null,"noid");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(roomNoID);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullIDSpace(){
        Space spaceNoID = new Space(null,"level");
        spaceNoID.addLocation(properRoom3);
        spaceNoID.addLocation(properRoom4);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(spaceNoID);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullNameRoom(){
        Room roomNoName = new Room(10.0f,10.0f,10.0f,10.0f,102,null);
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(roomNoName);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertTrue(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullNameSpace(){
        Space spaceNoName = new Space(11,null);
        spaceNoName.addLocation(properRoom1);
        spaceNoName.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(spaceNoName);
        properBuilding.addLocation(properLevel2);
        assertTrue(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullArea(){
        Room roomNullArea = new Room(null,10.0f,10.0f,10.0f,101,"Room 1");
        properLevel1.addLocation(roomNullArea);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullCube(){
        Room roomNullCube = new Room(10.0f,null,10.0f,10.0f,101,"Room 1");
        properLevel1.addLocation(roomNullCube);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullHeating(){
        Room roomNullHeating = new Room(10.0f,10.0f,null,10.0f,101,"Room 1");
        properLevel1.addLocation(roomNullHeating);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNullLight(){
        Room roomNullLight = new Room(10.0f,10.0f,10.0f,null,101,"Room 1");
        properLevel1.addLocation(roomNullLight);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestZeroArea(){
        Room roomZeroArea = new Room(0.0f,10.0f,10.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomZeroArea);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestZeroCube(){
        Room roomZeroCube = new Room(10.0f,0.0f,10.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomZeroCube);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestZeroHeating(){
        Room roomZeroHeating = new Room(10.0f,10.0f,0.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomZeroHeating);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertTrue(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestZeroLight(){
        Room roomZeroLight = new Room(10.0f,10.0f,10.0f,0.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomZeroLight);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertTrue(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNegativeArea(){
        Room roomNegativeArea = new Room(-10.0f,10.0f,10.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomNegativeArea);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNegativeCube(){
        Room roomNegativeCube = new Room(10.0f,-10.0f,10.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomNegativeCube);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }
    @Test
    public void TestNegativeHeating(){
        Room roomNegativeHeating = new Room(10.0f,10.0f,-10.0f,10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomNegativeHeating);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestNegativeLight(){
        Room roomNegativeLight = new Room(10.0f,10.0f,10.0f,-10.0f,103,"Room 3");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(roomNegativeLight);
        properLevel2.addLocation(properRoom4);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestDuplicateID(){
        Room roomDuplicateID = new Room(20.0f,20.0f,20.0f,20.0f,101,"Room 4");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(roomDuplicateID);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }

    @Test
    public void TestDuplicateName(){
        Room roomDuplicateName = new Room(20.0f,20.0f,20.0f,20.0f,104, "Room 1");
        properLevel1.addLocation(properRoom1);
        properLevel1.addLocation(properRoom2);
        properLevel2.addLocation(properRoom3);
        properLevel2.addLocation(roomDuplicateName);
        properBuilding.addLocation(properLevel1);
        properBuilding.addLocation(properLevel2);
        assertFalse(Validate.validateStructure(properBuilding, 3));
    }




}