import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
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
        while (!allDungeonsCleared()) {

            // let player choose dungeon
            int choice = menu.chooseDungeon(selectedDungeons);

            // get selected dungeon
            currentDungeon = selectedDungeons.get(choice - 1);

            // play selected dungeon
            playDungeon(currentDungeon);
        }
        // final dungeon implementation
    }

    /**
     * Plays the selected dungeon
     */
    private void playDungeon() {

    }
}
