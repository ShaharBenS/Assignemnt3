package PL;

import SharedClasses.NituzException;
import SharedClasses.MenuOP;
import BL.*;

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
            MenuOP l3=new MenuOP("Watch All open Orders",()->{
                System.out.println();//TODO: omri&shahar:add the needed message
            });
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
