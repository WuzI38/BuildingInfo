package pl.put.poznan.buildinginfo.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidateTest {
    Room properRoom1 = null;
    Room properRoom2 = null;
    Room properRoom3 = null;
    Room properRoom4 = null;

    Space properLevel1 = null;
    Space properLevel2 = null;

    Space properBuilding = null;

    static Room[] mockRooms;

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

    @BeforeAll
    public static void setUpMocks() {
        mockRooms = new Room[8];
        for (int i = 0; i < mockRooms.length; i++) {
            mockRooms[i] = Mockito.mock(Room.class);
            Mockito.when(mockRooms[i].checkIfNull()).thenReturn(true);
            Mockito.when(mockRooms[i].checkIfZero()).thenReturn(true);
            Mockito.when(mockRooms[i].getId()).thenReturn(i + 1);
            Mockito.when(mockRooms[i].getName()).thenReturn("Room " + (i + 1));
        }
    }

    @Test // Mock test 1
    public void TestProperBuilding(){
        Space mockLevel1 = Mockito.mock(Space.class);
        Space mockLevel2 = Mockito.mock(Space.class);
        Space mockBuilding = Mockito.mock(Space.class);

        ArrayList<Location> rooms1 = new ArrayList<>();
        rooms1.add(mockRooms[0]);
        rooms1.add(mockRooms[1]);

        ArrayList<Location> rooms2 = new ArrayList<>();
        rooms2.add(mockRooms[2]);
        rooms2.add(mockRooms[3]);

        ArrayList<Location> levels = new ArrayList<>();
        levels.add(mockLevel1);
        levels.add(mockLevel2);

        Mockito.when(mockLevel1.getLocations()).thenReturn(rooms1);
        Mockito.when(mockLevel2.getLocations()).thenReturn(rooms2);
        Mockito.when(mockBuilding.getLocations()).thenReturn(levels);

        assertTrue(Validate.validateStructure(mockBuilding, 3));
    }

    @Test // Mock test 2
    public void TestDepth4(){
        Space mockLevel1 = Mockito.mock(Space.class);
        Space mockLevel2 = Mockito.mock(Space.class);
        Space mockLevel3 = Mockito.mock(Space.class);
        Space mockLevel4 = Mockito.mock(Space.class);
        Space mockBuilding1 = Mockito.mock(Space.class);
        Space mockBuilding2 = Mockito.mock(Space.class);
        Space mockBuildings = Mockito.mock(Space.class);

        ArrayList<Location> rooms1 = new ArrayList<>();
        rooms1.add(mockRooms[0]);
        rooms1.add(mockRooms[1]);

        ArrayList<Location> rooms2 = new ArrayList<>();
        rooms2.add(mockRooms[2]);
        rooms2.add(mockRooms[3]);

        ArrayList<Location> rooms3 = new ArrayList<>();
        rooms1.add(mockRooms[4]);
        rooms1.add(mockRooms[5]);

        ArrayList<Location> rooms4 = new ArrayList<>();
        rooms2.add(mockRooms[6]);
        rooms2.add(mockRooms[7]);

        ArrayList<Location> levels1 = new ArrayList<>();
        levels1.add(mockLevel1);
        levels1.add(mockLevel2);

        ArrayList<Location> levels2 = new ArrayList<>();
        levels1.add(mockLevel3);
        levels1.add(mockLevel4);

        ArrayList<Location> buildings = new ArrayList<>();
        levels1.add(mockBuilding1);
        levels1.add(mockBuilding2);

        Mockito.when(mockLevel1.getLocations()).thenReturn(rooms1);
        Mockito.when(mockLevel2.getLocations()).thenReturn(rooms2);
        Mockito.when(mockLevel3.getLocations()).thenReturn(rooms3);
        Mockito.when(mockLevel4.getLocations()).thenReturn(rooms4);
        Mockito.when(mockBuilding1.getLocations()).thenReturn(levels1);
        Mockito.when(mockBuilding1.getLocations()).thenReturn(levels2);
        Mockito.when(mockBuildings.getLocations()).thenReturn(buildings);

        assertTrue(Validate.validateStructure(mockBuildings, 4));
    }


    @Test // Mock test 3
    public void TestDepth2(){
        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(mockRooms[1]);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertTrue(Validate.validateStructure(mockLevel, 2));
    }

    @Test // Mock test 4
    public void TestDepth1(){
        assertFalse(Validate.validateStructure(mockRooms[0], 1));
    }

    @Test // Mock test 5
    public void TestNullIDRoom(){
        Room r1 = Mockito.mock(Room.class);
        Mockito.when(r1.checkIfNull()).thenReturn(true);
        Mockito.when(r1.checkIfZero()).thenReturn(true);
        Mockito.when(r1.getId()).thenReturn(null);

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(r1);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
    }

    @Test // Mock test 6
    public void TestMockNull(){
        Room r1 = Mockito.mock(Room.class);
        Mockito.when(r1.checkIfNull()).thenReturn(false);
        Mockito.when(r1.checkIfZero()).thenReturn(true);
        Mockito.when(r1.getId()).thenReturn(0);

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(r1);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
    }

    @Test // Mock test 7
    public void TestMockZeros() {
        Room r1 = Mockito.mock(Room.class);
        Mockito.when(r1.checkIfNull()).thenReturn(true);
        Mockito.when(r1.checkIfZero()).thenReturn(false);
        Mockito.when(r1.getId()).thenReturn(0);
        Mockito.when(r1.getName()).thenReturn("Room 0");

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(r1);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
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

    @Test // Mock test 8
    public void TestDuplicateID(){
        Room r1 = Mockito.mock(Room.class);
        Mockito.when(r1.checkIfNull()).thenReturn(false);
        Mockito.when(r1.checkIfZero()).thenReturn(false);
        Mockito.when(r1.getId()).thenReturn(1);
        Mockito.when(r1.getName()).thenReturn("Room 0");

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(r1);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
    }
    @Test // Mock test 9
    public void TestDuplicateName(){
        Room r1 = Mockito.mock(Room.class);
        Mockito.when(r1.checkIfNull()).thenReturn(false);
        Mockito.when(r1.checkIfZero()).thenReturn(false);
        Mockito.when(r1.getId()).thenReturn(0);
        Mockito.when(r1.getName()).thenReturn("Room 1");

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(mockRooms[0]);
        rooms.add(r1);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
    }

    @Test // Mock test 10
    public void TestEmptyMocks(){
        Room r1 = Mockito.mock(Room.class);
        Room r2 = Mockito.mock(Room.class);

        Space mockLevel = Mockito.mock(Space.class);

        ArrayList<Location> rooms = new ArrayList<>();
        rooms.add(r1);
        rooms.add(r2);

        Mockito.when(mockLevel.getLocations()).thenReturn(rooms);

        assertFalse(Validate.validateStructure(mockLevel, 2));
    }
}