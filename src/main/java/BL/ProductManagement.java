package BL;

import DAL.Items;
import DAL.OrdersItems;
import DAL.Prices;
import DAL.Quantities;
import SharedClasses.*;
import javafx.util.Pair;

public class ProductManagement {
    private Items ITEMS;
    private Prices PRICES;
    private Quantities QUANTITIES;
    private SupplierBL SBL;

    public ProductManagement(Items items, Prices prices, Quantities quantities ,SupplierBL sbl)
    {
        this.ITEMS = items;
        this.PRICES = prices;
        this.QUANTITIES = quantities;
        this.SBL = sbl;
    }

    // ADD NEW item TO DATABASE
     // FORMAT: [0:ID] [1:LOCATION] [2:MANUFACTURE] [3:MINIMAL AMOUNT] [4:CATEGORY CODE] [5:NAME] [6:SELL PRICE] [7:WEIGHT] [8:DESCRIPTION]
    public boolean addItem(String pLine) {
        String[] iParts = pLine.split("\\s+");
        if (iParts.length != 9) return false;
        Quantity quantity;
        Item item;
        Price price;
        try {
            /*0*/
            int id = Integer.parseInt(iParts[0]);
            if (iParts[0].length() != 6) return false;
            /*3*/
            int minimal = Integer.parseInt(iParts[3]);
            /*4*/
            int cCode = Integer.parseInt(iParts[4]);
            if (iParts[4].length() != 3) return false;
            double sell = Double.parseDouble(iParts[6]);
            double weight = Double.parseDouble(iParts[7]);
            item = new Item(id, iParts[5], cCode, iParts[2], weight,iParts[8]);
            quantity = new Quantity(id, BL.shopID, iParts[1],0,0,minimal,0,minimal*3);
            price = new Price(id, sell, 0, null,null);

        } catch (Exception e) { return false; }
        return (ITEMS.addItem(item) && PRICES.addItemPrice(price) && QUANTITIES.addItemQuantity(quantity));
    }

    //RETURN item FROM DATABASE IF EXISTS, ELSE RETURN NULL
    public Item getItem(int id) { return ITEMS.getItem(id); }

    //[ID] [NEW ID]
    public boolean updateItemId(String ids) {
        String[] sid = ids.split("\\s+");
        if (sid.length != 2) return false;
        try {
            int id = Integer.parseInt(sid[0]);
            int newId = Integer.parseInt(sid[1]);
            return ITEMS.setID(id, newId);
        } catch (Exception e) { return false; }
    }


