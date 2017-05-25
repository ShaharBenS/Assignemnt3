package ProgramLauncher;

import BL.*;
import DAL.*;
import PL.*;
import SharedClasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shahar on 06/04/17.
 */
public class ProgramLauncher
{
    public static List<OrderItem> alreadyWarned = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static Thread checkPeriodicOrders;
    private static boolean continuePeriodCheck = true;
    public static void main(String [] args) throws InterruptedException {
        Connection conn = getConnectionAndInitDatabase("Database.db");

        // DAL INIT
        Items ITEMS = new Items(conn);
        Quantities QUANTITIES = new Quantities(conn);
        Categories CATEGORIES = new Categories(conn);
        Prices PRICES = new Prices(conn, CATEGORIES);

        Contacts CONTACTS = new Contacts(conn);
        Discounts DISCOUNTS = new Discounts(conn);
        Orders ORDERS = new Orders(conn);
        OrdersItems ORDERS_ITEMS = new OrdersItems(conn);
        SupplierItems SUPPLIER_ITEMS = new SupplierItems(conn);
        Suppliers SUPPLIERS = new Suppliers(conn);

        DAL dal =  new DAL(conn,ITEMS, SUPPLIERS);

        // BL INIT
        SupplierBL SBL = new SupplierBL(CONTACTS, DISCOUNTS, ITEMS, SUPPLIER_ITEMS, SUPPLIERS, ORDERS, ORDERS_ITEMS,QUANTITIES);
        ProductManagement PRODUCT_MANAGEMENT = new ProductManagement(ITEMS, PRICES, QUANTITIES, SBL);
        CategoryManagement CATEGORY_MANAGEMENT = new CategoryManagement(CATEGORIES, ITEMS, PRICES, QUANTITIES);
        PriceManagement PRICE_MANAGEMENT = new PriceManagement(PRICES);

        BL bl =  new BL(dal);

        // PL INIT
        //TODO:GAL: redo all
        PL_Stock PL_STOCK = new PL_Stock(PRODUCT_MANAGEMENT, PRICE_MANAGEMENT, CATEGORY_MANAGEMENT,SBL);
        PL_Supplier pl_sup= new PL_Supplier (SBL);
        PL_Orders pl_ord= new PL_Orders(SBL);
        Menu MENU = new Menu(PL_STOCK, pl_sup, pl_ord);

        /*
            Database init
         */

        if(CATEGORIES.addCategory(new Category(103,"Drinks"))) {
            CATEGORIES.addCategory(new Category(102, "KARTON", 103));
            CATEGORIES.addCategory(new Category(100, "Milk", 102));
            CATEGORIES.addCategory(new Category(101, "Meat"));
            CATEGORIES.addCategory(new Category(104, "Bread"));
            CATEGORIES.addCategory(new Category(105, "35%", 101));

            SUPPLIERS.addSupplier(new Supplier(100000, "TNUVA", 111, 1, 15, "LEOMI",
                    "BY HAND", "10:50", "netivot"));
            SUPPLIERS.addSupplier(new Supplier(200000, "TARA", 222, 2, 16, "HAPOALIM",
                    "INTERNET ONLY", "13:00", "shfaram"));
            SUPPLIERS.addSupplier(new Supplier(300000, "MOTHER-EARTH", 333, 3, 17, "YAHAV",
                    "Mail", "05:00", "plat-earth"));

            CONTACTS.addContact(new Contact("10101010", 100000, "Naruto Uzumaki",
                    "123456789", "a@a.a"));

            CONTACTS.addContact(new Contact("20202020", 200000, "Edoward Elich",
                    "465123789", "a@a.a"));

            CONTACTS.addContact(new Contact("30303030", 300000, "Natsu Dragneel",
                    "789123456", "c@c.c"));

            //TODO:omri&shahar:
            ITEMS.addItem(new Item(111111, "KORNFLEKS", 102, "SHKEL-INC"));
            ITEMS.addItem(new Item(222222, "Steak", 101, "COWS-KILLERS"));
            ITEMS.addItem(new Item(333333, "Cheese", 100, "TARA"));
            ITEMS.addItem(new Item(444444, "White-Bread", 101, "Bereshit"));
            ITEMS.addItem(new Item(555555, "Soda", 103, "Shweps"));
            ITEMS.addItem(new Item(666666, "Cola", 103, "Coca-Cola"));
            ITEMS.addItem(new Item(777777, "Arak", 103, "Tzuani-Nehmad"));
            ITEMS.addItem(new Item(888888, "Potatoes", 104, "Mother-Earth"));
            ITEMS.addItem(new Item(999999, "Tomato", 104, "Mother-Earth"));
            ITEMS.addItem(new Item(101010, "Rice", 105, "Mother-Earth"));
            ITEMS.addItem(new Item(202020, "Eggs", 105, "Mother-Chicken"));


            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 111111, 100000, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 111111, 100001, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 333333, 100001, 12.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 222222, 200000, 22.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 333333, 300000, 32.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 444444, 400000, 42.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 555555, 500000, 52.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 666666, 600000, 52.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 777777, 700000, 62.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 888888, 800000, 72.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(300000, 999999, 900000, 82.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(100000, 101010, 110000, 92.5));
            SUPPLIER_ITEMS.addSupplierItem(new SupplierItem(200000, 202020, 120000, 112.5));

            DISCOUNTS.addDiscount(new Discount(100000, 111111, 20, 10));
            DISCOUNTS.addDiscount(new Discount(200000, 111111, 20, 20));
            DISCOUNTS.addDiscount(new Discount(200000, 222222, 20, 20));
            DISCOUNTS.addDiscount(new Discount(300000, 333333, 20, 30));
            DISCOUNTS.addDiscount(new Discount(200000, 333333, 20, 30));
            DISCOUNTS.addDiscount(new Discount(400000, 444444, 20, 40));
            DISCOUNTS.addDiscount(new Discount(500000, 555555, 20, 50));
            DISCOUNTS.addDiscount(new Discount(600000, 666666, 20, 60));
            DISCOUNTS.addDiscount(new Discount(700000, 777777, 20, 70));
            DISCOUNTS.addDiscount(new Discount(800000, 888888, 20, 80));
            DISCOUNTS.addDiscount(new Discount(900000, 999999, 20, 90));
            DISCOUNTS.addDiscount(new Discount(110000, 101010, 20, 15));
            DISCOUNTS.addDiscount(new Discount(120000, 202020, 20, 25));


            ORDERS.addOrder(new Order(1, 200000, new Date(new java.util.Date()), "20202020", 0));
            ORDERS.addOrder(new Order(2, 300000, new Date(new java.util.Date()), "30303030", 1));
            ORDERS.addOrder(new Order(3, 100000, new Date(new java.util.Date()), "10101010", 2));
            ORDERS.addOrder(new Order(4, 300000, new Date(new java.util.Date()), "30303030", 3));


            ORDERS_ITEMS.addOrderItem(new OrderItem(1, 111111, 30, 50.0));
            ORDERS_ITEMS.addOrderItem(new OrderItem(2, 222222, 40, 10));
            ORDERS_ITEMS.addOrderItem(new OrderItem(3, 333333, 30, 60.0));
            ORDERS_ITEMS.addOrderItem(new OrderItem(4, 444444, 40, 10));
            ORDERS_ITEMS.addOrderItem(new OrderItem(1, 555555, 30, 22.5));
            ORDERS_ITEMS.addOrderItem(new OrderItem(2, 666666, 40, 10));
            ORDERS_ITEMS.addOrderItem(new OrderItem(3, 777777, 30, 32.5));
            ORDERS_ITEMS.addOrderItem(new OrderItem(4, 888888, 40, 10));
            ORDERS_ITEMS.addOrderItem(new OrderItem(1, 999999, 30, 42.5));
            ORDERS_ITEMS.addOrderItem(new OrderItem(2, 101010, 40, 10));
            ORDERS_ITEMS.addOrderItem(new OrderItem(3, 202020, 30, 12.5));

            QUANTITIES.addItemQuantity(new Quantity(111111, "SHELF 2-A", 0,
                    10, 10, 0, 30));
            QUANTITIES.addItemQuantity(new Quantity(222222, "SHELF 2-B", 0,
                    20, 10, 0, 12));
            QUANTITIES.addItemQuantity(new Quantity(333333, "SHELF 2-C", 0,
                    30, 10, 30, 63));
            QUANTITIES.addItemQuantity(new Quantity(444444, "SHELF 2-D", 0,
                    40, 20, 20, 80));
            QUANTITIES.addItemQuantity(new Quantity(555555, "SHELF 2-E", 0,
                    50, 20, 10, 10));
            QUANTITIES.addItemQuantity(new Quantity(666666, "SHELF 2-F", 0,
                    60, 30, 10, 13));
            QUANTITIES.addItemQuantity(new Quantity(777777, "SHELF 2-G", 0,
                    70, 30, 10, 50));
            QUANTITIES.addItemQuantity(new Quantity(888888, "SHELF 2-H", 0,
                    80, 40, 30, 90));
            QUANTITIES.addItemQuantity(new Quantity(999999, "SHELF 2-I", 0,
                    90, 40, 60, 60));
            QUANTITIES.addItemQuantity(new Quantity(101010, "SHELF 2-J", 0,
                    100, 50, 30, 30));
            QUANTITIES.addItemQuantity(new Quantity(202020, "SHELF 2-K", 0,
                    110, 50, 10, 50));

            PRICES.addItemPrice(new Price(111111, 20.5, 0, null, null));
            PRICES.addItemPrice(new Price(222222, 30.5, 0, null, null));
            PRICES.addItemPrice(new Price(333333, 50.5, 0, null, null));
            PRICES.addItemPrice(new Price(444444, 60.5, 0, null, null));
            PRICES.addItemPrice(new Price(555555, 70.5, 30,
                    new Date(2017, 12, 10), new Date(2017, 12, 30)));
            PRICES.addItemPrice(new Price(666666, 80.5, 20,
                    new Date(2017, 04, 10), new Date(2017, 04, 16)));
            PRICES.addItemPrice(new Price(777777, 90.5, 10,
                    new Date(2017, 03, 10), new Date(2017, 03, 14)));
            PRICES.addItemPrice(new Price(888888, 100.5, 0, null, null));
            PRICES.addItemPrice(new Price(999999, 200.5, 0, null, null));
            PRICES.addItemPrice(new Price(101010, 56.5, 0, null, null));
            PRICES.addItemPrice(new Price(202020, 23.5, 0, null, null));

            ORDERS.setArrivalDate(3, new Date(new java.util.Date()));
            ORDERS.setArrivalDate(4, new Date(new java.util.Date()));

            //TODO:: change to supplier
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (313,'tnuva','Petach-Tikava','Shlomo','0500000000'); INSERT INTO Supliers (code) VALUES (313)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (323,'elit','Ramat-Gan','benizri','0522222222'); INSERT INTO Supliers (code) VALUES (323)";
            stmt.executeUpdate(sql);
            sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (4,'elit','Ramat-Gan','benizri','0522228222'); INSERT INTO Supliers (code) VALUES (4)";
            stmt.executeUpdate(sql);

            String sql;
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (1, 'David', 2)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (2, 'Yisrael', 1)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (3, 'Shimon', 3)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (4, 'Moshe', 4)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (5, 'Marziano', 5)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (555225398 , 'AA', 6)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (111111234  , 'BB', 4)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Drivers (ID,Name, licence) VALUES (234567891  , 'CC', 3)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('12121212', 'Volvo', 1, 500, 1000)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('13131313', 'Ford', 2, 750, 4000)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('14141414', 'Tetra', 3, 1000, 7000)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('15151515', 'Mazda', 4, 1500, 10000)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Trucks (Plate,Model,licenseType,Wight,MaxWight) VALUES ('16161616', 'Subaru', 5, 2250, 15000)";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (21212, 0.2, 'koteg')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (78, 650.2, 'cow')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (23232, 1, 'milk')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (24242, 0.5, 'hummus')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (25252, 0.01, 'tiktak')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Items (code , Weight ,descripsion) VALUES (26262, 670, 'chocolate')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (414,'supersal','rishon','Shimi','0501212121'); INSERT INTO Shops (code , rigion ) VALUES (414,'A')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (424,'Mega','jafa','Meni','0548889999'); INSERT INTO Shops (code , rigion ) VALUES (424,'A')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (434,'Osher Ad','jerusalem','avram','0587777777'); INSERT INTO Shops (code , rigion ) VALUES (434,'B')";
                stmt.executeUpdate(sql);
                sql="INSERT INTO Sites (code , Name  ,Address , Contact , Phone ) VALUES (5,'Osher Ad','Tel Aviv','avrami','0587778777'); INSERT INTO Shops (code , rigion ) VALUES (5,'B')";
                stmt.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        SBL.initOrderID();

        checkPeriodicOrders = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
            while(continuePeriodCheck){

                Order [] orders = ORDERS.getPeriodicOrders();
                List<Order> warnings = new ArrayList<>();
                int count = 0;
                for(Order order:orders)
                {
                    // check if tomorrow the order comes
                    java.sql.Date lastDate = order.getDate().toSQLdate();
                    java.sql.Date todayDate = new Date(new java.util.Date()).toSQLdate();
                    long diff = todayDate.getTime() - lastDate.getTime();
                    long days = TimeUnit.MILLISECONDS.toDays(diff);
                    int frequency = ORDERS.getFrequency(order.getOrderID());

                    if(days - frequency == 0)
                    {
                        ORDERS.setDate(order.getOrderID(),new Date(new java.util.Date()));
                        ORDERS.setArrivalDate(order.getOrderID(),null);
                    }
                    if(frequency - days == 1)
                    {
                        count++;
                        warnings.add(order);
                    }
                }

                orders = new Order[count];
                orders = warnings.toArray(orders);

                if(orders.length > 0)
                {
                    synchronized (System.in)
                    {
                        for (Order order : orders) {
                            //System.out.println(order.toStringWithoutOrderItems());
                            OrderItem[] OI = ORDERS_ITEMS.getOrderItems(order.getOrderID());
                            for (OrderItem aOI : OI) {
                                Iterator iterator = alreadyWarned.iterator();
                                boolean continue_ = false;
                                while (iterator.hasNext()) {
                                    OrderItem oi = (OrderItem) iterator.next();
                                    if (oi.getOrderID() == aOI.getOrderID() && aOI.getItemID() == oi.getItemID()) {
                                        continue_ = true;
                                    }
                                }
                                if (continue_) {
                                    continue;
                                }
                                System.out.println("Periodic Order for tomorrow found, OrderID: "+aOI.getOrderID());
                                System.out.println(aOI.toString());
                                Quantity q = QUANTITIES.getQuantity(aOI.getItemID());
                                System.out.println("The current Amount is: " + (q == null ? "0" : q.getCurrent()));
                                System.out.println("Would you like to change the amount to order? Enter 'yes' to change");
                                String choice = sc.nextLine();
                                if (choice.equals("yes")) {
                                    System.out.println("New Amount: ");
                                    int amount = Integer.parseInt(sc.nextLine());
                                    boolean result = ORDERS_ITEMS.setQuantity(order.getOrderID(), aOI.getItemID(), amount);
                                    System.out.println("Update status: " + result);
                                    alreadyWarned.add(aOI);
                                }
                                alreadyWarned.add(aOI);
                            }
                        }
                    }
                }


                try {
                    Thread.sleep(24*60*60);
                } catch (InterruptedException e)
                {
                }
            }
        });
        checkPeriodicOrders.start();

