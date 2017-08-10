package udcode.com.mygrocerylist.Activities.model;

/**
 * Created by yudi on 8/9/2017.
 */

public class Grocery {

    private String grocery_name;
    private String quantity;
    private String dateAdded;
    private int id;
    public Grocery() {

    }
    public Grocery(String grocery_name, String quantity, String dateAdded, int id) {
        this.grocery_name = grocery_name;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.id = id;
    }
    public String getGrocery_name() {
        return grocery_name;
    }


    public void setGrocery_name(String grocery_name) {
        this.grocery_name = grocery_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
