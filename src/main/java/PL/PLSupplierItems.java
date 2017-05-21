package PL;

import SharedClasses.SupplierItem;

import java.util.Scanner;

import BL.SupplierBL;

public class PLSupplierItems {

    SupplierBL bl;
    Scanner sc;

    public PLSupplierItems(SupplierBL bl, Scanner sc) {
        this.bl = bl;
        this.sc = sc;
    }


    /////////////////// SUPPLIER ITEMS ///////////////////////////////////////////////////
    public void case2() {

        System.out.println("Please choose an option:");
        System.out.println("1- Add new supplier's item");
        System.out.println("2- Update exist supplier's item");
        System.out.println("3- Get supplier's item details");
        System.out.println("4- Get supplier's items");
        System.out.println("5- Delete exist supplier's item");
        System.out.println("6- Back");


        int option;

        try {

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    case21();
                    break;
                case 2:
                    case22();
                    break;
                case 3:
                    case23();
                    break;
                case 4:
                    case24();
                    break;
                case 5:
                    case25();
                case 6:
                    return;
                default:
                    System.out.println("ERROR! wrong operation");
                    break;
            }
        } catch (Exception e) {
            System.out.println("ERROR! not a number");
        }
    }

    public void case21() {

        int ID;

        System.out.println("Please enter Supplier ID");

        try {
            ID = Integer.parseInt(sc.nextLine());
            if (ID < 0) {
                System.out.println("ERROR! invalid ID");
                return;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return;
        }

        boolean Exists = bl.checkSupExist(ID);

        if (Exists) {
            int ItemID, CatalogNumber;
            double Cost;
            try {
                System.out.println("Please enter item's id");
                ItemID = Integer.parseInt(sc.nextLine());

                boolean itemEx= bl.checkIfItemExistInSupItems(ItemID);

                if(!itemEx) {
                    System.out.println("Please enter the catalog number of the item");
                    CatalogNumber = Integer.parseInt(sc.nextLine());

                    System.out.println("Please enter item's price");
                    Cost = Double.parseDouble(sc.nextLine());
                }
                else{
                    System.out.println("ItemID is already exist, please delete supplier's item before.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("invalid input");
                return;
            }

            if(bl.addSupplierItem(ID, ItemID, CatalogNumber, Cost)) {
                System.out.println("Supplier's item has been added");
            }
            else{
                System.out.println("something went wrong");
            }
        } else {
            System.out.println("Supplier ID is not exist, please enter supplier first.");
            return;
        }
    }


    public void case22() {
        int ID;
        int choose = -1;
        boolean Continue = true;
        System.out.println("Please enter supplier's ID");

        try {
            ID = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return;
        }
        boolean Exists = bl.checkSupExist(ID);
        boolean ERROR = true;
        if (Exists) {
            while (Continue) {
                int itemId;
                System.out.println("Please enter item's ID");

                try {
                    itemId = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("ERROR! invalid ID");
                    return;
                }
                System.out.println("Please choose an option:");
                System.out.println("1 - update item's catalog number");
                System.out.println("2 - update item's price");
                System.out.println("3 - back");

                try {
                    choose = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("ERROR! wrong operation");
                }


                boolean itemExists = bl.checkItemExist(ID, itemId);
                if (itemExists) {
                    switch (choose) {
                        case 1:
                            System.out.println("Please enter item's new catalog number");
                            int catalogNum;
                            try {
                                catalogNum = Integer.parseInt(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("invalid catalog number");
                                return;
                            }
                            ERROR = !bl.setSupplierItem(ID, itemId, catalogNum, 1);
                            break;

                        case 2:
                            System.out.println("Please enter item's new price");
                            double price;
                            try {
                                price = Double.parseDouble(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println("invalid price");
                                return;
                            }
                            ERROR = !bl.setSupplierItem(ID, itemId, price, 2);
                            break;

                        case 3:
                            Continue = false;
                            return;
                        default:
                            System.out.println("ERROR! invalid operation");
                            break;
                    }
                    if (ERROR) {
                        System.out.println("Something went wrong");
                    } else {
                        System.out.println("Update completed");
                        Continue = false;
                    }
                } else {
                    System.out.println("ERROR! this Supplier ID has not this item ID");
                }
            }
        } else {
            System.out.println("ERROR! Supplier ID not exists");
        }
    }

    public void case23() {
        int ID;

        System.out.println("please enter supplier's id");

        try {
            ID = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid supplier ID");
            return;
        }

        int itemId;
        System.out.println("please enter item's id");

        try {
            itemId = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid supplier ID");
            return;
        }
        boolean Exists = bl.checkItemExist(ID, itemId);
        if (Exists) {
            SupplierItem supItem= bl.getSupplierItem(itemId, ID);
            String ans ="";
            ans+="Supplier ID: " + supItem.getSupplierID() + "\n";
            ans+= "Item ID: " + supItem.getItemID() + "\n";
            ans += "Catalog number: " + supItem.getCatalogNumber() + "\n";
            ans += "Cost: " + supItem.getCost() + "\n";
            System.out.println(ans);
        } else {
            System.out.println("ERROR! invalid supplier ID and item ID");
            return;
        }
    }


    public void case24() {
        int ID;

        System.out.println("please enter supplier's id");

        try {
            ID = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid supplier ID");
            return;
        }

        boolean Exists = bl.checkSupExist(ID);
        if (Exists) {
            System.out.println(bl.getSupplierItems(ID));
        } else {
            System.out.println("ERROR! invalid supplier ID and item ID");
            return;
        }
    }


    public void case25() {
        int ID;

        System.out.println("please enter supplier's id");

        try {
            ID = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid supplier ID");
            return;
        }

        int itemId;
        System.out.println("please enter item's id");

        try {
            itemId = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("ERROR! invalid item ID");
            return;
        }
        boolean Exists = bl.checkItemExist(ID, itemId);
        if (Exists) {
            if (bl.removeSupplierItem(itemId, ID))
                System.out.println("Deleted successfully");
            else System.out.println("something went wrong");
        } else {
            System.out.println("ERROR! invalid supplier ID and item ID");
        }
    }


}
