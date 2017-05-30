package PL;

import SharedClasses.Supplier;

import java.util.Scanner;

import javax.swing.SingleSelectionModel;

import BL.SupplierBL;

public class PLSuppliers {
    SupplierBL bl;
    Scanner sc;

    public PLSuppliers(SupplierBL bl, Scanner sc) {
        this.bl = bl;
        this.sc = sc;
    }

    /////////////////// SUPPLIERS ///////////////////////////////////////////////////
    public void case1() {
        System.out.println("Please choose an option:");
        System.out.println("1- Add new supplier");
        System.out.println("2- Update exist supplier");
        System.out.println("3- Get supplier's details");
        System.out.println("4- Get supplier's contacts");
        System.out.println("5- Delete exist supplier");
        System.out.println("6- Back");


        int option;

        try {

            option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    case11();
                    break;
                case 2:
                    case12();
                    break;
                case 3:
                    case13();
                    break;
                case 4:
                    case14();
                    break;
                case 5:
                    case15();
                    break;
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

    public void case11() {

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

        if (!Exists) {
            int BankNum, BranchNum, AccountNum;
            String Name, Payment, DeliveryMethod, SupplyTime, address;
            try {
                System.out.println("Please enter supplier's name");
                Name = sc.nextLine();

                System.out.println("Please enter supplier's bank number");
                BankNum = Integer.parseInt(sc.nextLine());

                System.out.println("Please enter supplier's branch number");
                BranchNum = Integer.parseInt(sc.nextLine());

                System.out.println("Please enter supplier's account number");
                AccountNum = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("invalid input");
                return;
            }
            System.out.println("Please enter supplier's payment manner: cash or check");
            Payment = sc.nextLine();
            if (!Payment.equals("check") && !Payment.equals("cash")) {
                System.out.println("invalid payment manner");
                return;
            }
            System.out.println("Please enter supplier's address");
            address = sc.nextLine();
            

            System.out.println("Please enter supplier's delivery method: with delivery or without delivery");
            DeliveryMethod = sc.nextLine();
            if (!DeliveryMethod.equals("with delivery") && !DeliveryMethod.equals("without delivery")) {
                System.out.println("invalid delivery method");
                return;
            }
            if (DeliveryMethod.equals("with delivery")) {
                System.out.println("Please enter supplier's supply time:");
                SupplyTime = sc.nextLine();
                String []days = SupplyTime.split("\\s");
                for(int i=0; i<days.length;i++){
                    if (!days[i].equals("Sunday") && !days[i].equals("Monday") && !days[i].equals("Tuesday") && !days[i].equals("Wednesday") && !days[i].equals("Thursday") && !days[i].equals("Friday") && !days[i].equals("Saturday")) {
                        System.out.println("invalid supply time");
                        return;
                   }
                }
               if( bl.addSupplier(ID,Name, BankNum, BranchNum, AccountNum, Payment, DeliveryMethod, SupplyTime, address))
                   System.out.println("Supplier has been added");
                else System.out.println("something went wrong");
            } else if(bl.addSupplier(ID,Name, BankNum, BranchNum, AccountNum, Payment, DeliveryMethod, "NULL",address))
                    System.out.println("Supplier has been added");
                    else System.out.println("something went wrong");
        } else {
            System.out.println("Supplier ID is already exist");
            case1();
        }
    }


    public void case12() {
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

                System.out.println("Please choose an option:");
                System.out.println("1 - update supplier's id");
                System.out.println("2 - update supplier's name");
                System.out.println("3 - update supplier's bank number");
                System.out.println("4 - update supplier's branch number");
                System.out.println("5 - update supplier's account number");
                System.out.println("6 - update supplier's payment manner");
                System.out.println("7 - update supplier's delivery method");
                System.out.println("8 - update supplier's supply time");
                System.out.println("9 - update supplier's address");
                System.out.println("10 - back");

                try {
                    choose = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("ERROR! wrong operation");
                }

                switch (choose) {
                    case 1:
                        System.out.println("Please enter new supplier's id");
                        int newID;
                        try {
                            newID = Integer.parseInt(sc.nextLine());
                            if (newID < 0) {
                                System.out.println("ERROR! invalid id");
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("ERROR! wrong id");
                            break;
                        }
                        if (!bl.checkSupExist(newID)) {
                            ERROR = !bl.setSupplier(ID, newID, 1);
                            if (!ERROR)
                                ID = newID;
                        } else {
                            System.out.println("ERROR! invalid/exist supplier id.");
                            break;
                        }
                        break;
                    case 2:
                        System.out.println("Please enter supplier's new name");
                        String name = sc.nextLine();
                        ERROR = !bl.setSupplier(ID, name, 2);
                        break;
                    case 3:
                        System.out.println("Please enter supplier's new bank number");
                        int BankNum;
                        try {
                            BankNum = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("invalid bank number");
                            return;
                        }
                        ERROR = !bl.setSupplier(ID, BankNum, 3);
                        break;

                    case 4:
                        System.out.println("Please enter supplier's new branch number");
                        int BranchNum;
                        try {
                            BranchNum = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("invalid branch number");
                            return;
                        }
                        ERROR = !bl.setSupplier(ID, BranchNum, 4);
                        break;

                    case 5:
                        System.out.println("Please enter supplier's new account number");
                        int AccountNum;
                        try {
                            AccountNum = Integer.parseInt(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("invalid account number");
                            return;
                        }
                        ERROR = !bl.setSupplier(ID, AccountNum, 5);
                        break;


                    case 6:
                        System.out.println("Please enter supplier's payment manner, one of the options: cash or check");
                        String payment = sc.nextLine();
                        if (!payment.equals("cash") && !payment.equals("check")) {
                            ERROR = true;
                            System.out.println("ERROR! invalid payment manner");
                            break;
                        }
                        ERROR = !bl.setSupplier(ID, payment, 6);
                        break;
                    case 7:
                        System.out.println("Please enter supplier's delivery method, one of the options: with delivery or without delivery");
                        String Delivery = sc.nextLine();
                        if (!Delivery.equals("with delivery") && !Delivery.equals("without delivery")) {
                            ERROR = true;
                            System.out.println("ERROR! invalid delivery method");
                            break;
                        }

                        if (Delivery.equals("with delivery")) {
                            System.out.println("Please enter supplier's supply time, one of the days");
                            String SupplyTime1 = sc.nextLine();
                            if (!SupplyTime1.equals("Sunday") && !SupplyTime1.equals("Monday") && !SupplyTime1.equals("Tuesday") && !SupplyTime1.equals("Wednesday") && !SupplyTime1.equals("Thursday") && !SupplyTime1.equals("Friday") && !SupplyTime1.equals("Saturday")) {
                                ERROR = true;
                                System.out.println("ERROR! invalid supply time");
                                break;
                            }
                            ERROR = !bl.setSupplier(ID, SupplyTime1, 8);
                        } else {
                            ERROR = !bl.setSupplier(ID, "NULL", 8);
                        }
                        ERROR = !bl.setSupplier(ID, Delivery, 7) && ERROR;
                        break;
                    case 8:
                        if (bl.getDeliveryMethod(ID).equals("without delivery")) {
                            System.out.println("please change the supplier's delivery method first");
                            continue;
                        }
                        System.out.println("Please enter supplier's supply time, one of the days");
                        String SupplyTime = sc.nextLine();
                        if (!SupplyTime.equals("Sunday") && !SupplyTime.equals("Monday") && !SupplyTime.equals("Tuesday") && !SupplyTime.equals("Wednesday") && !SupplyTime.equals("Thursday") && !SupplyTime.equals("Friday") && !SupplyTime.equals("Saturday")) {
                            ERROR = true;
                            System.out.println("ERROR! invalid supply time");
                            break;
                        }
                        ERROR = !bl.setSupplier(ID, SupplyTime, 8);
                        break;
                    case 9:
                    	System.out.println("Please enter supplier's address");
                        String address = sc.nextLine();
                        ERROR = !bl.setSupplier(ID, address, 9);
                        break;
                    case 10:
                        return;
                    default:
                        System.out.println("ERROR! invalid operation");
                        break;
                }
                if (ERROR) {
                    System.out.println("Something went wrong");
                } else {
                    System.out.println("Update completed");
                }
            }
        } else {
            System.out.println("ERROR! Supplier ID not exists");
        }
    }


    public void case13() {
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
           Supplier sup =bl.getSupplier(ID);
            String ans ="";
            ans+="Supplier ID: " + sup.getId() + "\n";
            ans+= "Name: " + sup.getName() + "\n";
            ans += "Bank number: " + sup.getBankNum() + "\n";
            ans += "Branch number: " + sup.getBranchNum() + "\n";
            ans += "Account number: " + sup.getAccountNum() + "\n";
            ans += "Payment: " + sup.getPayment() + "\n";
            ans += "Delivery Method: " + sup.getDeliveryMethod() + "\n";
            ans += "Supply Time: " + sup.getSupplyTime() + "\n";
            ans+= "Address: " + sup.getAddress();
            System.out.println(ans);
        } else {
            System.out.println("ERROR! invalid supplier ID");
            return;
        }
    }


    public void case14() {
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
            String con=bl.getSupplierContact(ID);
            if(con.length()==0){
                System.out.println("This supplier has no contacts");
            }
            else {
                System.out.println(con);
            }
        } else {
            System.out.println("ERROR! invalid supplier ID");
        }
    }


    public void case15() {
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
            if (bl.removeSupplier(ID))
                System.out.println("supplier has been removed");
            else System.out.println("something went wrong");
        } else {
            System.out.println("ERROR! invalid supplier ID");
        }
    }
}
