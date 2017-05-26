package SharedClasses;

/**
 * Created by Omri on 19-Apr-17.
 */
public class Quantity {

    private int itemID;
    private int ShopID;
    private String location;
    private int defects;
    private int warehouse;
    private int minimum;
    private int store;
    private int amount_to_order;
    private int current;


    public Quantity(int itemID,int shopID, String location, int defects, int warehouse, int minimum, int store, int amount_to_order)
    {
        this.itemID = itemID;
        this.ShopID = shopID;
        this.location = location;
        this.defects = defects;
        this.warehouse = warehouse;
        this.minimum = minimum;
        this.store = store;
        this.amount_to_order = amount_to_order;
        UpdateCurrent();
    }

    private void UpdateCurrent() { this.current = store+warehouse+defects; }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDefects() {
        return defects;
    }

    public void setDefects(int defects) {
        this.defects = defects;
        UpdateCurrent();
    }

    public int getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(int warehouse) {
        this.warehouse = warehouse;
        UpdateCurrent();
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
        UpdateCurrent();
    }

    public int getAmount_to_order() {
        return amount_to_order;
    }

    public void setAmount_to_order(int amount_to_order) {
        this.amount_to_order = amount_to_order;
    }

    public int getCurrent() {
        return current;
    }
    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int shopID) {
        ShopID = shopID;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
    public String toStringStock()
    {
        String str = "";

        str+="----- QUANTITY -----" + "\n";
        str+="ITEM ID: " + itemID + "\n";
        str+="CURRENT AMOUNT: " + current + "\n";
        str+="STORE AMOUNT: " + store + "\n";
        str+="WAREHOUSE AMOUNT: " + warehouse + "\n";
        str+="DEFECT AMOUNT: " + defects + "\n";
        str+="MINIMAL AMOUNT: " + minimum + "\n";
        str+="----- QUANTITY -----" + "\n";

        return str;
    }

    @Override
    public String toString() {
        String str = "";

        str += "----- QUANTITY -----" + "\n";
        str += "LOCATION: " + location + "\n";
        str += "CURRENT AMOUNT: " + current + "\n";
        str += "STORE AMOUNT: " + store + "\n";
        str += "WAREHOUSE AMOUNT: " + warehouse + "\n";
        str += "DEFECT AMOUNT: " + defects + "\n";
        str += "MINIMAL AMOUNT: " + minimum + "\n";
        str += "AMOUNT TO ORDER: " + amount_to_order + "\n";
        str += "----- QUANTITY -----" + "\n";

        return str;
    }

    public String toStringDefectsPart2()
    {
        String str ="";

        str += "LOCATION: " + location + "\n";
        str += "CURRENT AMOUNT: " + current + "\n";
        str += "STORE AMOUNT: " + store + "\n";
        str += "WAREHOUSE AMOUNT: " + warehouse + "\n";
        str += "DEFECT AMOUNT: " + defects + "\n";
        str += "----- DEFECTS -----\n";

        return str;
    }
}
