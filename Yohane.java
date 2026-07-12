/**
 * Represents the player. Tracks her position, health, gold, iventory.
 * 
 * Yohane
 */

import java.util.ArrayList;

public class Yohane {
    private float hp, maxHp; // her current hp and max hp shec can have
    private ArrayList<Item> iventory;
    private int currentItemIndex;
    private int gold;
    private int x, y; // for her position: x will be column, y will be row

    /**
     * Creates a Yohane object at the given starting position. By default, HP 
     * is 3/3 , 0 gold, and empty inventory
     * 
     * @param x starting x position (the col in the 2d array)
     * @param y starting y position (the row in the 2d array)
     */
    public Yohane(int x, int y){
        this.x = x;
        this.y = y;
        this.hp = 3;
        this.maxHp = 3;
        this.gold = 0;
        this.iventory = new ArrayList<>();
        this.currentItemIndex = -1; // -1 if inventory is empty
    }

    // methods that help with positioning
    /**
     * Updates Yohane's position given the x and y.
     * Called by dungeon after checking move input
     * 
     * @param x new row position
     * @param y new col position
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns Yohane's current row position
     * 
     * @return current row (x)
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Yohane's current column position
     * 
     * @return current column (y)
     */
    public int getY() {
        return y;
    }

    // methods that has smth to do with hp and killing the evil things
    /**
     * Yohane takes damage and reduces her HP by the given amount
     * 
     * @param damage
     */
    public void takeDamage(float damage){
        this.hp -= damage;
        if(this.hp < 0) this.hp = 0; // to make saure hp is not gon be a - value
    }

    /**
     * Heals Yohane by the given amount
     * 
     * @param restored amount of hp restored
     */
    public void heal(float restored){
        this.hp += restored;
        if(this.hp > this.maxHp) this.hp = this.maxHp; // to make sure no exceed max hp
    }

    /**
     * Checks whether Yohane is still alive
     * 
     * @return true if HP is above 0, false otherwise
     */
    public boolean isAlive(){
        return this.hp > 0;
    }

    /**
     * Returns Yohane's current hp
     * 
     * @return current hp
     */
    public float getHp() {
        return hp;
    }

    /**
     * Returns Yohane's max hp
     * 
     * @return max hp
     */
    public float getMaxHp() {
        return maxHp;
    }

    // for the moolah
    /**
     * Add gold to Yohane's total gold
     * 
     * @param amount amount of gold to add
     */
    public void addGold(int amount){
        this.gold += amount;
    }
    
    /**
     * Returns the total gold Yohane has currently
     * 
     * @return current gold
     */
    public int getGold() {
        return gold;
    }

    // for the inv
    /**
     * Adds an item to Yohane's inventory. If an Yohane already has that item,
     * its quantity gets incremented. If this is the first item picked up, it 
     * becomes the item on hand.
     * 
     * @param newItem item to add
     */
    public void addItem(Item newItem){
        //to check if yohane already has that item
        for(Item item : iventory){ // hi amber, this means "for each item in the inventory". this is a for each loop
            if(item.getName().equals(newItem.getName())){
                item.incrementQty(); // add +1 to qty
                return;
            }
        }

        //if yohane dont have that item yet
        iventory.add(newItem);

        if(currentItemIndex == -1) // -1 means empty
            currentItemIndex = iventory.size() - 1; // sets the item index to 0 if yohane has nthg on hand
                                                    // inventory.size = 1 after adding the item so index becomes 0
    }

    /**
     * Returns the current item on hand, null if no items
     * 
     * @return current item, null if none
     */
    public Item getCurrentItem(){
        if(iventory.isEmpty() || currentItemIndex == -1) return null;
        
        return iventory.get(currentItemIndex);
    }

    /**
     * Switches the item on hand to the next item in the inventory
     */
    public void switchToNextItem(){
        if(iventory.isEmpty()) return;

        currentItemIndex++;
        if(currentItemIndex >= iventory.size())
            currentItemIndex = 0; // go back to the first item
    }

    /**
     * Switches the item on hand to the previous item in the inventory
     */
    public void switchToPreviousItem(){
        if(iventory.isEmpty()) return;

        currentItemIndex--;
        if(currentItemIndex < 0)
            currentItemIndex = iventory.size() - 1; // go to the last item in list
    }

    /**
     * Use the item current on hand and decremnt its quantity. If item qty drops
     * to 0 then it is removed from the inventory and current item on hand becomes
     * N/A
     */
    public void useCurrentItem(){
        Item item = getCurrentItem();

        if(item == null) return;

        if(item.getName().equalsIgnoreCase("Noppo Bread"))
            heal((float) 0.5); // the possible item we can get rn is only noppo bread for mco1
        
        item.decrementQty();
        if(item.getQuantity() == 0){
            iventory.remove(item);
            currentItemIndex = -1; // players has to [ ] to have smth on hand
        }
    }

    /**
     * Returns Yohane's entire inventory
     * 
     * @return list of items in inventory
     */
    public ArrayList<Item> getIventory() {
        return iventory;
    }
}
