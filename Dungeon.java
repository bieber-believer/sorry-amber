public class Dungeon {
    private Floor floor;
    private int dungeonNumber;
    private String dungeonName;

    /**
     * Constructs a dungeon by creating its floor
     */
    public Dungeon() {
        floor = new Floor(1, "Izu-Mito Sea Paradise", 1, 1);
    }

    /**
     * Returns the current floor in the dungeon
     * 
     * @return current floor
     */
    public Floor getFloor() {
        return floor;
    }
}
