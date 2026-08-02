import java.util.ArrayList;

public class Shop {
    private ArrayList<Item> items;

    /**
     * Creates Hanamaru's shop and loads the available items.
     */
    public Shop() {
        items = new ArrayList<>();

       // add all items
        items.add(new Item("Tears of a fallen angel", 30));
        items.add(new Item("Noppoo Bread", 100));
        

       
    }

}
