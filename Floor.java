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
    private String message = "";
    private String deathCause = "";

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
    public Floor(int dungeonNumber, String dungeonName, int floorNumber, int totalFloors) {
        this.dungeonNumber = dungeonNumber;
        this.dungeonName = dungeonName;
        this.floorNumber = floorNumber;
        this.totalFloors = totalFloors;
        bats = new ArrayList<>();
        generateMap();
    }

    public Yohane getYohane() {
        return yohane;
    }

    private String tileToColoredString(int row, int col) {

        // Yohane
        if (yohane.getRow() == row && yohane.getCol() == col)
            return PINK + "Y " + RESET;

        // Bat
        for (Bat bat : bats) {
            if (bat.getRow() == row && bat.getCol() == col) {
                if (bat.hasJustAttacked())
                    return RED + "B " + RESET;
                else
                    return RED + "b " + RESET;
            }
        }

        // Tiles
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

    public void generateMap() {
        map = new Tile[MAP1_TEMPLATE.length][MAP1_TEMPLATE[0].length()];
        for (int row = 0; row < MAP1_TEMPLATE.length; row++) {
            for (int col = 0; col < MAP1_TEMPLATE[row].length(); col++) {
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

    public void displayHeader() {
        System.out.println("Dungeon #" + dungeonNumber + ": " + dungeonName);
        System.out.println("Floor " + floorNumber + " of " + totalFloors);
        System.out.println();

    }

    public void displayStats() {
        System.out.printf("%-30s%30s%n",
                "HP: " + yohane.getHp() + "/" + yohane.getMaxHp(),
                "Gold: " + yohane.getGold());

        Item item = yohane.getCurrentItem();
        System.out.println("Items on Hand: " +
                (item == null ? "None" : item.getName()));

        System.out.println();
    }

    public void displayMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(tileToColoredString(row, col));
            }
            System.out.println();
        }
    }

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

    private void moveBats() {
        for(Bat bat : bats)
            bat.takeTurn(yohane, this);

        if (yohane.getHp() <= 0 && deathCause.isEmpty()) {
            deathCause = "a Bat";
            floorFinished = true;
        }
    }

    public void playerMovement(char direction){
        int newRow = yohane.getRow();
        int newCol = yohane.getCol();

        switch (direction) {
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
            applyHeatDamage();
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
                message += GREEN + "Obstacle cleared! You can now pass." + RESET + "\n";                applyHeatDamage();
                endTurn();
                break;

            case TREASURE:
                if (Math.random() < 0.5) {
                    int gold = (int)(Math.random() * 91) + 10;
                    yohane.addGold(gold);
                    map[newRow][newCol] = Tile.PASSABLE;
                    message += YELLOW + "You got " + gold + " GP!" + RESET + "\n";
                } else {
                    yohane.addItem(new Item("Noppo Bread"));
                    map[newRow][newCol] = Tile.PASSABLE;
                    message += GREEN + "You got Noppo Bread!" + RESET + "\n";
                }

                applyHeatDamage();
                endTurn();
                break;

            case SPIKE:
                yohane.takeDamage(0.5f);
                map[newRow][newCol] = Tile.PASSABLE;
                message += RED + "You got 0.5 HP damage from the Spike!" + RESET + "\n";
                if (yohane.getHp() <= 0) {
                    this.deathCause = "Spike Damage";
                }
                applyHeatDamage();
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
                break;

            case BORDER:
                message += RED + "You can't go there!" + RESET + "\n";
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

    private void endTurn() {
        playerTurns++;
        if (playerTurns % 2 == 0)
            moveBats();
    }

    public void waitTurn() {
        applyHeatDamage();
       message += RED + "Invalid input! You stayed in the same place." + RESET + "\n";
        endTurn();
    }

    private void applyHeatDamage() {
        if (map[yohane.getRow()][yohane.getCol()] == Tile.HEAT) {
            yohane.takeDamage(1.0f);
            message += RED + "You took 1 HP damage from the heat!" + RESET + "\n";
            if (yohane.getHp() <= 0) {
                this.deathCause = "Extreme Heat";
            }
        }
    }

    public boolean isFloorFinished() {
        return floorFinished;
    }

    public String getDeathCause() {
        return deathCause;
    }

    public void addMessage(String text) {
        this.message += text;
    }

}

