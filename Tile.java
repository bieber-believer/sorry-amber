/**
 * Represents a tile within the dungeon floor. This will help determing whether
 * Yohane can pass through a tile, and what happens if she interacts with it.
 * 
 * Tile
 */

//https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html

public enum Tile {
    PASSABLE, // .
    WALL,     // v
    SPIKE,    // x
    WATER,    // w
    HEAT,     // h
    TREASURE, // T
    EXIT,     // E
    BORDER,   //  *
    GOLD      // g
}
