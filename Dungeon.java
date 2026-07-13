public class Dungeon {
    private Floor floor;

    public Dungeon() {
        floor = new Floor(1, "Beginner's Cave", 1, 1);
    }

    public Floor getFloor() {
        return floor;
    }
}
