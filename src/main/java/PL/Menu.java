package PL;

import javax.naming.SizeLimitExceededException;
import java.util.Scanner;

/**
 * Created by Omri on 06-Apr-17.
 */
public class Menu {

    private PL_Stock pl_stock;
    private PL_Supplier pl_sup;
    private PL_Orders pl_ord;
    private Scanner scanner;

    private final String[] MENU =
            {   "Choose an Option:",
                    "1) Supplier Management",
                    "2) Stocks Management",
                    "3) Order Management",
                    "4) Exit"};


    public Menu(PL_Stock pl_stock, PL_Supplier pl_sup, PL_Orders pl_ord) {
        this.pl_stock = pl_stock;
        this.pl_sup = pl_sup;
        this.pl_ord= pl_ord;
        this.scanner = new Scanner(System.in);
    }


    public void start()
    {
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
    }

}
