package PL;

import SharedClasses.NituzException;
import SharedClasses.MenuOP;
import BL.*;
import SharedClasses.Shift;
import com.sun.org.apache.xpath.internal.operations.String;

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
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the new Driver last name:");
                java.lang.String lName=scanner.nextLine();
                System.out.println("Please insert the new Driver first name:");
                java.lang.String fName=scanner.nextLine();
                System.out.println("Please insert the new Driver starting date:");
                java.lang.String startingDate=scanner.nextLine();
                System.out.println("Please insert the new Driver license type:");
                java.lang.String licens=scanner.nextLine();
                System.out.println("Please insert the new Driver salary:");
                java.lang.String salary=scanner.nextLine();
                System.out.println("Please insert the new Driver bank number:");
                java.lang.String bankn=scanner.nextLine();
                System.out.println("Please insert the new Driver account number:");
                java.lang.String acountn=scanner.nextLine();
                System.out.println("Please insert the new Driver terms:");
                java.lang.String terms=scanner.nextLine();
                try {
                    bl.addDriver(id, lName, fName, startingDate, terms, salary, licens, bankn, acountn);
                }catch (NituzException e){
                    System.out.println(e.getMessage());
                }
            });
            t1.addSon(t11);
            MenuOP t12=new MenuOP("Update Driver",()->{
                System.out.println("Please insert the ID of driver that you wants to update:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the Driver new last name (if there is no change leave empty):");
                java.lang.String lName=scanner.nextLine();
                System.out.println("Please insert the Driver new first name (if there is no change leave empty):");
                java.lang.String fName=scanner.nextLine();
                System.out.println("Please insert the Driver new starting date (if there is no change leave empty):");
                java.lang.String startingDate=scanner.nextLine();
                System.out.println("Please insert the Driver new license type (if there is no change leave empty):");
                java.lang.String licens=scanner.nextLine();
                System.out.println("Please insert the Driver new salary (if there is no change leave empty):");
                java.lang.String salary=scanner.nextLine();
                System.out.println("Please insert the Driver new bank number (if there is no change leave empty):");
                java.lang.String bankn=scanner.nextLine();
                System.out.println("Please insert the Driver new account number (if there is no change leave empty):");
                java.lang.String acountn=scanner.nextLine();
                System.out.println("Please insert the Driver new terms (if there is no change leave empty):");
                java.lang.String terms=scanner.nextLine();
                java.lang.String d[]=new  java.lang.String[9];
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
                try {
                    bl.updateDriver(id, d);
                }catch (NituzException e){
                    System.out.println(e.getMessage());
                }
            });
            t1.addSon(t12);
            MenuOP t13=new MenuOP("Remove Driver",()->{
                System.out.println("Please insert the ID of Driver you wants to Remove:");
                java.lang.String id=scanner.nextLine();
                try {
                    bl.removeDriver(id);
                }catch (NituzException e){
                    System.out.println(e.getMessage());
                }
            });
            t1.addSon(t13);
            MenuOP t14=new MenuOP("Show All Drivers",()->{
                System.out .println();// TODO: Ofir & Liam : add needed function
            });
            t1.addSon(t14);
            menues[0].addSon(t1);
            MenuOP t2=new MenuOP("Workers Shifts");
            MenuOP t21=new MenuOP("Open Shift new Shift",()->{
                System.out.println("Please Insert the new Shift date:");
                java.lang.String date=this.scanner.nextLine();
                System.out.println("Please Insert the new shift time:");
                java.lang.String time=this.scanner.nextLine();
                System.out.println("Please Insert the new shift manager ID:");
                java.lang.String manager=this.scanner.nextLine();
                try {
                    bl.addShift(date, time, manager);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            t2.addSon(t21);
            MenuOP t22=new MenuOP("Remove Shift",()->{
                System.out.println("Please Insert the date of the Shift you wants to Delete:");
                java.lang.String date=this.scanner.nextLine();
                System.out.println("Please Insert the time of the Shift you wants to Delete:");
                java.lang.String time=this.scanner.nextLine();
                try{
                    Shift s= bl.getShift(date, time);
                    boolean deleteShift = bl.deleteShift(s);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            t2.addSon(t22);
            MenuOP t23=new MenuOP("Show Shift",()->{
                System.out.println("Please Insert the date of the Shift:");
                java.lang.String date=this.scanner.nextLine();
                System.out.println("Please Insert the time of the Shift:");
                java.lang.String time=this.scanner.nextLine();
                try{
                    System.out.println(bl.getShift(date,time).toString());
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            t2.addSon(t23);
            MenuOP t24=new MenuOP("Manage Workers in Shifts");
                MenuOP t241=new MenuOP("Show Available Worker",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    System.out.println();//TODO: ofir&liam: add the needed function
                });
                t24.addSon(t241);
                MenuOP t242=new MenuOP("Add Workers to Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Worker ID or 'q' if you have finished");
                            java.lang.String id = scanner.nextLine();
                            if (id.contentEquals("q"))
                                con = false;
                            else
                                bl.addWorkerInShift(id, s);
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t24.addSon(t242);
                MenuOP t243=new MenuOP("Remove Workers from Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Worker ID or 'q' if you have finished");
                            java.lang.String id = scanner.nextLine();
                            if (id.contentEquals("q"))
                                con = false;
                            else
                                bl.removeWorkerFromShift(id, s);
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t24.addSon(t243);
                MenuOP t244=new MenuOP("Sign Worker to Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Worker ID or 'q' if you have finished");
                            java.lang.String id = scanner.nextLine();
                            if (id.contentEquals("q"))
                                con = false;
                            else
                                bl.addPossibleShift(s, id);
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t24.addSon(t244);
                MenuOP t245=new MenuOP ("Unsign Worker from Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Worker ID or 'q' if you have finished");
                            java.lang.String id = scanner.nextLine();
                            if (id.contentEquals("q"))
                                con = false;
                            else
                                bl.deletePossibleShift(s, id);
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t24.addSon(t245);
            t2.addSon(t24);
            MenuOP t25=new MenuOP("Manege Roles in Shifts");
                MenuOP t251=new MenuOP("Add Roles to Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Role or 'q' if you have finished");
                            java.lang.String role = scanner.nextLine();
                            if (role.contentEquals("q"))
                                con = false;
                            else {
                                java.lang.String r[] = new java.lang.String[1];
                                r[0] = role;
                                bl.chooseRolesInShift(r, s);
                            }
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t25.addSon(t251);
                MenuOP t252=new MenuOP("Remove Role from Shift",()->{
                    System.out.println("Please Insert the date of the Shift:");
                    java.lang.String date=this.scanner.nextLine();
                    System.out.println("Please Insert the time of the Shift:");
                    java.lang.String time=this.scanner.nextLine();
                    try {
                        Shift s = bl.getShift(date, time);
                        boolean con = true;
                        while (con) {
                            System.out.println("Please insert Role or 'q' if you have finished");
                            java.lang.String role = scanner.nextLine();
                            if (role.contentEquals("q"))
                                con = false;
                            else
                                bl.deleteRoleInShift(role, s);
                        }
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t25.addSon(t252);
            t2.addSon(t25);
            menues[0].addSon(t2);
            MenuOP t3=new MenuOP("Banks Control");
                MenuOP t31=new MenuOP ("Add Bank",()->{
                    System.out.println("Please enter Bank Number:");
                    java.lang.String bnum=scanner.nextLine();
                    System.out.println("Please enter Bank Name:");
                    java.lang.String bname=scanner.nextLine();
                    try {
                        bl.addBank(bnum, bname);
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t3.addSon(t31);
                MenuOP t32=new MenuOP ("Remove Bank",()->{
                    System.out.println("Please enter Bank Number:");
                    java.lang.String bnum=scanner.nextLine();
                    try {
                        bl.deleteBank(bnum);
                    } catch (NituzException x) {
                        System.out.println(x.getMessage());
                    }
                });
                t3.addSon(t32);
                MenuOP t33=new MenuOP ("Show All Banks",()->{
                    System.out.println();//TODO: ofir & liam: add needed function
                });
                t3.addSon(t33);
            menues[0].addSon(t3);
            MenuOP t4=new MenuOP("Roles Management");
                MenuOP t41=new MenuOP("Add Role",()->{
                    boolean con=true;
                    while(con) {
                        System.out.println("Please Role Name or 'q' if you have finished");
                        java.lang.String id=scanner.nextLine();
                        if (id.contentEquals("q"))
                            con=false;
                        else
                            try {
                                bl.addRole(id);
                            } catch (NituzException x) {
                                System.out.println(x.getMessage());
                            }
                    }
                });
                t4.addSon(t41);
                MenuOP t42=new MenuOP("Remove Role",()->{
                    boolean con=true;
                    while(con) {
                        System.out.println("Please insert Role Name or 'q' if you have finished");
                        java.lang.String id=scanner.nextLine();
                        if (id.contentEquals("q"))
                            con=false;
                        else
                            try {
                                bl.deleteRole(id);
                            } catch (NituzException x) {
                                System.out.println(x.getMessage());
                            }
                    }
                });
                t4.addSon(t42);
                MenuOP t43=new MenuOP("Show Roles",()->{
                    System.out.println();//TODO: Ofir & Liam: add needed function
                });
                t4.addSon(t43);
            menues[0].addSon(t4);

        menues[1]=new MenuOP("Director of Personnel shop");
        MenuOP s1=new MenuOP("Workers Control");
            MenuOP s11=new MenuOP("Add Worker",()->{
                System.out.println("Please insert the new Worker ID:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the new Worker last name:");
                java.lang.String lName=scanner.nextLine();
                System.out.println("Please insert the new Worker first name:");
                java.lang.String fName=scanner.nextLine();
                System.out.println("Please insert the new Worker starting date:");
                java.lang.String startingDate=scanner.nextLine();
                System.out.println("Please insert the new Worker job:");
                java.lang.String licens=scanner.nextLine();
                System.out.println("Please insert the new Worker salary:");
                java.lang.String salary=scanner.nextLine();
                System.out.println("Please insert the new Worker bank number:");
                java.lang.String bankn=scanner.nextLine();
                System.out.println("Please insert the new Worker account number:");
                java.lang.String acountn=scanner.nextLine();
                System.out.println("Please insert the new Worker terms:");
                java.lang.String terms=scanner.nextLine();
                try {
                    bl.add(id, lName, fName, startingDate, terms, salary, licens, bankn, acountn);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            s1.addSon(s11);
            MenuOP s12=new MenuOP("Update Worker",()->{
                System.out.println("Please insert the ID of the Worker that you wants to update:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the Worker new last name (if there is no change leave empty):");
                java.lang.String lName=scanner.nextLine();
                System.out.println("Please insert the Worker new first name (if there is no change leave empty):");
                java.lang.String fName=scanner.nextLine();
                System.out.println("Please insert the Worker new starting date (if there is no change leave empty):");
                java.lang.String startingDate=scanner.nextLine();
                System.out.println("Please insert the Worker new job (if there is no change leave empty):");
                java.lang.String licens=scanner.nextLine();
                System.out.println("Please insert the Worker new salary (if there is no change leave empty):");
                java.lang.String salary=scanner.nextLine();
                System.out.println("Please insert the Worker new bank number (if there is no change leave empty):");
                java.lang.String bankn=scanner.nextLine();
                System.out.println("Please insert the Worker new account number (if there is no change leave empty):");
                java.lang.String acountn=scanner.nextLine();
                System.out.println("Please insert the Worker new terms (if there is no change leave empty):");
                java.lang.String terms=scanner.nextLine();
                java.lang.String d[]=new  java.lang.String[9];
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
                try {
                    bl.update(id, d);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            s1.addSon(s12);
            MenuOP s13=new MenuOP("Remove Worker",()->{
                System.out.println("Please insert the ID of the Worker you wants to Remove:");
                java.lang.String id=scanner.nextLine();
                try {
                    bl.remove(id);
                }catch (NituzException e){
                    System.out.println(e.getMessage());
                }
            });
            s1.addSon(s13);
            MenuOP s14=new MenuOP("Show All Workers",()->{
                System.out .println();// TODO: Ofir & Liam : add needed function
            });
            s1.addSon(s14);
        menues[1].addSon(s1);
        menues[1].addSon(t2.clone());
        menues[1].addSon(t3.clone());
        menues[1].addSon(t4.clone());
        menues[2]=new MenuOP("Director of logistics");
            //for Director of Logistics
            MenuOP l1=new MenuOP("Trucks Control");
            MenuOP l11=new MenuOP("Add Truck",()->{
                System.out.println("Please insert the new truck Plate:");
                java.lang.String plate=scanner.nextLine();
                System.out.println("Please insert the new truck Model:");
                java.lang.String model=scanner.nextLine();
                System.out.println("Please insert the new truck Weight:");
                java.lang.String weight=scanner.nextLine();
                System.out.println("Please insert the new truck Maximum carry weight:");
                java.lang.String maxWeight=scanner.nextLine();
                System.out.println("Please insert the new truck license type:");
                java.lang.String licens=scanner.nextLine();
                try{
                    bl.addTruck(plate,model,weight,maxWeight,licens);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l1.addSon(l11);
            MenuOP l12=new MenuOP("Watch all Trucks",()->{
                try {
                    System.out.println(bl.getAllTrucks());
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l1.addSon(l12);
            menues[2].addSon(l1);
            MenuOP l2=new MenuOP("Transport Management");
            MenuOP l21=new MenuOP("Create Transport",()->{
                System.out.println("Please insert the new transport id:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the new transport date:");
                java.lang.String date=scanner.nextLine();
                System.out.println("Please insert the new transport leave time:");
                java.lang.String time=scanner.nextLine();
                try {
                    bl.addTransport(date, time, id);
                } catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l2.addSon(l21);
            MenuOP l26=new MenuOP("Assign Truck and Driver",()->{
                System.out.println("Please insert the id of the transport you wants Assign Truck and driver to:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the Truck plate:");
                java.lang.String truck=scanner.nextLine();
                System.out.println("Please insert the Driver ID:");
                java.lang.String driver=scanner.nextLine();
                java.lang.String d[]=new java.lang.String[4];
                d[2]=truck;
                d[3]=driver;
                try {
                    bl.updateTransport(id, d);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l2.addSon(l26);
            MenuOP l22=new MenuOP("Update Transport",()->{
                System.out.println("Please insert the id of the transport you wants to update:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the new date of the transport (if there is no change leave empty):");
                java.lang.String date=scanner.nextLine();
                System.out.println("Please insert the new leave time of the transport (if there is no change leave empty):");
                java.lang.String time=scanner.nextLine();
                java.lang.String d[]=new java.lang.String[4];
                if(!date.contentEquals("")){
                    d[0]=date;
                }
                if(!time.contentEquals("")){
                    d[1]=time;
                }
                try {
                    bl.updateTransport(id, d);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l2.addSon(l22);
            MenuOP l23=new MenuOP("Missions Control");
            MenuOP l231=new MenuOP("Add Mission",()->{
                System.out.println("Please insert the id of the transport you wants to add mission to:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                java.lang.String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                java.lang.String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                java.lang.String item=scanner.nextLine();
                System.out.println("Please insert the amount to be transferred:");
                java.lang.String amount=scanner.nextLine();
                try {
                    bl.addMission(id, amount, shop, supplier, item);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l23.addSon(l231);
            MenuOP l232=new MenuOP("Update Mission",()->{
                System.out.println("Please insert the id of the transport you wants to update mission in:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                java.lang.String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                java.lang.String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                java.lang.String item=scanner.nextLine();
                System.out.println("Please insert the new amount:");
                java.lang.String amount=scanner.nextLine();
                try {
                    bl.updateMission(id, shop, item, supplier, amount);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l23.addSon(l232);
            MenuOP l233=new MenuOP("Remove Mission",()->{
                System.out.println("Please insert the id of the transport you wants to remove mission from:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the supplier id:");
                java.lang.String supplier=scanner.nextLine();
                System.out.println("Please insert the shop id:");
                java.lang.String shop=scanner.nextLine();
                System.out.println("Please insert the item id:");
                java.lang.String item=scanner.nextLine();
                try {
                    bl.updateMission(id, shop, item, supplier, "0");
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l23.addSon(l233);
            l2.addSon(l23);
            MenuOP l24=new MenuOP("Transports Docs");
            MenuOP l241=new MenuOP("General Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                java.lang.String id=scanner.nextLine();
                try {
                    bl.makeDoc(id);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l24.addSon(l241);
            MenuOP l242=new MenuOP("Site Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the id of the Site you wants to create documents:");
                java.lang.String shop=scanner.nextLine();
                try {
                    bl.makeDoc(id, shop);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l24.addSon(l242);
            MenuOP l243=new MenuOP("Shop-Supplier Transport Documents",()->{
                System.out.println("Please insert the id of the transport you wants to create documents:");
                java.lang.String id=scanner.nextLine();
                System.out.println("Please insert the id of the Shop you wants to create documents:");
                java.lang.String shop=scanner.nextLine();
                System.out.println("Please insert the id of the Supplier you wants to create documents:");
                java.lang.String supplier=scanner.nextLine();
                try {
                    bl.makeDoc(id, shop, supplier);
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l24.addSon(l243);
            l2.addSon(l24);
            MenuOP l25= new MenuOP("Watch All Transports", ()->{
                try{
                    System.out.println(bl.getAllTransport());
                }catch (NituzException x) {
                    System.out.println(x.getMessage());
                }
            });
            l2.addSon(l25);
            menues[2].addSon(l2);
            MenuOP l4=new MenuOP("Watch all drivers",()->{
                System.out.println();//TODO: Ofir & Liam: add the needed function
            });
            menues[2].addSon(l4);
            MenuOP l3=new MenuOP("Watch All open Orders",()->{
                System.out.println();//TODO: omri&shahar:add the needed message
            });
            menues[2].addSon(l3);
        menues[3]=new MenuOP("storekeeper");
        MenuOP st1= new MenuOP("Stock Management",()->{pl_stock.start();});
        menues[3].addSon(st1);
        MenuOP st3=new MenuOP("Order Management",()->{pl_ord.orderCase();});
        menues[3].addSon(st3);
        MenuOP st2=new MenuOP("Suppliers Management",()->{pl_sup.start();});
        menues[3].addSon(st2);
        menues[4]=new MenuOP("Shop Manager");
        menues[4].addSon(st2.clone());
        MenuOP m1=new MenuOP("Reports");//watch all reports
        MenuOP m11=new MenuOP("Stock Reports",()->{});
        m1.addSon(m11);
        MenuOP m12=new MenuOP("Order Reports");
        MenuOP m121=new MenuOP("Oder Report by order number",()->{pl_ord.case3();});
        m12.addSon(m121);
        MenuOP m122=new MenuOP("Order Report by Supplier ID",()->{pl_ord.case31();});
        m12.addSon(m122);
        m1.addSon(m12);
        m1.addSon(s14.clone());
        MenuOP m13=new MenuOP ("Transports Reports");
        m13.addSon(l25.clone());
        m13.addSon(l24.clone());
        m1.addSon(m13);
        menues[4].addSon(m1);
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
                    java.lang.String s = this.scanner.nextLine();
                    role=bl.setUser(s);
                    signed = true;
                    System.out.println("Welcome to the Super-Li computer System.");
                } catch (NituzException e) {
                    System.out.println(e);
                }
            } else {
                if (role==0){
                    System.out.println("you have no open option. type ENTER to log out");
                    this.scanner.nextLine();
                    bl.logOut();
                }
                else{
                    this.menues[role-1].execute();
                    bl.logOut();
                    exit = true;
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
