package SharedClasses;

/**
 * Created by rotem on 06/04/2017.
 */

public class Order {

    //TODO:omri&shahr: add supp shop

    private int orderID;
    private int supplierID;
    private int shopID;
    private Date date;
    private String ContactID;
    private int Frequency;
    private OrderItem[] orderItems;

    @Override
    public String toString() // TODO : omri ; add ShopID to String
    {
        String ans ="";
        ans+="***** ORDER *****\n";
        ans+="ORDER ID: "+orderID+"\n";
        ans+="SUPPLIER ID: "+supplierID+"\n";
        ans+="Date: "+date.toString()+"\n";
        for(int i=0; i<orderItems.length; i++)
        {
            ans+=orderItems[i].toString();
        }
        ans+="***** ORDER *****\n";
        return  ans;
    }
    public String toStringWithoutOrderItems()
    {
        String ans ="";
        ans+="***** ORDER *****\n";
        ans+="ORDER ID: "+orderID+"\n";
        ans+="SUPPLIER ID: "+supplierID+"\n";
        ans+="Date: "+date.toString()+"\n";
        ans+="***** ORDER *****\n";
        return  ans;
    }
    public Order(int OrderID, int shopID, int supplierID, Date date, String ContactID, int frequency, OrderItem[] orderItems){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.shopID = shopID;
        this.ContactID= ContactID;
        this.Frequency = frequency;
        this.orderItems = orderItems;
        Frequency = 0;
    }
    public Order(int OrderID, int shopID, int supplierID, Date date, String ContactID){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.shopID = shopID;
        this.ContactID= ContactID;
        Frequency = 0;
    }
    public Order(int OrderID,int shopID,  int supplierID, Date date, String ContactID, int frequency){
        this.orderID = OrderID;
        this.supplierID = supplierID;
        this.date = new Date(date);
        this.shopID = shopID;
        this.ContactID= ContactID;
        this.Frequency = frequency;
    }
    public Order(Order ord, OrderItem[] orderItems){
    	this.orderID = ord.getOrderID();
        this.supplierID = ord.getSupplier();
        this.date = ord.getDate();
        this.ContactID= ord.getContactID();
        this.shopID = ord.getShopID();
        this.orderItems = orderItems;
    }

    public int getOrderID(){ return orderID;}

    public void setOrderID(int orderID){this.orderID=orderID;}

    public int getSupplier(){return supplierID;}

    public void setSupplierID(int supID){this.supplierID=supID;}

    public void setSupplierName(String supName){
    }

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

    public String getContactID(){ return ContactID;}

    public void setContactID(String contactID){this.ContactID=contactID;}

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }

    public int getFrequency() {
        return Frequency;
    }

    public void setFrequency(int frequency) {
        Frequency = frequency;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
