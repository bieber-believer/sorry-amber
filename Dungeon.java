public class Dungeon {
    private Floor floor;
    private int dungeonNumber;
    private String dungeonName;

    public Dungeon() {
        floor = new Floor(1, "Izu-Mito Sea Paradise", 1, 1);
    }

    public Floor getFloor() {
        return floor;
    }
}
