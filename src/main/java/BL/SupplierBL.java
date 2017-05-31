package BL;

import DAL.*;
import SharedClasses.*;
import SharedClasses.Date;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class SupplierBL {
    Contacts contacts;
    Discounts dis;
    Items item;
    SupplierItems si;
    Suppliers sup;
    Orders order;
    OrdersItems OI;
    Quantities quantities;
    BL bl;
    public static int OrderID;

    public SupplierBL(Contacts contacts, Discounts dis, Items item, SupplierItems si, Suppliers sup, Orders order, OrdersItems ordersItems,Quantities quantities,BL bl) {
        this.contacts = contacts;
        this.dis = dis;
        this.item = item;
        this.si = si;
        this.sup = sup;
        this.order = order;
        this.OI = ordersItems;
        this.quantities = quantities;
        this.bl = bl;
    }

    public void initOrderID()
    {
        int o_id = order.getHighestOrderID()+1;
        OrderID = o_id;
    }


    public boolean addContact(int SupplierId, String Id, String name, String phoneNum, String Email) {
        Contact con = new Contact(Id, SupplierId, name, phoneNum, Email);
        return contacts.addContact(con);
    }

    public boolean setContact(String id, Object change, int code) {// 1- name, 2- phoneNum, 3-Email
        switch (code) {
            case 1:
                String name = (String) change;
                return contacts.setName(id, name);
            case 2:
                String phoneNum = (String) change;
                return contacts.setPhoneNum(id, phoneNum);
            case 3:
                String Email = (String) change;
                return contacts.setEmail(id, Email);
            default:
                return false;
        }
    }

    public Contact getContact(String id) {
        return contacts.getContact(id);
    }

    public String getContactName(String id) {
        return contacts.getContactName(id);
    }

    public String getContactPhone(String id) {
        return contacts.getContactPhone(id);
    }

    public String getContactEmail(String id) {
        return contacts.getContactEmail(id);
    }

    public String getSupplierContact(int id) {
        return contacts.getSupplierContact(id);
    }

    public boolean removeContact(String id, int supId) {
        return contacts.removeContact(id, supId);
    }

    public boolean addSupplier(int ID, String name, int BankNum, int BranchNum, int AccountNum, String payment, String DeliveryMethod, String SupplyTime, String address) {
        Supplier supplier = new Supplier(ID, name, BankNum, BranchNum, AccountNum, payment, DeliveryMethod, SupplyTime, address);
        return sup.addSupplier(supplier);
    }

    public boolean setSupplier(int id, Object change, int code) {// 1- ID,2-name, 3- BankNum, 4- BranchNum, 5-AccountNum, 6-payment, 7-DeliveryMethod,8-SupplyTime, 9-address
        switch (code) {
            case 1:
                int newID = (Integer) change;
                return sup.setID(id, newID);
            case 2:
                String name = (String) change;
                return sup.setName(id, name);
            case 3:
                int BankNum = (Integer) change;
                return sup.setBankNum(id, BankNum);
            case 4:
                int BranchNum = (Integer) change;
                return sup.setBranchNum(id, BranchNum);
            case 5:
                int AccountNum = (Integer) change;
                return sup.setAccountNum(id, AccountNum);
            case 6:
                String payment = (String) change;
                return sup.setPayment(id, payment);
            case 7:
                String DeliveryMethod = (String) change;
                return sup.setDelivery(id, DeliveryMethod);
            case 8:
                String SupplyTime = (String) change;
                return sup.setSupplyTime(id, SupplyTime);
            case 9:
            	 String address = (String) change;
                 return sup.setAddress(id, address);
            default:
                return false;
        }
    }

    /* OMRI's FUNCTION */
    Pair[] getAllFinalPrices(int shopID)
    {
        return OI.getAllFinalPrices(shopID);
    }

    public Supplier getSupplier(int id) {
        return sup.getSupplier(id);
    }

    public String getDeliveryMethod(int id) {
        return sup.getDeliveryMethod(id);
    }

    public boolean removeSupplier(int id) {
        return sup.removeSupplier(id);
    }

    public boolean addSupplierItem(int SupplierID, int ItemID, int CatalogNumber, double Cost) {
        SupplierItem supItem = new SupplierItem(SupplierID, ItemID, CatalogNumber, Cost);
        return si.addSupplierItem(supItem);
    }

    public boolean setSupplierItem(int supId, int itemId, Object change, int code) {// 1- CatalogNumber, 2-Cost
        switch (code) {
            case 1:
                int CatalogNumber = (Integer) change;
                return si.setCatalogNumber(supId, itemId, CatalogNumber);
            case 2:
                double Cost = (Double) change;
                return si.setCost(supId, itemId, Cost);
            default:
                return false;
        }
    }

    public SupplierItem getSupplierItem(int itemId, int supId) {
        return si.getSupplierItem(itemId, supId);
    }

    public int getCatalogNumber(int itemId, int supId){return si.getCatalogNumber(itemId, supId);}

    public double getCost(int itemId, int supId){return si.getCost(itemId, supId);}

    public String getSupplierItems(int id) {
        return si.getSupplierItems(id);
    }

    public boolean removeSupplierItem(int id, int supId) {
        return si.removeSupplierItem(id, supId);
    }


    public boolean addDiscount(int SupplierID, int ItemID, int Quantity, int DiscountPercentage) {
        Discount discount = new Discount(SupplierID, ItemID, Quantity, DiscountPercentage);
        return dis.addDiscount(discount);
    }

    public boolean setDiscount(int supId, int itemId,int quantity,  int DiscountPercentage) {
        return dis.setDiscountPercentage(supId, itemId,quantity, DiscountPercentage);
    }

    public Discount getDiscount(int supplierId, int itemId, int quantity) {
        return dis.getDiscount(supplierId, itemId, quantity);
    }

    public int getDiscountPercentage(int itemId, int supId, int quantity){return  dis.getDiscountPer(supId, itemId, quantity);}

    public String getDiscounts(int supId, int ItemId) {
        return dis.getDiscounts(supId, ItemId);
    }

    public boolean removeDiscount(int supplirId, int itemId, int quantity) {
        return dis.removeDiscount(supplirId, itemId, quantity);
    }


    public boolean checkSupExist(int id) {

        return sup.ifExist(id);
    }

    public boolean checkItemExist(int supID, int itemId) {
        return si.ifExist(supID, itemId);
    }

    public boolean checkExistConID(String id) {
        return contacts.ifExist(id);
    }

    public boolean checkExistConSup(int supID, String conID) {
        return contacts.ifExist(supID, conID);
    }

    public boolean checkExistDis(int supID, int itemID, int Quantity) {
        return dis.ifExist(supID, itemID, Quantity);
    }

    public boolean checkExistItemID(int itemId)
    {
        return item.ifExist(itemId);
    }


    public int addOrder(int supplierId, Date date,int frequency){
        String conID=contacts.getContactID(supplierId);
        Order ord = new Order(OrderID++,BL.shopID,supplierId,date, conID,frequency);
        if(!order.addOrder(ord))
        {
            return -1;
        }

        Supplier supp = getSupplier(supplierId);
        if(supp.getDeliveryMethod().equals("with delivery"))
        {
            return ord.getOrderID();
        }
        try
        {
            Calendar c = Calendar.getInstance();
            int currDay  = c.get(Calendar.DAY_OF_WEEK);
            int dayInWeek = supp.getSupplyTimeInNumber();
            int daysToAdd = (dayInWeek - currDay);
            daysToAdd = (daysToAdd == 0) ? 7 : (daysToAdd < 0) ? (7-Math.abs(daysToAdd)) : daysToAdd;


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new java.util.Date());
            calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
            Date dateToAdd = new Date(calendar.getTime());


            bl.addTransport(dateToAdd.toStringWithBackslash(),
                    "08:00",""+ord.getOrderID());
        }
        catch (NituzException ignored){}

        return ord.getOrderID();
    }
    
    public boolean setOrder(int orderID,int itemID,  Object change, int code){//1-contact ID,2- item quantity
    	switch (code) {
        case 1:
            String conID = ((String) change);
            return order.setContactID(orderID, conID);
        case 2:
            int quantity = ((Integer) change).intValue();
            Order order = getOrder(OrderID);
            boolean ans = OI.setQuantity(orderID, itemID, quantity);
            try{
                bl.updateMission(""+orderID,""+order.getShopID(),""+itemID,""+order.getSupplierID(),""+quantity);
            }
            catch (NituzException ignored){}

            return ans;
        default:
            return false;
    	}
    }
    public Order getOrder(int orderID){
    	Order ord = order.getOrder(orderID);
        return  new Order(ord.getOrderID()
                ,ord.getShopID(),ord.getSupplierID(),new Date(ord.getDate()),ord.getContactID(),
                ord.getFrequency(),OI.getOrderItems(orderID));
    }
    
    public Order[] getOrderOfSup(int supID){
    	Order[] toReturn;
    	List<Order> orderSup=order.getOrderSup(supID);
        toReturn= new Order[orderSup.size()];
    	for(int i=0; i<orderSup.size();i++){
            toReturn[i]= orderSup.get(i);
            toReturn[i].setOrderItems(OI.getOrderItems(toReturn[i].getOrderID()));
    	}
    	return toReturn;
    }
    
    public boolean addOrderItem(int orderID, int itemID, int quantity)
    {
        Order OrderToAdd = order.getOrder(orderID);

        Supplier supp = getSupplier(OrderToAdd.getSupplierID());
        if(!supp.getDeliveryMethod().equals("with delivery"))
        {
            try{
                boolean ans = bl.addMission(""+orderID,""+quantity,""+OrderToAdd.getShopID(),
                        ""+OrderToAdd.getSupplierID(),""+itemID);
            }
            catch (NituzException ignored)
            {
                return false;
            }
        }

        int disco = dis.getDiscountPer(OrderToAdd.getSupplierID(), itemID, quantity);
        double cost = si.getCost(itemID,OrderToAdd.getSupplierID());
        double finalCost = cost;
        if(disco != 0)
         finalCost = cost - (disco*cost)/100;
        OrderItem orderItem = new OrderItem(orderID,itemID, quantity, finalCost);

        return OI.addOrderItem(orderItem);
    }
    public boolean setOrderArrivalDate(String line)
    {
        try{
            String [] splitted = line.split("\\s");
            int id = Integer.parseInt(splitted[0]);
            Date a_date = new Date(splitted[1]);
            if(order.getArrivalDate(id) == null)
            {
                Order ord = getOrder(id);
                java.sql.Date lastDate = ord.getDate().toSQLdate();
                java.sql.Date todayDate = new Date(new java.util.Date()).toSQLdate();
                long diff = todayDate.getTime() - lastDate.getTime();
                long days = TimeUnit.MILLISECONDS.toDays(diff);
                int frequency = order.getFrequency(id);

                if(frequency - days > 0)
                {
                    return false;
                }

                if(!order.setArrivalDate(id,a_date))
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
            OrderItem [] orderItems = OI.getOrderItems(id);
            for (OrderItem orderItem : orderItems) {
                int item_id = orderItem.getItemID();
                Quantity quantity = quantities.getQuantity(item_id,BL.shopID);
                if(quantity!=null)
                {
                    quantities.updateWarehouse(item_id,quantity.getWarehouse() + orderItem.getQuantity());
                }
            }
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public boolean removeOrder(int orderID)
    {
        return order.removeOrder(orderID);

    }
    
    public boolean removeOrderItem(int orderID, int itemID)
    {
        boolean ans = OI.removeOrderItem(orderID,itemID);
        Order order = getOrder(orderID);
        try{
            bl.updateMission(""+order.getOrderID(),""+order.getShopID(),
                    ""+itemID,""+order.getSupplierID(),""+0);
        }
        catch (NituzException ignored){}

        return ans;
    }

    public boolean checkIfItemExistInSupItems(int itemID , int suppID){
        return si.checkIfItemExist(itemID, suppID);
    }


    public Integer[] getSuppliersID(int itemID)
    {
        return si.getSuppliersID(itemID);
    }

    public boolean checkOrderExist(int orderID){
    	return order.checkOrderExist(orderID);
    }

    public boolean checkItemExistInOrder(int orderID, int itemID){return OI.checkItemExistInOrder(orderID, itemID);}

    public boolean isItemOrdered(int itemID, int shopID) { return OI.isItemOrdered(itemID, shopID); }

    public int getOrderItemQuantity(int orderID_int, int id){ return OI.getOrderItemQuantity(orderID_int,id);}

    public int getItemIDFromOrderAndCatalogNumber(int order, int catalogItem)
    {
        return si.getItemIDFromOrderAndCatalogNumber(this.order.getOrder(order).getSupplierID(),catalogItem);
    }

    public boolean checkExistCatalogNumber(int item) {
        return si.checkIfItemCatalogExists(item);
    }

    public Order[] getOrderWithoutDelivery()
    {
        Order [] orders = order.getOrderWithoutDelivery();
        for(Order o: orders){
            o.setOrderItems(OI.getOrderItems(o.getOrderID()));
        }
        return orders;
    }
}