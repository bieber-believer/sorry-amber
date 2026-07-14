/**
 * Represents a single floor within the dungeon. Holds the floor layout, bats on 
 * the floor, and Yohane. Handles displaying floor and floor information, processes
 * player movement and bat turns.
 * 
 * Floor
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Floor {
    private Tile[][] map;
    private ArrayList<Bat> bats;
    private Yohane yohane;
    private int totalFloors;
    private boolean floorFinished;
    private int floorNumber;
    private int dungeonNumber;
    private String dungeonName;
    private int playerTurns = 0;
    private String message = ""; // messages during the game
    private String deathCause = ""; // cause of death

    //colors 
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[38;2;255;0;0m";
    private static final String GREEN = "\u001B[38;2;0;180;50m";
    private static final String EXIT_GREEN = "\u001B[38;2;57;255;20m";
    private static final String YELLOW = "\u001B[38;2;255;255;0m";
    private static final String BLUE = "\u001B[38;2;33;33;255m";
    private static final String PINK = "\u001B[38;2;255;105;180m";
    private static final String CYAN = "\u001B[38;2;0;255;255m";
    private static final String WHITE = "\u001B[38;2;255;255;255m";
    private static final String ORANGE = "\u001B[38;2;255;128;0m";

    private static final String[] MAP1_TEMPLATE = {
            "*******************************************************",
            "*.....x.............x.................................*",
            "*..T..x....h........x...............b.................*",
            "*.....x....hh.......x.......ww........xx..............*",
            "*.....xx.......b...xx.......ww........xx....x...b.....*",
            "*.....x....v.......xx..x....ww........xx.b..x.........*",
            "*.x...x....x.......vx..x....ww........xx....x.........*",
            "*.x..Yx....x.......vx.......ww........xx..vvv...xxxbxx*",
            "*..x..vvvvvvvvvvvvv....vvvvvww........xx.h.....vv.....*",
            "*..x....x.................vvv............h.....vv.....*",
            "*.......x..................v.......b.....h.....vv.b..E*",
            "*******************************************************"
    };

    /**
     * Creates a Floor with the given dungeon number, name, floor number,
     * and total floor count. It creates the bat list and generates the map
     * 
     * @param dungeonNumber number of the dungeon this floor belongs to
     * @param dungeonName name of the dungeon
     * @param floorNumber current floor number
     * @param totalFloors total number of floors in the dungeon
     */
    public Floor(int dungeonNumber, String dungeonName, int floorNumber, int totalFloors) {
        this.dungeonNumber = dungeonNumber;
        this.dungeonName = dungeonName;
        this.floorNumber = floorNumber;
        this.totalFloors = totalFloors;
        bats = new ArrayList<>();
        generateMap();
    }

    /**
     * Returns the Yohane object on the floor
     * 
     * @return yohane
     */
    public Yohane getYohane() {
        return yohane;
    }

    /**
     * Returns the colored text of whatever is in the tile position
     * 
     * @param row row to check
     * @param col column to checl
     * @return colored string of whatever tile is in the position
     */
    private String tileToColoredString(int row, int col) {
        // yohane
        if (yohane.getRow() == row && yohane.getCol() == col)
            return PINK + "Y " + RESET;

        // bat
        for (Bat bat : bats) {
            if (bat.getRow() == row && bat.getCol() == col) {
                if (bat.hasJustAttacked())
                    return RED + "B " + RESET;
                else
                    return RED + "b " + RESET;
            }
        }

        // tiles
        switch (map[row][col]) {
            case PASSABLE:
                return ". ";

            case WALL:
                return GREEN + "v " + RESET;

            case SPIKE:
                return WHITE + "x " + RESET;

            case WATER:
                return CYAN + "w " + RESET;

            case HEAT:
                return ORANGE + "h " + RESET;

            case TREASURE:
                return YELLOW + "T " + RESET;

            case EXIT:
                return EXIT_GREEN + "E " + RESET;

            case BORDER:
                return BLUE + "* " + RESET;

            case GOLD:
                return YELLOW + "g " + RESET;

            default:
                return "  ";
        }
    }

    /**
     * Builds the map from MAP_1 TEMPLATE. Also creates Yohane and Bat objects
     */
    public void generateMap() {
        map = new Tile[MAP1_TEMPLATE.length][MAP1_TEMPLATE[0].length()];
        for (int row = 0; row < MAP1_TEMPLATE.length; row++) { // printing row
            for (int col = 0; col < MAP1_TEMPLATE[row].length(); col++) { // printing column
                char symbol = MAP1_TEMPLATE[row].charAt(col);
                switch (symbol) {
                    case '.':
                        map[row][col] = Tile.PASSABLE;
                        break;
                    case '*':
                        map[row][col] = Tile.BORDER;
                        break;
                    case 'v':
                        map[row][col] = Tile.WALL;
                        break;
                    case 'x':
                        map[row][col] = Tile.SPIKE;
                        break;
                    case 'w':
                        map[row][col] = Tile.WATER;
                        break;
                    case 'h':
                        map[row][col] = Tile.HEAT;
                        break;
                    case 'T':
                        map[row][col] = Tile.TREASURE;
                        break;
                    case 'E':
                        map[row][col] = Tile.EXIT;
                        break;
                    case 'Y':
                        map[row][col] = Tile.PASSABLE;
                        yohane = new Yohane(row, col);
                        break;
                    case 'b':
                        map[row][col] = Tile.PASSABLE;
                        bats.add(new Bat(row, col, 0.5f, 5, 2));
                        break;
                }
            }
        }
    }

    /**
     * Displays info regarding the dungeon like dungeon number, name, and floor 
     * progress
     */
    public void displayHeader() {
        System.out.println("Dungeon #" + dungeonNumber + ": " + dungeonName);
        System.out.println("Floor " + floorNumber + " of " + totalFloors);
        System.out.println();
    }

    /**
     * Displays Yohane's current stats like HP, gold, and item on hand
     */
    public void displayStats() {
        System.out.printf("%-30s%30s%n",
                "HP: " + yohane.getHp() + "/" + yohane.getMaxHp(),
                "Gold: " + yohane.getGold());

        Item item = yohane.getCurrentItem();
        System.out.println("Items on Hand: " + (item == null ? "None" : item.getName()));

        System.out.println();
    }

    /**
     * Displays the full map of the floor
     */
    public void displayMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(tileToColoredString(row, col));
            }
            System.out.println();
        }
    }

    /**
     * Displays the full interface of the dungeon like the header, stats,
     * map, and a message if it is not empty
     */
    public void displayFloor() {
        displayHeader();
        displayStats();
        System.out.println();
        displayMap();

        if (!message.isEmpty()) {
            System.out.println();
            System.out.println(message);
            message = "";
        }
    }

    /**
     * Checks whether Yohane can walk onto a tile at the given position
     * 
     * @param row row to check
     * @param col column to check
     * @return true if Yohane can pass through it, false otherwise
     */
    public boolean isPassableForYohane(int row, int col) {
        Tile tile = map[row][col];

        switch (tile) {
            case PASSABLE:
            case HEAT:
            case EXIT:
            case GOLD:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks whether a bat can walk onto a tile at the given position
     * 
     * @param row row to check
     * @param col column to check
     * @return true if the bat can pass through it, false otherwise
     */
    public boolean isPassableForBat(int row, int col) {
        Tile tile = map[row][col];
        switch (tile) {
            case PASSABLE:
            case WATER:
                return true;
            default:
                return false;
        }
    }

    /**
     * Makes the bats in the floor take its turn and checks whether it killed 
     * Yohane
     */
    private void moveBats() {
        for(Bat bat : bats)
            bat.takeTurn(yohane, this);

        if (yohane.getHp() <= 0 && deathCause.isEmpty()) {
            deathCause = "a Bat";
            floorFinished = true;
        }
    }

    /**
     * Processes the players movement as well as tile interactions
     * 
     * @param action input of player regarding yohane's next move
     */
    public void playerAction(char action){
        int newRow = yohane.getRow();
        int newCol = yohane.getCol();

        switch (action) {
            case 'W':
                newRow--;
                break;
            case 'S':
                newRow++;
                break;
            case 'A':
                newCol--;
                break;
            case 'D':
                newCol++;
                break;
            case ' ':
                yohane.useCurrentItem();
                applyHeatDamage(); // check if she used an item on a heat tile
                endTurn(); // count as turn
                break;
            case '[':
                yohane.switchToPreviousItem();
                applyHeatDamage(); // check if she used an item on a heat tile
                endTurn();
                break;
            case ']':
                yohane.switchToNextItem();
                applyHeatDamage(); // check if she used an item on a heat tile
                endTurn();
                break;
            default:
                waitTurn();
                return;
        }

        Bat deadBat = null;

        for (Bat bat : bats) {
            if (bat.getRow() == newRow && bat.getCol() == newCol) {
                deadBat = bat;
                break;
            }
        }

        if (deadBat != null) {
            bats.remove(deadBat);
            map[newRow][newCol] = Tile.GOLD;
            message += GREEN + "You killed the bat! Collect gold from the ground." + RESET + "\n";
            applyHeatDamage(); // check to see if she on heat tile
            endTurn();
            return;
        }

        Tile tile = map[newRow][newCol];
        switch (tile){
            case PASSABLE:
                yohane.setPosition(newRow, newCol);
                endTurn();
                break;

            case WALL:
                map[newRow][newCol] = Tile.PASSABLE;
                message += GREEN + "Obstacle cleared! You can now pass." + RESET + "\n";                
                applyHeatDamage(); // check to see if she on heat tile
                endTurn();
                break;

            case TREASURE:
                if (Math.random() < 0.5) {
                    int gold = (int)(Math.random() * 91) + 10;
                    yohane.addGold(gold);
                    map[newRow][newCol] = Tile.PASSABLE;
                    message += YELLOW + "You got " + gold + " GP!" + RESET + "\n";
                    yohane.setPosition(newRow, newCol);
                } else {
                    yohane.addItem(new Item("Noppo Bread"));
                    map[newRow][newCol] = Tile.PASSABLE;
                    message += GREEN + "You got Noppo Bread!" + RESET + "\n";
                    yohane.setPosition(newRow, newCol);
                }

                applyHeatDamage(); // check to see if she on heat tile
                endTurn();
                break;

            case SPIKE:
                yohane.takeDamage(0.5f);
                map[newRow][newCol] = Tile.PASSABLE;
                message += RED + "You got 0.5 HP damage from the Spike!" + RESET + "\n";
                if (yohane.getHp() <= 0) {
                    this.deathCause = "Spike Damage";
                }
                applyHeatDamage(); // check to see if she on heat tile
                endTurn();
                break;

            case EXIT:
                yohane.setPosition(newRow, newCol);
                floorFinished = true;
                break;

            case HEAT:
                yohane.setPosition(newRow, newCol);
                endTurn();
                break;

            case WATER:
                message += RED + "You can't pass here! Try going around it." + RESET + "\n";
                applyHeatDamage(); // check to see if on heat tile
                endTurn();
                break;

            case BORDER:
                message += RED + "You can't go there!" + RESET + "\n";
                applyHeatDamage();
                endTurn();
                break;

            case GOLD:
                yohane.addGold(5);
                map[newRow][newCol] = Tile.PASSABLE;
                message += YELLOW + "You get 5 GP!" + RESET + "\n";
                yohane.setPosition(newRow, newCol);
                endTurn();
                break;

            default:
                return;
        }
    }

    /**
     * Increments player's turn count and triggers bat movement
     */
    private void endTurn() {
        playerTurns++;
        if (playerTurns % 2 == 0)
            moveBats();
    }

    /**
     * Handles invalid input and applies heat damage of Yohane stays on
     * top of a heat tile.
     */
    public void waitTurn() {
        applyHeatDamage(); //check if she is on heat tile
        message += RED + "Invalid input! You stayed in the same place." + RESET + "\n";
        endTurn();
    }

    /**
     * Apples heat dmaage to Yohane if she is currently standing ona heat tile
     * and checks whether it kills her
     */
    private void applyHeatDamage() {
        if (map[yohane.getRow()][yohane.getCol()] == Tile.HEAT) {
            yohane.takeDamage(1.0f);
            message += RED + "You took 1 HP damage from the heat!" + RESET + "\n";
            if (yohane.getHp() <= 0) {
                this.deathCause = "Extreme Heat";
            }
        }
    }

    /**
     * Checks whether this floor has been finished
     * 
     * @return true if the floor is finished, false otherwise
     */
    public boolean isFloorFinished() {
        return floorFinished;
    }

    /**
     * Returns the cause of Yohane's death
     * 
     * @return cause of death or an empty string if Yohane is still alive
     */
    public String getDeathCause() {
        return deathCause;
    }

    /**
     * Appends the given text to the floors message to be shown the
     * next time the floor is displayed
     * 
     * @param text
     */
    public void addMessage(String text) {
        this.message += text;
    }
}

