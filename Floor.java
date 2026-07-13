import java.util.ArrayList;

public class Floor {
    private Tile[][] map;
    private ArrayList<Bat> bats;
    private Yohane yohane;
    private int treasureRow;
    private int treasureCol;
    private boolean treasureClaimed;
    private int exitRow;
    private int exitCol;
    private int totalFloors;
    private boolean floorFinished;
    private int floorNumber;
    private int totalFloors;

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
        treasureClaimed = false;
        generateMap();
    }

    private char getTileSymbol(Tile tile) {
        switch(tile) {
            case PASSABLE:
                return '.';
            case WALL:
                return 'v';
            case SPIKE:
                return 'x';
            case WATER:
                return 'w';
            case HEAT:
                return 'h';
            case TREASURE:
                return 'T';
            case EXIT:
                return 'E';
            case BORDER:
                return '*';
            default:
                return ' ';
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
                        treasureRow = row;
                        treasureCol = col;
                        break;
                    case 'E':
                        map[row][col] = Tile.EXIT;
                        exitRow = row;
                        exitCol = col;
                        break;
                    case 'Y':
                        map[row][col] = Tile.PASSABLE;
                        yohane = new Yohane(row, col);
                        break;
                    case 'b':
                        map[row][col] = Tile.PASSABLE;
                        bats.add(new Bat(row, col, 0.5f, 20, 1));
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
        System.out.println("HP: " + yohane.getHp() + "/" + yohane.getMaxHp());
        System.out.println("Gold: " + yohane.getGold());
    }

    public void displayInventory() {
        Item item = yohane.getCurrentItem();
        if(item == null)
            System.out.println("Items on Hand: None");
        else
            System.out.println("Items on Hand: " + item.getName());
    }

    public void displayMap() {
        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[row].length; col++) {
                if(yohane.getRow() == row && yohane.getCol() == col) {
                    System.out.print('Y');
                } else {
                    boolean batFound = false;
                    for(Bat bat : bats) {
                        if(bat.getRow() == row && bat.getCol() == col) {
                            if(bat.hasJustAttacked())
                                System.out.print('B');
                            else
                                System.out.print('b');
                            batFound = true;
                            break;
                        }
                    }
                    if(!batFound)
                        System.out.print(getTileSymbol(map[row][col]));
                }
            }
            System.out.println();
        }
    }

    public void displayFloor() {
        displayHeader();
        displayStats();
        displayInventory();
        System.out.println();
        displayMap();
    }

    public boolean isPassableForYohane(int row, int col) {
        Tile tile = map[row][col];

        switch (tile) {
            case PASSABLE:
            case HEAT:
            case EXIT:
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
        }
        Tile tile = map[newRow][newCol];
        switch (tile){
            case PASSABLE:
                yohane.setPosition(newRow, newCol);
                break;

            case WALL:
                map[newRow][newCol] = Tile.PASSABLE;
                break;

            case TREASURE:
                if (Math.random() < 0.5) {
                    int gold = (int)(Math.random() * 91) + 10;
                    yohane.addGold(gold);
                } else {
                    yohane.addItem(new Item("Noppo Bread"));
                }

                treasureClaimed = true;
                map[newRow][newCol] = Tile.PASSABLE;
                break;

            case SPIKE:
                yohane.takeDamage(0.5);
                map[newRow][newCol] = Tile.PASSABLE;
                break;

            case EXIT:
                yohane.setPosition(newRow, newCol);
                floorFinished = true;
                break;

            case HEAT:
                yohane.setPosition(newRow, newCol);
                break;

            case WATER:
                System.out.println("You can't pass here! Try going around it.");
                break;

            case BORDER:
                System.out.println("You can't go there!");

        }
    }
}


