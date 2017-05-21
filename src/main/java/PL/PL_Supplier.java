package PL;

import java.util.Scanner;

import BL.SupplierBL;


public class PL_Supplier {
    public final int SLEEP_TIME = 1000; // In seconds
    SupplierBL bl;
    private Scanner sc = new Scanner(System.in);

    public PL_Supplier(SupplierBL bl) {
        this.bl = bl;
    }

    public void start() {
        mainMenu();
    }

    /////////////////// MAIN MENU ///////////////////////////////////////////////////
    public void mainMenu() {
        boolean Continue = true;
        int option;
        while (Continue) {
            printOptions();
            try {
                option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        PLSuppliers pls = new PLSuppliers(bl, sc);
                        pls.case1();
                        break;
                    case 2:
                        PLSupplierItems plsa = new PLSupplierItems(bl, sc);
                        plsa.case2();
                        break;
                    case 3:
                        //Contacts.
                        PLContacts plc = new PLContacts(bl, sc);
                        plc.case3();
                        break;
                    case 4:
                        //Discounts.
                        PLDiscount pld = new PLDiscount(bl, sc);
                        pld.case4();
                        break;
                    case 5:
                        Continue = false;
                        return;
                    default:
                        System.out.println("ERROR! wrong operation");
                        mainMenu();
                        break;
                }
            } catch (Exception e) {
                System.out.println("ERROR! not a number");
            }
        }
    }



    private void printOptions() {
        System.out.println("Please choose a topic to work on:");
        System.out.println("1- Suppliers");
        System.out.println("2- Supplier's items");
        System.out.println("3- Contacts");
        System.out.println("4- Discounts");
        System.out.println("5- Back");

    }


}
    
