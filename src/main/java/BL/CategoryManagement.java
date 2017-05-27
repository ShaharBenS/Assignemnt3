package BL;

import DAL.Categories;
import DAL.Items;
import DAL.Prices;
import DAL.Quantities;
import SharedClasses.Category;
import SharedClasses.Item;
import javafx.util.Pair;

import java.util.Map;

/**
 * Created by Shahar on 29/03/17.
 */

public class CategoryManagement {

    private Categories _CD;
    private Items ITEMS;
    private Prices PRICES;
    private Quantities QUANTITIES;

    public CategoryManagement(Categories _CD, Items ITEMS, Prices PRICES, Quantities QUANTITIES) {
        this._CD = _CD;
        this.ITEMS = ITEMS;
        this.PRICES = PRICES;
        this.QUANTITIES = QUANTITIES;
    }

    public boolean addCategory(String line) {
        String[] cParts = line.split("\\s+");
        if (cParts.length != 2 && cParts.length != 3) return false;
        Category c;
        try {
            /*0*/
            int id = Integer.parseInt(cParts[0]);
            if (cParts[0].length() != 3) return false;
            /*1*/ /* << NOTHING TO CHECK << */
            int idF = -1;
            if (cParts.length == 3) {
                if (cParts[2].length() != 3) return false;
                else idF = Integer.parseInt(cParts[2]);
            }

            c = new Category(id, cParts[1], idF);

        } catch (Exception e) {
            return false;
        }
                return _CD.addCategory(c);
    }

    /*
        If category not found returns NULL
     */
    public Category getCategory(int id) {
        return _CD.getCategory(id);
    }

    public boolean updateCategoryId(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3 || prop[1].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            int nid = Integer.parseInt(prop[1]);
            return _CD.updateCategoryId(id, nid);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategoryName(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            return _CD.updateCategoryName(id, prop[1]);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategoryIdFather(String line) {
        String[] prop = line.split("\\s+");
        if (prop.length != 2) return false;
        try {
            if (prop[0].length() != 3 || prop[1].length() != 3) return false;
            int id = Integer.parseInt(prop[0]);
            int nid = Integer.parseInt(prop[1]);
            return _CD.updateCategoryIdFather(id, nid);
        } catch (Exception e) {
            return false;
        }
    }

    public String[] productReportbyCategory(String line) {
     try {
         String[] cats = line.split("\\s+");
         Category[] cArr = new Category[cats.length];
         for (int i = 0; i < cArr.length; i++) {
             int id = 0;
             try {
                 id = Integer.parseInt(cats[i]);
             } catch (Exception e) {
                 return new String[]{"1 or more INVALID ID"};
             }
             if (cats[i].length() != 3) return new String[]{"1 or more INVALID ID"};
             cArr[i] = _CD.getCategory(id);
         }
         Item[] items = _CD.getAllProductsbyCat(cArr);
         Map K_ID_V_PRICE = _CD.getAllFinalPricesMap(); //KEY = ID | VALUE = PRICE (DOUBLE)
         if (K_ID_V_PRICE == null) return new String[]{"No Items\n"};

         String[] toStrings = new String[items.length];
         for (int i = 0; i < items.length; i++) // init @param: quantities
         {
             toStrings[i] = "------- FULL ITEM -------\n";
             toStrings[i] += items[i].toString();
             toStrings[i] += QUANTITIES.getQuantity(items[i].getItemID(),BL.shopID).toString();
             toStrings[i] += PRICES.getPrice(items[i].getItemID()).toString();
             toStrings[i] += "Final Cost: " + (Double) K_ID_V_PRICE.get(items[i].getItemID())+"\n";
             toStrings[i] += "------- FULL ITEM -------\n\n";
         }
         return toStrings;
     }
     catch(Exception e) {return new String[]{"No Results Were Found\n"};}
    }

}