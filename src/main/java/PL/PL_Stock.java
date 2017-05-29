package PL;

import java.util.Scanner;

import BL.CategoryManagement;
import BL.PriceManagement;
import BL.ProductManagement;
import BL.SupplierBL;


public class PL_Stock
{
    private Scanner scanner = new Scanner(System.in);
    private ProductManagement ProductM;
    private PriceManagement PriceM;
    private CategoryManagement CategoryM;
    private SupplierBL SBL;

    private final String[] SUPER_MENU = {"Choose an option:" ,
            "1) Add Item / Category",
            "2) Update Item / Category",
            "3) More Tools",
            "4) Back"};
    private final String[] ADD_MENU = {"Choose an option:" ,
            "1) Add new Item" ,
            "2) Add new Category",
            "3) Back"
            };

    private final String[] UPDATE_MENU =
            {"Choose an option:",
            " 1) CATEGORY SECTION",
            " 2) ITEM SECTION",
            " 3) Back"
    };

    private final String [] CATEGORY_UPDATE_MENU = {
            " 1) Update category ID",
            " 2) Update category Name",
            " 3) Update category Father ID",
            " 4) Back"};
    private final String [] ITEM_UPDATE_MENU = {
            " 1) Update item ID",
            " 2) Update item Location",
            " 3) Update item Manufacture",
            " 4) Update Warehouse Stock From Arrived Order",
            " 5) Update item Amount in Store",
            " 6) Update item Minimal Amount",
            " 7) Update item Amount of Defects",
            " 8) Update item Category Code",
            " 9) Update item Order Amount",
            "10) Update item Sell Price",
            "12) Update item weight",
            "13) Update item Description",
            "14) Back"};


    private final String[] TOOLS_MENU = {"Choose an option:" ,
            "1) Add new Discount",
            "2) Stock report by item ID",
            "3) Stock report by category/ies",
            "4) Defect Items report",
            "5) Show all Items",
            "6) Back"
    };


    public PL_Stock(ProductManagement pm, PriceManagement price_m, CategoryManagement cm,SupplierBL sbl)
    {
        this.CategoryM = cm;
        this.PriceM = price_m;
        this.ProductM = pm;
        this.SBL = sbl;
    }

    /*
        This method starts reading input and
        stops when user wishes to stop.
     */
    public void start()
    {
        int operation;

        while(true)
        {
            System.out.println(SUPER_MENU[0]);
            System.out.println(SUPER_MENU[1]);
            System.out.println(SUPER_MENU[2]);
            System.out.println(SUPER_MENU[3]);
            System.out.println(SUPER_MENU[4]);


            try { operation = Integer.parseInt(scanner.nextLine()); }
            catch(Exception r) { System.out.print("Invalid operation. Please try again\n\n"); continue; }

            String prop;
            switch(operation)
            {
                case 1:
                {
                    ADD_MENU();
                    break;
                }
                case 2:
                {
                    UPDATE_MENU();
                    break;
                }
                case 3:
                {
                    TOOLS_MENU();
                    break;
                }
                case 4:
                {
                    return;
                }
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;

            }
            //Read Input:
        }
    }
    private void ADD_MENU()
    {
        int operation;
        while(true)
        {
            System.out.println(ADD_MENU[0]);
            System.out.println(ADD_MENU[1]);
            System.out.println(ADD_MENU[2]);
            System.out.println(ADD_MENU[3]);


            try { operation = Integer.parseInt(scanner.nextLine()); }
            catch(Exception r) { System.out.print("Invalid operation. Please try again\n\n"); continue; }

            switch(operation)
            {
                case 1:
                {
                    System.out.print("Enter the Items properties (separated by 1 space!) in the following structure:\n" +
                            "[ID] [LOCATION] [MANUFACTURE] [MINIMAL AMOUNT] [CATEGORY CODE] [NAME] [SELL PRICE] [WEIGHT]\n");
                    String ItemLine = scanner.nextLine();
                    if (ProductM.addItem(ItemLine))
                        System.out.print(" >> item added successfully\n");
                    else System.out.print(" >> Invalid arguments. Try again\n");
                    break;
                }
                case 2:
                {
                    System.out.print("Enter the Category properties (separated by 1 space!) in the following structure:\n" +
                            "[ID] [NAME] [ID FATHER - optional]\n");
                    String categoryLine = scanner.nextLine();
                    if (CategoryM.addCategory(categoryLine))
                        System.out.print(" >> Category added successfully\n");
                    else System.out.print(" >> Invalid arguments. Try again\n");
                    break;
                }
                case 3:
                {
                    return;
                }
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;
            }
        }
    }