        // start
        MENU.start();
        continuePeriodCheck = false;
        checkPeriodicOrders.interrupt();
        checkPeriodicOrders.join();


        try
        {
            conn.commit();
            conn.close();
            Thread.sleep(500);

        } catch (Exception e)
        {
        }
    }

    private static Connection getConnectionAndInitDatabase(String dataBaseName) {
        Connection c = null;
        Statement stmt = null;
        try {
            /*Opening Connection*/
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dataBaseName);

            c.createStatement().execute("PRAGMA FOREIGN_KEYS = ON;");
            c.setAutoCommit(false);


            String sql;

            stmt = c.createStatement();



            //********************************************************************************************************

            sql = "CREATE TABLE Drivers (ID INT PRIMARY KEY NOT NULL, Name VARCHAR(50) NOT NULL, Licence INT NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Trucks (Plate VARCHAR(10) PRIMARY KEY NOT NULL, Model VARCHAR(50) NOT NULL, licenseType INT NOT NULL , Wight INT NOT NULL , MaxWight INT NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Transport (TransportNumber INT PRIMARY KEY NOT NULL ,  date DATE NOT NULL );";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Items (code INT PRIMARY KEY NOT NULL, Weight DOUBLE NOT NULL ,descripsion VARCHAR(50) DEFAULT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Sites (code INT PRIMARY KEY NOT NULL, Name VARCHAR(50) NOT NULL ,Address VARCHAR(50) NOT NULL, Contact VARCHAR(50) NOT NULL, Phone VARCHAR(10) NOT NULL);";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Shops (code INT PRIMARY KEY REFERENCES Sites(code), rigion VARCHAR(50) NOT NULL );";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE Supliers (code INT PRIMARY KEY REFERENCES Sites(code));";
            stmt.executeUpdate(sql);
            sql="CREATE TABLE Missions (shop INT REFERENCES Shops(code), Supplier INT REFERENCES Supliers(code) ,Transport INT REFERENCES Transport(TransportNumber), item INT REFERENCES Itemss(code),plandQ INT ,actualQ INT ,PRIMARY KEY (shop,Supplier,Transport,item));";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE TrucksTrnsportSigning  (truck VARCHAR(10) REFERENCES Trucks(Plate), transport INT REFERENCES Transport(TransportNumber), PRIMARY KEY (truck,transport));";
            stmt.executeUpdate(sql);
            sql= "CREATE TABLE driverAsiignmetns (truck VARCHAR(10) REFERENCES Trucks(Plate), transport INT PRIMARY KEY REFERENCES Transport(TransportNumber) ,driver INT REFERENCES Drivers(ID));";
            stmt.executeUpdate(sql);

            //********************************************************************************************************



            /*Creating Tables if they are NOT existed */

            /*
                Suppliers Table : ID, Name, BankNum, BranchBum, AccountNum, Payment, DeliveryMethod, SupplyTime, Address.
             */
            //TODO:omri&shahar: id referecne to Site, need to remove all what we got in site -address
            sql = "CREATE TABLE IF NOT EXISTS Suppliers " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " Name          TEXT    NOT NULL, " +
                    " BankNum          INT    NOT NULL, " +
                    " BranchNum        INT    NOT NULL, " +
                    " AccountNum	   INT    NOT NULL, " +
                    " Payment         TEXT	NOT NULL," +
                    " DeliveryMethod TEXT NOT NULL," + //SOMEONE BRING ME OR I BRING FROM SOMEONE
                    " SupplyTime TEXT," + //DAYS
                    " Address TEXT NOT NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Contacts Table : ID, Full name, Phone Number, Email.
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Contacts " +
                    "(ID   TEXT NOT NULL," +
                    "SupplierID INT  NOT NULL," +
                    " FullName   TEXT  NOT NULL, " +
                    " PhoneNumber TEXT NOT NULL, " +
                    " Email	TEXT," +
                    "PRIMARY KEY(SupplierID, ID),"+
                    "FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
            stmt.execute(sql);
            stmt.close();

            /*
                Category : ID, Name, ID_Father. When ID_father is -1, that category has no father.
             */
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS CATEGORY " +
                    "(ID INT PRIMARY KEY     NOT NULL ," +
                    " NAME           CHAR(50) NOT NULL, " +
                    " ID_FATHER  INT DEFAULT NULL REFERENCES CATEGORY(ID) " +
                    " ON UPDATE CASCADE ON DELETE SET NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Items : ID, Name, CategoryNumber, Manufacture.
             */
            stmt = c.createStatement();
            sql = "  CREATE TABLE IF NOT EXISTS Items " +
                    " (ID   INT PRIMARY KEY  NOT NULL," +
                    " NAME   TEXT NOT NULL, " +
                    " Weight REAL NOT NULL," +
                    " CategoryNumber       INT    REFERENCES CATEGORY(ID) ON DELETE SET NULL ON UPDATE CASCADE, " +
                    " Manufacture          TEXT    NOT NULL);";
            stmt.execute(sql);
            stmt.close();


            /*
                SuppliersItems : SupplierID, ItemID, CatalogNumber, Cost, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS SupplierItems " +
                    "(SupplierID INT   NOT NULL," +
                    " ItemID INT  NOT NULL," +
                    " CatalogNumber INT NOT NULL,"+
                    " Cost REAL  NOT NULL, " +
                    " PRIMARY KEY(SupplierID,ItemID)," +
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            /*
                Discounts : SupplierID, ItemID, Quantity, DiscountPercentage, SupplierID(FR), ItemID(FR)
             */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Discounts " +
                    "(SupplierID INT  NOT NULL," +
                    " ItemID INT   NOT NULL," +
                    " Quantity INT NOT NULL,"+
                    " DiscountPercentage INT  NOT NULL, " +
                    " PRIMARY KEY (SupplierID, ItemID, Quantity),"+
                    " FOREIGN KEY(SupplierID) REFERENCES Suppliers(ID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON DELETE CASCADE ON UPDATE CASCADE); " ;
            stmt.execute(sql);
            stmt.close();


            //TODO:omri&shahar: need to add shop number ref to shop
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS Orders " +
                    "(OrderID INT PRIMARY KEY  NOT NULL," +
                    " SupplierID INT   NOT NULL," +
                    " Date  DATE  NOT NULL, " +
                    " ContactID TEXT  NOT NULL, " +
                    " ArrivalDate Date DEFAULT NULL," +
                    " OrderFrequency INT NOT NULL DEFAULT 0," +
                    " FOREIGN KEY(SupplierID , ContactID) REFERENCES Contacts(SupplierID, ID) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            stmt = c.createStatement();
            sql =    "CREATE TABLE IF NOT EXISTS OrdersItems " +
                    "(OrderID INT  NOT NULL," +
                    " ItemID INT NOT NULL,"+
                    " Quantity INT  NOT NULL," +
                    " FinalCost REAL  NOT NULL, " +
                    "PRIMARY KEY(OrderID,ItemID), "+
                    " FOREIGN KEY(OrderID) REFERENCES Orders(OrderID) ON UPDATE CASCADE ON DELETE CASCADE,"+
                    " FOREIGN KEY(ItemID) REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE);";
            stmt.execute(sql);
            stmt.close();


            /*
                Quantities : ItemID, Location, Defects, Warehouse, Minimum, Store, Order. (Current = Store+Warehouse+Defects)
             */
            //TODO:omri&shahar: add supp in shops ref
            stmt = c.createStatement();
            sql =   "CREATE TABLE IF NOT EXISTS QUANTITIES " +
                    "(ItemID INT REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE ," +
                    "LOCATION TEXT NOT NULL," +
                    "MINIMUM INT NOT NULL," +
                    "ORDER_AMOUNT INT DEFAULT 0," +
                    "WAREHOUSE INT NOT NULL," +
                    "STORE INT NOT NULL," +
                    "DEFECTS INT NOT NULL);";
            stmt.execute(sql);
            stmt.close();

            /*
                Prices : ItemID, SellPrice,, Percentage, DateStart, DateEnd.
            */
            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS PRICES" +
                    "(ItemID INT REFERENCES Items(ID) ON UPDATE CASCADE ON DELETE CASCADE ," +
                    "SellPrice REAL NOT NULL," +
                    "Percentage INT,"+
                    "DateStart DATE," +
                    "DateEnd DATE);";
            stmt.execute(sql);
            stmt.close();

            c.commit();
            stmt.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
    }


}