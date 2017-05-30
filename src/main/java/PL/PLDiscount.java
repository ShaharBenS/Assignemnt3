package PL;

import SharedClasses.Discount;

import java.util.Scanner;

import BL.SupplierBL;

public class PLDiscount {

    SupplierBL bl;
    Scanner sc;

    public PLDiscount(SupplierBL bl, Scanner sc) {
        this.bl = bl;
        this.sc = sc;
    }

    ///////////////// DISCOUNTS ///////////////////////////////////////////////////
    private void printOptions4() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new discount");
        System.out.println("2- Update discount");
        System.out.println("3- Get item's discount");
        System.out.println("4- Get item discounts");
        System.out.println("5- Delete item's discount");
        System.out.println("6- Back");
    }

    public void case4() {
        int option;
        printOptions4();
        try {
            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    //add
                    case41();
                    break;
                case 2:
                    //update discount
                    case42();
                    break;
                case 3:
                    //get
                    case43();
                    break;
                case 4:
                    case44();
                    break;
                case 5:
                    //delete
                    case45();
                    break;
                case 6:
                    //back
                    return;

                default:
                    System.out.println("ERROR! wrong operation");
                    case4();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);

            System.out.println("ERROR! not a number");
        }
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

    private int getItemID() {
        int itemID = 0;
        System.out.println("Please enter item ID");
        try {
            itemID = Integer.parseInt(sc.nextLine());
            if (itemID <= 0) {
                System.out.println("ERROR! invalid ID");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid ID");
            return 0;
        }

        boolean Exist = bl.checkExistItemID(itemID);
        if (Exist)
            return itemID;
        else {
            System.out.println("ERROR! invalid item ID");
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

    private int getDiscount() {
        int dicount = 0;
        System.out.println("Please enter Discout");
        try {
            dicount = Integer.parseInt(sc.nextLine());
            if (dicount < 1 || dicount > 100) {
                System.out.println("ERROR! invalid Discount");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR! invalid Discount");
            return 0;
        }
        return dicount;
    }

    //add discount
    public void case41() {
        int supID = getSupID();
        int itemID = 0;
        int Quantity = -1;
        int Discout = 0;
        if (supID != 0) {
            itemID = getItemID();
            if (itemID != 0 && bl.checkItemExist(supID, itemID)) {
                Quantity = getQuantity();
                if (Quantity != -1) {
                    if (!bl.checkExistDis(supID, itemID, Quantity)) {
                        Discout = getDiscount();
                        if (Discout != 0) {
                            bl.addDiscount(supID, itemID, Quantity, Discout);
                            System.out.println("Discount has been added successfully");
                        } else {
                            System.out.println("ERROR");
                            case41();
                        }
                    } else {
                        System.out.println("ERROR");
                        case41();
                    }
                } else {
                    System.out.println("ERROR");
                    case41();
                }
            } else {
                System.out.println("ERROR");
                case41();
            }
        } else {
            System.out.println("ERROR");
            case41();
        }
    }

    //update
    public void case42() {
        int supID = getSupID();
        int itemID = 0;
        int quantity = 0;
        int discount = 0;
        if (supID != 0) {
            itemID = getItemID();
            if (itemID != 0 && bl.checkItemExist(supID, itemID)) {
                quantity = getQuantity();
                if (quantity != -1) {
                    if (bl.checkExistDis(supID, itemID, quantity)) {
                        discount = getDiscount();
                        if (discount != 0) {
                            bl.setDiscount(supID, itemID,quantity, discount);
                            System.out.println("updated successfully");
                        } else {
                            System.out.println("ERROR");
                            case42();
                        }
                    } else {
                        System.out.println("ERROR");
                        case42();
                    }
                } else {
                    System.out.println("ERROR");
                    case42();
                }
            } else {
                System.out.println("ERROR");
                case42();
            }
        } else {
            System.out.println("ERROR");
            case42();
        }
    }

    //get discount details.
    public void case43() {
        int supID = getSupID();
        int itemID = 0;
        int quantity = 0;
        if (supID != 0) {
            itemID = getItemID();
            if (itemID != 0) {
                quantity = getQuantity();
                if (quantity != -1) {
                    Discount dis= bl.getDiscount(supID, itemID, quantity);
                    String ans ="";
                    ans+="Supplier ID: " + dis.getSupplierID()+ "\n";
                    ans+= "Item ID: " + dis.getItemID() + "\n";
                    ans += "Quantity: " + dis.getQuantity() + "\n";
                    ans += "Discount percentage: " + dis.getDiscountPercentage() + "\n";
                    System.out.println(ans);
                } else case43();
            } else case43();
        } else
            case43();
    }

    public void case44() {
        int supID = getSupID();
        int itemID = 0;
        int quantity = 0;
        if (supID != 0) {
            itemID = getItemID();
            if (itemID != 0) {
                System.out.println(bl.getDiscounts(supID, itemID));

            }
            else case44();
        }
        else
            case44();
    }


    //delete discount.
    public void case45() {
        int supID = getSupID();
        int itemID = 0;
        int quantity = 0;
        if (supID != 0) {
            itemID = getItemID();
            if (itemID != 0) {
                quantity = getQuantity();
                if (quantity != -1) {
                    if (bl.removeDiscount(supID, itemID, quantity))
                        System.out.println("deleted successfully");
                    else {
                        System.out.println("something went wrong");
                        case4();
                    }
                } else case45();
            } else case45();
        } else
            case45();
    }

}
