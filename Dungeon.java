
/**
 * This class represent the dungeons of the game.
 */
import java.util.ArrayList;

public class Dungeon {
    private ArrayList<Floor> floors;
    private int currentFloor;
    private int dungeonNumber;
    private String dungeonName;
    private Idol idol;

    /**
     * Constructs a dungeon with the dungeon name and idol to be rescued.
     * Dungeon number is assigned once the player enters the dungeon
     *
     * @param dungeonName name of the dungeon
     * @param idol idol to be rescued
     */
    public Dungeon(String dungeonName, Idol idol) {
        this.dungeonNumber = 0;
        this.dungeonName = dungeonName;
        this.idol = idol;

        this.floors = new ArrayList<>();
        this.currentFloor = 0;
    }

    /**
     * Returns the current floor in the dungeon
     *
     * @return current floor
     */
    public Floor getCurrentFloor() {
        return floors.get(currentFloor);
    }

    /**
     * Returns the current floor number
     *
     * @return current floor number
     */
    public int getCurrentFloorNumber() {
        return currentFloor + 1;
    }

    /**
     * Returns the total number of floors in the dungeon
     *
     * @return total number of floors
     */
    public int getTotalFloors() {
        return floors.size();
    }

    /**
     * Returns the idol in the dungeon
     *
     * @return the idol to be rescued
     */
    public Idol getIdol() {
        return idol;
    }

    /**
     * Checks what dungeon number you're currently on
     *
     * @return the value of the dungeon number you're on
     */
    public int getDungeonNumber() {
        return dungeonNumber;
    }

    /**
     * Checks what dungeon you're currently on
     *
     * @return the name of the dungeon you're on
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * Goes to the next floor in the dungeon
     */
    public void nextFloor() {
        if (currentFloor < floors.size() - 1)
            currentFloor++;
    }

    /**
     * Checks to see if it is the last floor
     *
     * @return true if you reached the last floor
     */
    public boolean isLastFloor() {
        return currentFloor == floors.size() - 1;
    }

    /**
     * Starts the dungeon by assigning its dungeon number
     * and generating its floors
     *
     * @param dungeonNumber order the dungeon is played
     * @param yohane player exploring the dungeon
     */
    public void startDungeon (int dungeonNumber, Yohane yohane) {
        // Assign the dungeon number
        this.dungeonNumber = dungeonNumber;

        // reset dungeon progress
        currentFloor = 0;
        floors.clear();
        int totalFloors;

        // determines number of floors
        switch (dungeonNumber) {
            case 1:
                totalFloors = 1;
                break;

            case 2:
                totalFloors = (int) (Math.random() * 2) + 2;      // 2 or 3
                break;

            case 3:
                totalFloors = (int) (Math.random() * 2) + 3;      // 3 or 4
                break;

            default:
                totalFloors = 1;
        }

        // Create the floors
        for (int i = 1; i <= totalFloors; i++) { // i is the floorNumber
            floors.add(new Floor(
                    dungeonNumber,
                    dungeonName,
                    i,
                    totalFloors,
                    yohane
            ));
        }
    }
}
