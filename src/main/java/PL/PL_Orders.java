package PL;
import BL.SupplierBL;
import SharedClasses.Date;
import SharedClasses.Order;
import SharedClasses.OrderItem;
import SharedClasses.Supplier;

import java.util.Scanner;
/**
 * Created by Shahar on 19/04/17.
 */
public class PL_Orders
{
    public final int SLEEP_TIME = 1000; // In seconds
    SupplierBL bl;
    private Scanner sc = new Scanner(System.in);

    public PL_Orders(SupplierBL bl) {
        this.bl = bl;
    }

    private void printOptionsOrder() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new Order/item to order");
        System.out.println("2- Update order details");
        System.out.println("3- Get exist Order");
        System.out.println("4- Delete Details/Order");
        System.out.println("5- Get Supplier's Orders");
        System.out.println("6- Back");
    }

    private void printOptionsOrderUpdate() {
        System.out.println("Please choose an option:");
        System.out.println("1- Update contact's number");
        System.out.println("2- Update item quantity");
        System.out.println("3- Back");
    }

    private void printOptionsOrderDelete() {
        System.out.println("Please choose an option:");
        System.out.println("1- Delete exist order");
        System.out.println("2- Delete item from order");
        System.out.println("3- Back");
    }

    private void printOptionsOrderAdd() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new order");
        System.out.println("2- Add new item to order");
        System.out.println("3- Back");
    }

    private int getSupID() {
        int supID = 0;
        System.out.println("Please enter supplier ID");
        try {
            supID = Integer.parseInt(sc.nextLine());
            if (supID <= 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkSupExist(supID);
        if (Exist)
            return supID;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getOrderID() {
        int order = 0;
        System.out.println("Please enter Order Number");
        try {
            order = Integer.parseInt(sc.nextLine());
            if (order < 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkOrderExist(order);
        if (Exist) {
            return order;
        }
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getItem(){
        int item = 0;
        System.out.println("Please enter Catalog Number");
        try {
            item = Integer.parseInt(sc.nextLine());
            if (item < 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkExistCatalogNumber(item);
        if (Exist)
            return item;
        else {
            System.out.println("ERROR! supplier id isn't exist");
            return 0;
        }
    }

    private int getQuantity() {
        int quantity = 0;
        System.out.println("Please enter Quantity");
        try {
            quantity = Integer.parseInt(sc.nextLine());
            if (quantity < 0) {
                System.out.println("ERROR! invalid Quantity");
                return -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid Quantity");
            return -1;
        }
        return quantity;
    }
    private String getConID(int supID) {
        String conID = "";
        System.out.println("the contacts who works with supplier "+ supID + "are:");
        System.out.println(bl.getSupplierContact(supID));
        System.out.println("Please choose one contact ID");

        try {
            conID = sc.nextLine();
            if (!bl.checkExistConSup(supID, conID)) {
                System.out.println("ERROR! invalid Quantity");
                return "";
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid Quantity");
            return "";
        }
        return conID;
    }

    //OrderOptions
    public void orderCase() {
        int option;
        printOptionsOrder();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //add
                    case1();
                    break;
                case 2:
                    //update
                    case2();
                    break;
                case 3:
                    //get
                    case3();
                    break;
                case 4:
                    //delete
                    case4();
                    break;
                case 5:
                    //getSupplierOrders
                    case31();
                    return;
                case 6:
                    //back
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //add
    public void case1() {
        int option;
        printOptionsOrderAdd();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //new order
                    case11();
                    break;
                case 2:
                    //new item to order
                    case12();
                    break;
                case 3:
                    orderCase();
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }


    //addNewOrder
    public void case11() {
        System.out.println("Enter 1 to add a new Order\n" +
                            "Enter 2 to add a new Periodic Order [DAYS]");

        int choice = Integer.parseInt(sc.nextLine());
        int supID = getSupID();
        int frequency = 0;
        //check if the supplier exist
        if (supID != 0) {
            if(choice == 2)
            {
                System.out.println("Enter order frequency");
                frequency = Integer.parseInt(sc.nextLine());
            }
            int ans = bl.addOrder(supID, new Date(new java.util.Date()),frequency);
            if(ans == -1 ) {
                System.out.println("ERROR! something went wrong");
                case1();
            }
            else {
                System.out.println("Order has been added successfully");
                System.out.println("Order Number: " + ans);
                System.out.println("IF you want to add items to this order, please choose option number 2");
                case1();
            }
        }
    }

    //addItemToOrder
    public void case12() {
        int order = getOrderID();
        int catalogItem, quantity, ans;
        //check if the order exist
        if (order != 0) {
            catalogItem = getItem();

            int item = bl.getItemIDFromOrderAndCatalogNumber(order,catalogItem);

            if (item != 0) {
                quantity = getQuantity();
                if (quantity != -1) {
                    if(bl.addOrderItem(order, item, quantity))
                        System.out.println("Item has been added successfully");
                    else{
                        System.out.println("ERROR! something went wrong");
                        case1();
                    }
                } else {
                    System.out.println("ERROR! something went wrong");
                    case1();
                }
            } else {
                System.out.println("ERROR! something went wrong");
                case1();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case1();
        }
    }

    //updateOrder
    public void case2() {
        int option;
        printOptionsOrderUpdate();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //update contact id
                    case21();
                    break;
                case 2:
                    //update item quantity
                    case22();
                    break;
                case 3:
                    //back
                    orderCase();
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    orderCase();
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    //updateContactID
    public void case21() {
        int order = getOrderID();
        String conID;
        //check if the order exist
        if (order != 0) {
            conID = getConID(bl.getOrder(order).getSupplier());
            if (conID != "") {
                if(bl.setOrder(order,0,conID, 1))
                    System.out.println("contact has been changed successfuly");
                else{
                    System.out.println("ERROR! something went wrong");
                    case2();
                }
            } else {
                System.out.println("ERROR! something went wrong");
                case2();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case2();
        }

    }
    //updateItemQuantity
    public void case22() {
        int order = getOrderID();
        int catalogNumber, quantity;
        //check if the order exist
        if (order != 0) {
            catalogNumber = getItem();
            int itemNum = bl.getItemIDFromOrderAndCatalogNumber(order,catalogNumber);

            if (itemNum != 0 && bl.checkItemExistInOrder(order, itemNum)) {
                quantity = getQuantity();
                if (quantity != -1) {
                    bl.setOrder(order, itemNum, quantity, 2);
                    System.out.println("Item Quantity has been updated successfully");
                } else {
                    System.out.println("ERROR! something went wrong");
                    case2();
                }
            } else {
                System.out.println("ERROR! something went wrong");
                case2();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case2();
        }
    }


    //remove
    public void case4(){
        int option;
        printOptionsOrderDelete();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //delete exit order
                    case41();
                    break;
                case 2:
                    //delete item from order
                    case42();
                    break;
                case 3:
                    //back
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }

    }
    //removeOrder
    public void case41() {
        int order = getOrderID();
        //check if the order exist
        if (order != 0) {
            bl.removeOrder(order);
            System.out.println("Order has been removed successfully");
        } else {
            System.out.println("ERROR! something went wrong");
            case4();
        }
    }

    //removeOrderItem
    public void case42() {
        int order = getOrderID();
        int catalogNumber;
        //check if the order exist
        if (order != 0) {
            catalogNumber = getItem();
            int itemNum = bl.getItemIDFromOrderAndCatalogNumber(order,catalogNumber);

            if (itemNum != 0 && bl.checkItemExistInOrder(order, itemNum)) {
                bl.removeOrderItem(order, itemNum);
                System.out.println("Item from order has been removed successfully");

            } else {
                System.out.println("ERROR! something went wrong");
                case4();
            }
        } else {
            System.out.println("ERROR! something went wrong");
            case4();
        }
    }

    //getOrder
    public void case3() {
        int orderNum = getOrderID();
        //check if the order exist
        if (orderNum != 0) {

            Order order = bl.getOrder(orderNum);

            Supplier sup = bl.getSupplier(order.getSupplier());
            String ans ="";
            ans+="Order Number: " + order.getOrderID()+ "\n";
            ans+= "Supplier Name: " + sup.getName() + "\n";
            ans+= "Supplier ID: " + order.getSupplier() + "\n";
            ans += "Address: " + sup.getAddress() + "\n";
            ans += "Order Date: " + order.getDate() + "\n";
            ans += "Contact Number: " + bl.getContactPhone(order.getContactID()) + "\n";
            ans+= "Order frequency: " + order.getFrequency() + "\n";
            ans+= "Order items: " + "\n";
            OrderItem[] orderItems = order.getOrderItems();
            for(int i=0; i<orderItems.length; i++){
            	ans+= "Item ID: " + orderItems[i].getItemID() + "\n";
            	ans+= "Quantity: " + orderItems[i].getQuantity() + "\n";
            	ans+= "FinalCost: " + orderItems[i].getFinalCost() + "\n";
            	ans+= "\n";	
            }
            System.out.println(ans);
        } else {
            System.out.println("ERROR! something went wrong");

        }
    }

    //getSupplierOrders
    public void case31() {
        int supID = getSupID();
        //check if the supplier exist
        if (supID != 0) {
            System.out.println("Supplier "+ supID+ " orders are:");
            Order [] order  = bl.getOrderOfSup(supID);
            Supplier sup = bl.getSupplier(supID);
            String ans ="";
            for(int i=0; i<order.length; i++){
                ans+="Order Number: " + order[i].getOrderID()+ "\n";
                ans+= "Supplier Name: " + sup.getName() + "\n";
                ans+= "Supplier ID: " + order[i].getSupplier() + "\n";
                ans += "Address: " + sup.getAddress() + "\n";
                ans += "Order Date: " + order[i].getDate() + "\n";
                ans += "Contact Number: " + bl.getContactPhone(order[i].getContactID()) + "\n";
                ans+= "Order frequency: " + order[i].getFrequency() + "\n";
                ans+= "Order items: " + "\n";
                OrderItem[] orderItems = order[i].getOrderItems();

                for(int j=0; j<orderItems.length; j++){
                    ans+= "Item ID: " + orderItems[j].getItemID() + "\n";
                    ans+= "Quantity: " + orderItems[j].getQuantity() + "\n";
                    ans+= "FinalCost: " + orderItems[j].getFinalCost() + "\n";
                    ans+= "\n";
                }
            }
            System.out.println(ans);
        }
        else {
            System.out.println("ERROR! something went wrong");
            orderCase();
        }
    }

    void ordersWithoutDelivery()
    {
        Order [] orders = bl.getOrderWithoutDelivery();
        if(orders == null){return;}
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
