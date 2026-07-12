/**
 * Represents an item that Yohane can obtain or purchase.
 * 
 * Item
 */

public class Item {
    private String name;
    private int price;
    private int quantity;

    /**
     * Creates an Item object you can get from the treasure tile.
     * 
     * @param name name of item
     */
    public Item(String name){
        this.name = name;
        this.quantity = 1; // its assigned to 1 upon instatiation bc when u get from the treasure u get 1 noppo bread
    }

    /**
     * Constructs an Item with a name and price. This is intended for items available
     * for purchase in the shop. The item starts with a quantity of 1.
     * 
     * @param name name of item
     * @param price price of item
     */
    public Item(String name, int price){
        this.name = name;
        this.price = price;
        this.quantity = 1; // its 1 bc when u buy from the shop u get 1 of said item
    }

    /**
     * Increments the quantity of the item by 1
     */
    public void incrementQty(){
        this.quantity++;
    }

    /**
     * Decrements the quanityt of the item by 1
     */
    public void decrementQty(){
        this.quantity--;
    }

    /**
     * Returns the name of the item
     * 
     * @return name of item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item
     * 
     * @return price of item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the currenty quantity of held item
     * 
     * @return quantity of item
     */
    public int getQuantity() {
        return quantity;
    }
}
