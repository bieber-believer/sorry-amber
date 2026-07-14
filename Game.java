import java.util.Scanner;

/**
 * Handles the overall game. In charge of the menu loop, new playthroughs
 * and the dungeon gameplay.
 *
 * Game
 */
public class Game {
    private Dungeon dungeon;
    private Scanner scanner;
    private Menu menu;
    private Floor floor;
    private boolean hasPlayedBefore = false;
    private OverallStats stats;
    private int carriedOverGold = 0;

    /**
     * Constructs a game
     */
    public Game() {
        scanner = new Scanner(System.in);
        menu = new Menu(scanner);
        stats = new OverallStats();
    }

    /**
     * Handles the main menu loop
     */
    public void start() {
        boolean running = true;

        while (running) {
            char choice = menu.getChoice(hasPlayedBefore);

            switch (choice) {
                case 'n':
                    if (!hasPlayedBefore)
                        carriedOverGold = 0;

                    while (true) {
                        playGame();
                        if (floor == null) {
                            break;
                        }
                        hasPlayedBefore = true;
                        boolean won = floor.getYohane().getHp() > 0;

                        if (!won) {
                            stats.addGamesLost();
                        } else {
                            stats.getAqours().get(2).rescue();
                        }

                        carriedOverGold = floor.getYohane().getGold();

                        char endChoice = menu.displayEndScreen(won, hasPlayedBefore, floor.getDeathCause());
                        if (endChoice == 'n') {
                            continue;
                        }
                        if (endChoice == 'm') {
                            break;
                        }
                        if (endChoice == 'q') {
                            running = false;
                            break;
                        }
                    }
                    break;

                case 's':
                    menu.displayStatus(stats);
                    break;

                case 'q':
                    running = false;
                    System.out.println("See you again soon!");
                    break;
            }
        }
    }

    /**
     * Lets player choose a dungeon then run the dungeon until the floor
     * is finished or Yohane dies.
     */
    private void playGame() {
        int dungeonChoice = menu.chooseDungeon();

        switch (dungeonChoice) {
            case 1:
                dungeon = new Dungeon();
                break;

            case 2: // only 1 deungeon implemented so far
            case 3:
                System.out.println("This dungeon isn't available yet!");
                return;
        }

        floor = dungeon.getFloor();
        floor.getYohane().addGold(carriedOverGold);

        while (!floor.isFloorFinished() && floor.getYohane().getHp() > 0) {
            floor.displayFloor();

            System.out.print("Move (W/A/S/D), Use Item(Spacebar): ");
            String inputStr = scanner.nextLine();

            if (!inputStr.isEmpty()) {
                char input = Character.toUpperCase(inputStr.charAt(0));
                floor.playerAction(input);
            }
        }
    }
}
