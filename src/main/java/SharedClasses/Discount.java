package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Discount {
    private int supplierID;
    private int itemID;
    private int quantity;
    private int discountPercentage;

    public Discount(int supplierID, int itemID, int quantity, int discountPercentage) {
        this.supplierID = supplierID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
