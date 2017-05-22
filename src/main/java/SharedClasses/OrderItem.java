package SharedClasses;

/**
 * Created by rotem on 07/04/2017.
 */
public class OrderItem {
    private int orderID;
    private int ItemID;
    private int quantity;
    private double finalCost;


    public OrderItem(int orderID,int ItemID, int quantity, double finalCost){
        this.orderID = orderID;
        this.ItemID = ItemID;
        this.quantity = quantity;
        this.finalCost = finalCost;
    }

    @Override
    public String toString() {
        String ans="";
        ans+= "*** ORDER ITEM ***\n";
        ans+= "Item ID: "+ItemID+"\n";
        ans+= "Quantity: "+quantity+"\n";
        ans+= "Final Cost: "+finalCost+"\n";
        ans+= "*** ORDER ITEM ***\n";
        return ans;
    }

    public int getOrderID(){return orderID;}

    public void setOrdetID(int orderID){this.orderID=orderID;}


    public int getItemID(){return ItemID;}

    public void setItemID(int itemID){this.ItemID = itemID;}

    public int getQuantity(){return quantity;}

    public void setQuantity(int quantity){this.quantity = quantity;}


    public double getFinalCost(){return finalCost;}

    public void setFinalCost(double finalCost){this.finalCost = finalCost;}

}
