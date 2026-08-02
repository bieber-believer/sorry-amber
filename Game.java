import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


/**
 * Handles the overall game. In charge of the menu loop, new playthroughs
 * and the dungeon gameplay.
 *
 * Game
 */
public class Game {
    private ArrayList<Dungeon> selectedDungeons;
    private Dungeon currentDungeon;
    private Yohane yohane;
    private int nextDungeonNumber;

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
        selectedDungeons = new ArrayList<>();
        currentDungeon = null;
        nextDungeonNumber = 1;

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
                    if (hasPlayedBefore)
                        newGamePlus();
                    else
                        newGame();
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
     * Starts a new game
     */
    private void newGame() {
        yohane = new Yohane();
        generateDungeons();
        gameplayLoop();
    }

    /**
     * Starts a new game+.
     * Available only after a player completes one playthrough.
     * Gold from previous playthrough is carried over.
     */
    private void newGamePlus() {
        yohane = new Yohane();
        yohane.addGold(carriedOverGold);
        generateDungeons();
        gameplayLoop();
    }

    /**
     * This chooses the 3 of 8 dungeons that will be played every start of
     * a game
     */
    private void generateDungeons() {
        // clear previous dungeons
        selectedDungeons = new ArrayList<>();
        Random rand = new Random();

        // tracks which dungeons are chosen
        boolean[] used = new boolean[8];

        // select until 3 dungeons are chosen
        while (selectedDungeons.size() < 3) {
            int index = rand.nextInt(8); // randomly picks dungeons

            if(!used[index]) { // skip if dungeon is already selected
                used[index] = true;
                selectedDungeons.add(createDungeon(index));
            }
        }
    }

    /**
     * Creates a dungeon
     *
     * @param index index of the selected dungeon location
     * @return the created dungeon
     */
    private Dungeon createDungeon(int index) {
        switch (index) {
            case 0:
                return new Dungeon(
                        "Izu-Mito Sea Paradise",
                        stats.getAqours().get(2)
                );

            case 1:
                return new Dungeon(
                        "Yasudaya Ryokan",
                        stats.getAqours().get(0)
                );

            case 2:
                return new Dungeon(
                        "Numazu Deep Sea Aquarium",
                        stats.getAqours().get(1)
                );

            case 3:
                return new Dungeon(
                        "Shougetsu Confectionary",
                        stats.getAqours().get(3)
                );

            case 4:
                return new Dungeon(
                        "Nagahama Castle Ruins",
                        stats.getAqours().get(4)
                );

            case 5:
                return new Dungeon(
                        "Numazugoyotei",
                        stats.getAqours().get(5)
                );

            case 6:
                return new Dungeon(
                        "Uchiura Bay Pier",
                        stats.getAqours().get(6)
                );

            case 7:
                return new Dungeon(
                        "Awashima Marine Park",
                        stats.getAqours().get(7)
                );

            default:
                return null;
        }
    }

    /**
     * Loops through the game
     */
    private void gameplayLoop() {
         // continues til all 3 dungeons are cleared
        while (!allDungeonsCleared()) {

            // choose dungeon
            currentDungeon = menu.chooseDungeon(selectedDungeons);

            // play selected dungeon
            playDungeon(currentDungeon);

            // stop if yohane dies
            if (!yohane.isAlive()) {
                return;
            }
        }

        // enter final dungeon
        playFinalDungeon();
    }

    /**
     * Plays the selected dungeon
     *
     * @param dungeon dungeon to be played
     */
    private void playDungeon(Dungeon dungeon) {
        // assign a dungeon number if it is first visit
        if (dungeon.getDungeonNumber() == 0) {
            dungeon.startDungeon (nextDungeonNumber, yohane);
            nextDungeonNumber++;
        }

        boolean dungeonFinished = false;

        // play each floor
        while (!dungeonFinished) {
            Floor floor = dungeon.getCurrentFloor();

            playFloor(floor);

            // stop if yohane dies
            if (!yohane.isAlive())
                return;

            // moves to next floor
            if (dungeon.isLastFloor())
                dungeonFinished = true;
            else
                dungeon.nextFloor();
        }

        // rescue idol
        dungeon.getIdol().rescue();
        
        dungeon.setCleared(true);
    }

    /**
     * Plays the floor in the dungeon
     *
     * @param floor floor to be played
     */
    private void playFloor(Floor floor) {
        // continue til the floor is finished or yohane dies
        while (!floor.isFloorFinished() && yohane.isAlive()) {
            floor.displayFloor();

            System.out.print("Move (W/A/S/D), Use Item (Space): ");

            String input = scanner.nextLine();

            if (!input.isEmpty()) {
                floor.playerAction(
                        Character.toUpperCase(input.charAt(0))
                );
            }
        }
    }

    /**
     * Checks if all dungeons are cleared
     *
     * @return true if all dungeons are cleared, false if not
     */
    private boolean allDungeonsCleared() {
        for (Dungeon dungeon : selectedDungeons) {
            if (!dungeon.isCleared())
                return false;
        }

        return true;
    }

    /**
     * Plays the final dungeon
     */
    private void playFinalDungeon() {

    }
}
}
