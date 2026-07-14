import java.util.Random;

/**
 * Represents a bat in the dungeon. Bats move randomly depending on
 * the dungeon and can attack Yohane if Yohane is adjacent to the bat.
 * Bats damage and movement depends on the dungeon they are in.
 */
public class Bat {
    private int row, col; // bat's position
    private boolean justAttacked; // will help change b to B
    private float attackDamage;
    private int goldDrop; // how many gold a bat drops
    private int moveFrequency; // how frequent a bat will move based on yohane turn
    private static final String RED = "\u001B[38;2;255;0;0m";
    private static final String RESET = "\u001B[0m";
    private String message = "";

    //helps with the movement of the bat
    private static final int[] ROW_DIR = {-1, 1, 0, 0};
    private static final int[] COL_DIR = {0, 0, -1, 1};
    // if index = 0 for row and col, bat goes up, index = 1, bat goes down, 
    // index = 2 bat goes left, index = 3 bat go right

    private Random rand; //to generat random num

    /**
     * Create a bat object, given its starting position, damage. gold dropped, 
     * and how often the bath will move. The parameters will depend to the 
     * dungeon it belongs to.
     *
     * @param row starting y position
     * @param col starting x position
     * @param attackDamage the amount of damage bat deals
     * @param goldDrop the amount of gold a bat can drop
     * @param moveFrequency bat moves after every n of Yohane's turn
     */
    public Bat(int row, int col, float attackDamage, int goldDrop, int moveFrequency){
        this.row = row;
        this.col = col;
        this.attackDamage = attackDamage;
        this.goldDrop = goldDrop;
        this.moveFrequency = moveFrequency;
        this.justAttacked = false;
        this.rand = new Random();
    }

    /**
     * Check whether the bat is adjacent to the given postion
     *
     * @param otherRow row to check
     * @param otherCol colum to check
     * @return true if adjacent, false otherwise
     */
    public boolean isAdjacentTo(int otherRow, int otherCol){
        boolean above = (this.row == otherRow - 1 && this.col == otherCol);
        boolean below = (this.row == otherRow + 1 && this.col == otherCol);
        boolean left  = (this.row == otherRow && this.col == otherCol - 1);
        boolean right = (this.row == otherRow && this.col == otherCol + 1);

        return above || below || left || right;
    }

    /**
     * Moves the bat in a random direction. If the tiles is not
     * passable by the bat, it stays in place for that turn.
     *
     * @param floor floor bat exists on
     */
    private void moveRandomly(Floor floor){
        int index = rand.nextInt(4); // for the index for fow and col dir

        int newRow = this.row + ROW_DIR[index];
        int newCol = this.col + COL_DIR[index];

        if(floor.isPassableForBat(newRow, newCol)){
            this.row = newRow;
            this.col = newCol;
        } // if its not passable bat just stays put
    }

    /**
     * Simulates a bat's turn
     *
     * @param yohane the player
     * @param floor floor bat is on
     */
    public void takeTurn(Yohane yohane, Floor floor){
        if(isAdjacentTo(yohane.getRow(), yohane.getCol())){
            yohane.takeDamage(attackDamage);
            justAttacked = true;
            floor.addMessage(RED + "You have been attacked by a bat! You lose 0.5 HP!" + RESET + "\n");
            return; //bat dont move after attack
        }

        justAttacked = false;
        moveRandomly(floor);
    }

    /**
     * Return bat's current row positon (y position)
     *
     * @return current row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the bat's current column position (x position)
     *
     * @return current column position
     */
    public int getCol() {
        return col;
    }

    /**
     * Checks if the bat just attacked Yohane on its most recent turn.
     * Used to render bat as B or b.
     *
     * @return true if the bat just attacked, false otherwise
     */
    public boolean hasJustAttacked(){
        return justAttacked;
    }

    /**
     * Returns the amount of gold a bat drops when it dies
     *
     * @return amount of gold dropped
     */
    public int getGoldDrop() {
        return goldDrop;
    }

    /**
     * Returns how often a bat will move based on Yohane's number of turns
     *
     * @return bat's move frequency
     */
    public int getMoveFrequency() {
        return moveFrequency;
    }
}
