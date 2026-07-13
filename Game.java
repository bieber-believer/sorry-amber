import java.util.Scanner;

public class Game {
    private Dungeon dungeon;
    private Scanner scanner;

    public Game() {
        dungeon = new Dungeon();
        scanner = new Scanner(System.in);
    }

    public void start() {
        Floor floor = dungeon.getFloor();

        while (!floor.isFloorFinished() && floor.getYohane().getHp() > 0) {
            floor.displayFloor();

            System.out.print("Move (W/A/S/D/X): ");
            char input = Character.toUpperCase(scanner.next().charAt(0));

            floor.playerMovement(input);
        }

        if (floor.getYohane().getHp() <= 0)
            System.out.println("Game Over!");
        else
            System.out.println("Floor Cleared!");
    }
}
