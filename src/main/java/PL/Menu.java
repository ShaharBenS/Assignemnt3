package PL;

import SharedClasses.NituzException;
import SharedClasses.MenuOP;
import BL.*;
import SharedClasses.Shift;

import javax.naming.SizeLimitExceededException;
import java.util.Scanner;

/**
 * Created by Omri on 06-Apr-17.
 */
public class Menu {

    private PL_Stock pl_stock;
    private PL_Supplier pl_sup;
    private PL_Orders pl_ord;
    private BL bl;
    private MenuOP[] menues;
    private Scanner scanner;

    public Menu(PL_Stock pl_stock, PL_Supplier pl_sup, PL_Orders pl_ord, BL bl) {
        this.pl_stock = pl_stock;
        this.pl_sup = pl_sup;
        this.pl_ord= pl_ord;
        this.bl=bl;
        this.scanner = new Scanner(System.in);
        this.menues=new MenuOP[5]; // 0. Director of Personnel Transport Center 1. Director of Personnel shops 2. Director of logistics 3. storekeeper 4. Shop manager

        menues[0]=new MenuOP("Director of Personnel Transport Center");
            MenuOP t1=new MenuOP("Workers Control");
            MenuOP t11=new MenuOP("Add Driver", ()->{
                System.out.println("Please insert the new driver ID:");
                String id=scanner.nextLine();
                System.out.println("Please insert the new Driver last name:");
                String lName=scanner.nextLine();
                System.out.println("Please insert the new Driver first name:");
                String fName=scanner.nextLine();
                System.out.println("Please insert the new Driver starting date:");
                String startingDate=scanner.nextLine();
                System.out.println("Please insert the new Driver license type:");
                String licens=scanner.nextLine();
                System.out.println("Please insert the new Driver salary:");
                String salary=scanner.nextLine();
                System.out.println("Please insert the new Driver bank number:");
                String bankn=scanner.nextLine();
                System.out.println("Please insert the new Driver account number:");
                String acountn=scanner.nextLine();
                System.out.println("Please insert the new Driver terms:");
                String terms=scanner.nextLine();
                bl.addDriver(id,lName,fName,startingDate,terms,salary,licens,bankn,acountn);
            });
            t1.addSon(t11);
            MenuOP t12=new MenuOP("Update Driver",()->{
                System.out.println("Please insert the ID of driver that you wants to update:");
                String id=scanner.nextLine();
                System.out.println("Please insert the Driver new last name (if there is no change leave empty):");
                String lName=scanner.nextLine();
                System.out.println("Please insert the Driver new first name (if there is no change leave empty):");
                String fName=scanner.nextLine();
                System.out.println("Please insert the Driver new starting date (if there is no change leave empty):");
                String startingDate=scanner.nextLine();
                System.out.println("Please insert the Driver new license type (if there is no change leave empty):");
                String licens=scanner.nextLine();
                System.out.println("Please insert the Driver new salary (if there is no change leave empty):");
                String salary=scanner.nextLine();
                System.out.println("Please insert the Driver new bank number (if there is no change leave empty):");
                String bankn=scanner.nextLine();
                System.out.println("Please insert the Driver new account number (if there is no change leave empty):");
                String acountn=scanner.nextLine();
                System.out.println("Please insert the Driver new terms (if there is no change leave empty):");
                String terms=scanner.nextLine();
                String d[]=new String[9];
                if(!lName.contentEquals("")){
                    d[0]=lName;
                }
                if(!fName.contentEquals("")){
                    d[1]=fName;
                }
                if(!startingDate.contentEquals("")){
                    d[2]=startingDate;
                }
                if(!terms.contentEquals("")){
                    d[3]=terms;
                }
                if(!salary.contentEquals("")){
                    d[4]=salary;
                }
                if(!licens.contentEquals("")){
                    d[5]=licens;
                }
                if(!bankn.contentEquals("")){
                    d[6]=bankn;
                }
                if(!acountn.contentEquals("")){
                    d[7]=acountn;
                }
                bl.updateDriver(id,d);
            });
            t1.addSon(t12);
            MenuOP t13=new MenuOP("Remove Driver",()->{
                System.out.println("Please insert the ID of Driver you wants to Remove:");
                String id=scanner.nextLine();
                bl.removeDriver(id);
            });
            t1.addSon(t13);
            menues[0].addSon(t1);
            MenuOP t2=new MenuOP("Workers Shifts");
            MenuOP t21=new MenuOP("Open Shift new Shift",()->{
                System.out.println("Please Insert the new Shift date:");
                String date=this.scanner.nextLine();
                System.out.println("Please Insert the new shift time:");
                String time=this.scanner.nextLine();
                System.out.println("Please Insert the new shift manager ID:");
                String manager=this.scanner.nextLine();
                bl.addShift(date,time,manager);
            });
            t2.addSon(t21);
            MenuOP t22=new MenuOP("Remove Shift",()->{
                System.out.println("Please Insert the date of the Shift you wants to Delete:");
                String date=this.scanner.nextLine();
                System.out.println("Please Insert the time of the Shift you wants to Delete:");
                String time=this.scanner.nextLine();
                bl.deleteShift(bl.getShift(date,time));
            });
            t2.addSon(t22);
            MenuOP t23=new MenuOP("Show Shift",()->{
                System.out.println("Please Insert the date of the Shift:");
                String date=this.scanner.nextLine();
                System.out.println("Please Insert the time of the Shift:");
                String time=this.scanner.nextLine();
                System.out.println(bl.getShift(date,time).toString());
            });
            t2.addSon(t23);
            MenuOP t24=new MenuOP("Manage workers in Shifts");
                MenuOP t241=new MenuOP("Show Available Worker",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    String time=this.scanner.nextLine();
                    //TODO: ofir&liam: add the needed function
                });
                t24.addSon(t241);
                MenuOP t242=new MenuOP("Add Workers to Shift",()->{
                    System.out.println("Please Insert the date of the Shift you wants to Delete:");
                    String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift you wants to Delete:");
                    String time=this.scanner.nextLine();
                    Shift s=bl.getShift(date,time);
                    boolean con=true;
                    while(con) {
                        System.out.println("Please insert Worker ID or 'q' if you have finished");
                        String id=scanner.nextLine();
                        if (id.contentEquals("q"))
                            con=false;
                        else
                            bl.addWorkerInShift(id, s);
                    }
                });
                t24.addSon(t242);
        menues[1]=new MenuOP("Director of Personnel shop");
        menues[2]=new MenuOP("Director of logistics");
            //for Director of Logistics
            MenuOP l1=new MenuOP("Trucks Control");
            MenuOP l11=new MenuOP("Add Truck",()->{
                System.out.println("Please insert the new truck Plate:");
                String plate=scanner.nextLine();
                System.out.println("Please insert the new truck Model:");
                String model=scanner.nextLine();
                System.out.println("Please insert the new truck Weight:");
                String weight=scanner.nextLine();
                System.out.println("Please insert the new truck Maximum carry weight:");
                String maxWeight=scanner.nextLine();
                System.out.println("Please insert the new truck license type:");
                String licens=scanner.nextLine();
                bl.addTruck(plate,model,weight,maxWeight,licens);
            });
            l1.addSon(l11);
            MenuOP l12=new MenuOP("Watch all Trucks",()->{
                System.out.println(bl.getAllTrucks());
            });
            l1.addSon(l12);
            menues[2].addSon(l1);
            MenuOP l2=new MenuOP("Transport Management");
            MenuOP l21=new MenuOP("Create Transport",()->{
                System.out.println("Please insert the new transport id:");
                String id=scanner.nextLine();
                System.out.println("Please insert the new transport date:");
                String date=scanner.nextLine();
                System.out.println("Please insert the new transport leave time:");
                String time=scanner.nextLine();
                bl.addTransport(date,time,id);
            });
            l2.addSon(l21);
            MenuOP l26=new MenuOP("Assign Truck and Driver",()->{
                System.out.println("Please insert the id of the transport you wants Assign Truck and driver to:");
                String id=scanner.nextLine();
                System.out.println("Please insert the Truck plate:");
                String truck=scanner.nextLine();
                System.out.println("Please insert the Driver ID:");
                String driver=scanner.nextLine();
                String d[]=new String[4];
                d[2]=truck;
                d[3]=driver;
                bl.updateTransport(id,d);
            });
            l2.addSon(l26);
            MenuOP l22=new MenuOP("Update Transport",()->{
                System.out.println("Please insert the id of the transport you wants to update:");
                String id=scanner.nextLine();
                System.out.println("Please insert the new date of the transport (if there is no change leave empty):");
                String date=scanner.nextLine();
                System.out.println("Please insert the new leave time of the transport (if there is no change leave empty):");
                String time=scanner.nextLine();
                String d[]=new String[4];
                if(!date.contentEquals("")){
                    d[0]=date;
                }
                if(!time.contentEquals("")){
                    d[1]=time;
                }
                bl.updateTransport(id,d);
            });
            l2.addSon(l22);
            MenuOP l23=new MenuOP("Missions Control");
            MenuOP l231=new MenuOP("Add Mission",()->{
                System.out.println("Please insert the id of the transport you wants to add mission to:");
                String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                String item=scanner.nextLine();
                System.out.println("Please insert the amount to be transferred:");
                String amount=scanner.nextLine();
                bl.addMission(id, amount, shop, supplier,item);
            });
            l23.addSon(l231);
            MenuOP l232=new MenuOP("Update Mission",()->{
                System.out.println("Please insert the id of the transport you wants to update mission in:");
                String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                String item=scanner.nextLine();
                System.out.println("Please insert the new amount:");
                String amount=scanner.nextLine();
                bl.updateMission(id,shop,item,supplier,amount);
            });
            l23.addSon(l232);
            MenuOP l233=new MenuOP("Remove Mission",()->{
                System.out.println("Please insert the id of the transport you wants to remove mission from:");
                String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                String item=scanner.nextLine();
                bl.updateMission(id,shop,item,supplier,"0");
            });
            l23.addSon(l233);
            l2.addSon(l23);
            MenuOP l24=new MenuOP("Create Docs");
            MenuOP l241=new MenuOP("General Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                String id=scanner.nextLine();
                bl.makeDoc(id);
            });
            l24.addSon(l241);
            MenuOP l242=new MenuOP("Site Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                String id=scanner.nextLine();
                System.out.println("Please insert the id of the Site you wants to create documents:");
                String shop=scanner.nextLine();
                bl.makeDoc(id,shop);
            });
            l24.addSon(l242);
            MenuOP l243=new MenuOP("Shop-Supplier Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                String id=scanner.nextLine();
                System.out.println("Please insert the id of the Shop you wants to create documents:");
                String shop=scanner.nextLine();
                System.out.println("Please insert the id of the Supplier you wants to create documents:");
                String supplier=scanner.nextLine();
                bl.makeDoc(id,shop,supplier);
            });
            l24.addSon(l243);
            l2.addSon(l24);
            MenuOP l25= new MenuOP("Watch All Transports", ()->{
                System.out.println(bl.getAllTransport());
            });
            l2.addSon(l25);
            menues[2].addSon(l2);
            MenuOP l4=new MenuOP("Watch all drivers",()->{
                System.out.println();//TODO: Ofir: add the needed function
            });
            menues[2].addSon(l4);
            MenuOP l3=new MenuOP("Watch All open Orders",()->{
                System.out.println();//TODO: omri&shahar:add the needed message
            });
            menues[2].addSon(l3);
        menues[3]=new MenuOP("storekeeper");
        menues[4]=new MenuOP("Shop Manager");




    }


    public void start() throws NituzException {
        System.out.println("Analysis and Design of Software Systems\nHW3 Transport, Workers, Inventory and Suppliers Modules");
        boolean exit = false, signed=false;
        int role=0;
        while (!exit ) {
            if (!signed) {
                try {
                    System.out.println("Please enter your ID: ");
                    System.out.print("#>");
                    String s = scanner.nextLine();
                    role=bl.setUser(s);
                    signed = true;
                    System.out.println("Welcome to the Super-Li computer System.");
                } catch (NituzException e) {
                    System.out.println(e);
                }
            } else {
                if (role==0){
                    // TODO: Gal: regular workers??????
                }
                else{
                    this.menues[role-1].execute();
                    bl.logOut();
                }
            }
        }
        /*
        int operation;

        while(true) {
            for (int i = 0; i < MENU.length; i++) {
                System.out.print(MENU[i] + "\n");
            }
            try {
                operation = Integer.parseInt(scanner.nextLine());
            } catch (Exception r) {
                System.out.print("Invalid operation. Please try again\n\n");
                continue;
            }

            String prop;
            switch (operation)
            {
                case 1:
                    pl_sup.start();
                    break;
                case 2:
                    pl_stock.start();
                    break;
                case 3:
                    pl_ord.orderCase();
                    break;
                case 4: {
                    System.out.println("Bye!");
                    return;
                }
                default:
                    System.out.println("No Such Option!");
                    break;
            }
        }
        */
    }

}