    private void UPDATE_MENU()
    {
        int operation;
        while(true)
        {
            System.out.println(UPDATE_MENU[0]);
            System.out.println(UPDATE_MENU[1]);
            System.out.println(UPDATE_MENU[2]);

            try
            {
                operation = Integer.parseInt(scanner.nextLine());
            } catch (Exception r)
            {
                System.out.print("Invalid operation. Please try again\n\n");
                continue;
            }
            String prop;

            switch (operation)
            {
                case 1:
                    CATEGORY_UPDATE_MENU();
                case 2:
                    ITEM_UPDATE_MENU();
                case 3:
                    return;

                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;
            }
        }
    }
    private void CATEGORY_UPDATE_MENU()
    {
        int operation;

        while(true)
        {
            System.out.println(CATEGORY_UPDATE_MENU[0]);
            System.out.println(CATEGORY_UPDATE_MENU[1]);
            System.out.println(CATEGORY_UPDATE_MENU[2]);
            System.out.println(CATEGORY_UPDATE_MENU[3]);


            try { operation = Integer.parseInt(scanner.nextLine()); }
            catch(Exception r) { System.out.print("Invalid operation. Please try again\n\n"); continue; }

            String prop;
            switch(operation)
            {
                case 1:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryId(prop));
                    break;
                case 2:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW NAME]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryName(prop));
                    break;
                case 3:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID FATHER]\n");
                    prop = scanner.nextLine();
                    printUpdate(CategoryM.updateCategoryIdFather(prop));
                    break;
                case 4:
                {
                    return;
                }
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;

            }
            //Read Input:
        }
    }
    private void ITEM_UPDATE_MENU()
    {
        int operation;

        while(true)
        {
            System.out.println(ITEM_UPDATE_MENU[0]);
            System.out.println(ITEM_UPDATE_MENU[1]);
            System.out.println(ITEM_UPDATE_MENU[2]);
            System.out.println(ITEM_UPDATE_MENU[3]);
            System.out.println(ITEM_UPDATE_MENU[4]);
            System.out.println(ITEM_UPDATE_MENU[5]);
            System.out.println(ITEM_UPDATE_MENU[6]);
            System.out.println(ITEM_UPDATE_MENU[7]);
            System.out.println(ITEM_UPDATE_MENU[8]);
            System.out.println(ITEM_UPDATE_MENU[9]);
            System.out.println(ITEM_UPDATE_MENU[10]);
            System.out.println(ITEM_UPDATE_MENU[11]);
            System.out.println(ITEM_UPDATE_MENU[12]);

            try { operation = Integer.parseInt(scanner.nextLine()); }
            catch(Exception r) { System.out.print("Invalid operation. Please try again\n\n"); continue; }

            String prop;
            switch(operation)
            {
                case 1:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ID]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemId(prop));
                    break;
                case 2:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW LOCATION]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemLocation(prop));
                    break;
                case 3:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MANUFACTURE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemManufacture(prop));
                    break;
                case 4:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[Arriving Order ID] [Arrival Date] ** Date: DD.MM.YYYY **\n");
                    prop = scanner.nextLine();
                    printUpdate(SBL.setOrderArrivalDate(prop));
                    break;
                case 5:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT IN STORE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemAmountInStore(prop));
                    break;
                case 6:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW MINIMAL AMOUNT]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemMinimalAmount(prop));
                    break;
                case 7:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW AMOUNT OF DEFECTS]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemDefectAmount(prop));
                    break;
                case 8:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW CATEGORY CODE]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateItemCategoryCode(prop));
                    break;
                case 9:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW ORDER AMOUNT]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateOrderAmount(prop));
                    break;
                case 10:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW SELL PRICE]\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.updateSellPrice(prop));
                    break;
                case 11:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW WEIGHT]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateWeight(prop));
                    break;
                case 12:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID] [NEW DESCRIPTION]\n");
                    prop = scanner.nextLine();
                    printUpdate(ProductM.updateDescription(prop));
                    break;
                case 13:
                    return;
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;

            }
            //Read Input:
        }
    }

    private void TOOLS_MENU()
    {
        int operation;
        while(true)
        {
            System.out.println(TOOLS_MENU[0]);
            System.out.println(TOOLS_MENU[1]);
            System.out.println(TOOLS_MENU[2]);
            System.out.println(TOOLS_MENU[3]);
            System.out.println(TOOLS_MENU[4]);
            System.out.println(TOOLS_MENU[5]);
            System.out.println(TOOLS_MENU[6]);


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
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID-of item or category] [DISCOUNT(in %)] [START DATE] [END DATE] **DATE FORM: DD.MM.YYYY\n");
                    prop = scanner.nextLine();
                    printUpdate(PriceM.addDiscount(prop));
                    break;
                case 2:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID]\n");
                    prop = scanner.nextLine();
                    System.out.print(ProductM.ItemReport(prop));
                    break;
                case 3:
                    System.out.print("Enter properties in the following structure:\n" +
                            "[ID CATEGORY 1] [ID CATEGORY 2] ...... [ID CATEGORY n]\n");
                    prop = scanner.nextLine();
                    String[] productsbyCat = CategoryM.productReportbyCategory(prop);
                    for (String aProductsbyCat : productsbyCat) System.out.print(aProductsbyCat);
                    break;
                case 4:
                    String[] prodDef = ProductM.getAllDefectProducts();
                    for (String aProdDef : prodDef) System.out.print(aProdDef);
                    break;
                case 5:
                    String[] prod = ProductM.getAllItems();
                    for (String aProd : prod) System.out.print(aProd);
                    break;
                case 6:
                    return;
                default: System.out.print("Invalid Operation!!! Try again...\n\n\n");
                    break;
            }
        }
    }

    private void printUpdate(boolean arg)
    {
        if(arg) System.out.print(" >> Updated successfully\n");
        else System.out.print(" >> Invalid arguments. Try again\n");
    }
}