    public boolean updateItemLocation(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            return QUANTITIES.updateLocation(id, prop[1]);
        } catch (Exception e) { return false; }
    }

    public boolean updateItemManufacture(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            return ITEMS.setManufacture(id, prop[1]);
        } catch (Exception e) { return false; }
    }

    public boolean updateItemAmountInWarehouse(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int newAmount = Integer.parseInt(prop[1]);
            boolean ans = QUANTITIES.updateWarehouse(id, newAmount);
            if(ans) checkIfNeedToOrder(id);
            return ans;
        } catch (Exception e) { return false; }
    }


    //NOTE : THIS FUNCTION TAKE AMOUNT FROM WAREHOUSE TO STORE !
    public boolean updateItemAmountInStore(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int postAmount = Integer.parseInt(prop[1]);
            Quantity itemQuantity = QUANTITIES.getQuantity(id,BL.shopID);
            int store = itemQuantity.getStore();
            int warehouse = itemQuantity.getWarehouse();
            if(itemQuantity == null) return false;

            //case: remove from store
            if (store > postAmount) {
                return QUANTITIES.updateStore(id, postAmount);
            }
            //case: move from warehouse to store
            else {
                //case: not enough items in warehouse
                if (warehouse - postAmount + store < 0) return false;

                boolean ans = updateItemAmountInWarehouse("" + id + " " + (warehouse - postAmount + store));
                if (!ans) return false;
                ans = QUANTITIES.updateStore(id, postAmount);
                return ans;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //NOTE: THIS FUNCTION REMOVE AMOUNT FROM THE STORE AS WELL !
    public boolean updateItemDefectAmount(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int postAmount = Integer.parseInt(prop[1]);
            Quantity itemQuantity = QUANTITIES.getQuantity(id,BL.shopID);
            int defects = itemQuantity.getDefects();
            int store = itemQuantity.getStore();
            if(itemQuantity == null) return false;

            //case: remove from defects
            if (defects > postAmount) {
                return QUANTITIES.updateDefects(id, postAmount);
            }
            //case: move from store to defects
            else {
                //case: not enough items in store
                if (store - postAmount + defects < 0) return false;

                boolean ans = updateItemAmountInStore("" + id + " " + (store - postAmount + defects));
                if (!ans) return false;
                ans = QUANTITIES.updateDefects(id, postAmount);
                return ans;
            }
        } catch (Exception e) {
            return false;
        }
    }


    //NOTE: THIS FUNCTION REMOVE AMOUNT FROM THE STORE AS WELL !
    public boolean addDefects(String line, String orderID) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int addAmount = Integer.parseInt(prop[1]);
            Quantity itemQuantity = QUANTITIES.getQuantity(id,BL.shopID);
            int defects = itemQuantity.getDefects();
            int warehouse = itemQuantity.getWarehouse();
            if(itemQuantity == null) return false;
            int orderID_int = Integer.parseInt(orderID);
            int orderAmount = SBL.getOrderItemQuantity(orderID_int, id);
            if(orderAmount-addAmount < 0)return  false;
            boolean ans = updateItemAmountInWarehouse("" + id + " " + (warehouse - addAmount));
            if (!ans) return false;
            ans = QUANTITIES.updateDefects(id, addAmount+defects);
            return ans;

        } catch (Exception e) {
            return false;
        }
    }


    public boolean updateOrderAmount(String line){
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int newOrderAmount = Integer.parseInt(prop[1]);
            return ITEMS.setOrderAmount(id, newOrderAmount);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateItemCategoryCode(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int newCategoryID = Integer.parseInt(prop[1]);
            return ITEMS.setCategory(id, newCategoryID);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateItemMinimalAmount(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            int newMinimal = Integer.parseInt(prop[1]);
            boolean ans = QUANTITIES.updateMinimum(id, newMinimal);
            checkIfNeedToOrder(id);
            return ans;
        } catch (Exception e) {
            return false;
        }
    }


    //TODO:shahar: its a random TODO - u need to make sure the thread will be active only for מחסנאי and manager


    public boolean updateWeight(String line)
    {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            double newWeight = Double.parseDouble(prop[1]);
            return ITEMS.setWeight(id, newWeight);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateDescription(String line)
    {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            int id = Integer.parseInt(prop[0]);
            return ITEMS.setDescription(id, prop[1]);
        } catch (Exception e) {
            return false;
        }
    }

    public String ItemReport(String line) {
        if (line.length() != 6) return "Invalid ID";
        try {
            int id = Integer.parseInt(line);
            Quantity quantity = QUANTITIES.getQuantity(id,BL.shopID);
            if (quantity==null) return "ID not found!\n";
            else return quantity.toStringStock();
        } catch (Exception e) {
            return "Invalid ID\n";
        }
    }

    public String[] getAllItems() {
       try {
           Pair[] K_ID_V_PRICE = SBL.getAllFinalPrices(BL.shopID); //KEY = ID | VALUE = PRICE (DOUBLE)
           if (K_ID_V_PRICE == null) return new String[]{"No Items\n"};

           String[] toStrings = new String[K_ID_V_PRICE.length];
           for (int i = 0; i < K_ID_V_PRICE.length; i++) // init @param: quantities
           {
               toStrings[i] = "------- FULL ITEM -------\n";
               toStrings[i] += (ITEMS.getItem((Integer) K_ID_V_PRICE[i].getKey())).toString();
               toStrings[i] += (QUANTITIES.getQuantity((Integer) K_ID_V_PRICE[i].getKey(),BL.shopID)).toString();
               toStrings[i] += (PRICES.getPrice((Integer) K_ID_V_PRICE[i].getKey())).toString();
               toStrings[i] += "Final Cost: " + (Double) K_ID_V_PRICE[i].getValue() + "\n";
               toStrings[i] += "------- FULL ITEM -------\n\n";
           }
           return toStrings;
       }
       catch (Exception e) { return new String[]{"No Items Were Found\n"}; }
    }

    public String[] getAllDefectProducts() {
        Quantity[] quantities = QUANTITIES.getAllDefectItems(BL.shopID);
        int size = quantities.length;
        if(size == 0) return new String[]{"No Defects were found !"};
        Item[] items = new Item[size];
        String[] toStringsDefects = new String[size];
        for(int i=0; i<size; i++)
        {
            items[i] = ITEMS.getItem(quantities[i].getItemID());
            toStringsDefects[i] = items[i].toStringDefectsPart1() + quantities[i].toStringDefectsPart2();
        }
        return toStringsDefects;
    }

    private void checkIfNeedToOrder(int id)
    {
        Quantity quantity = QUANTITIES.getQuantity(id,BL.shopID);
        if (quantity.getWarehouse() <= quantity.getMinimum()) {

            if(SBL.isItemOrdered(id, BL.shopID))
                return;

            Integer[] suppliersID = SBL.getSuppliersID(id);
            if(suppliersID==null) return;
            double first_cost = SBL.getCost(id,suppliersID[0]);
            first_cost = first_cost - (first_cost*SBL.getDiscountPercentage(id,suppliersID[0],quantity.getAmount_to_order())/100);
            Pair<Integer,Double> minimum = new Pair<>(suppliersID[0],first_cost);
            for(int i = 1; i<suppliersID.length; i++)
            {
                double cost = SBL.getCost(id, suppliersID[i]);
                cost = cost - (cost*SBL.getDiscountPercentage(id,suppliersID[i],quantity.getAmount_to_order())/100);
                if(cost < minimum.getValue())
                {
                    minimum = new Pair<>(suppliersID[i],cost);
                }
            }
            int supplierID = minimum.getKey();
            int oid = ITEMS.existOrder(supplierID, BL.shopID);
            int orderID;
            if(oid == 0) //there is no open order
                orderID = SBL.addOrder(supplierID,new Date(new java.util.Date()),0);
            else orderID = oid;
            if(!SBL.addOrderItem(orderID,id, quantity.getAmount_to_order())) return;
           Order order = SBL.getOrder(orderID);
            System.out.println(order.toString());
        }
    }


}