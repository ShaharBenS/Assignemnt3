package DAL;

import BL.BL;
import SharedClasses.Quantity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shahar on 21/04/17.
 */
public class Quantities
{
    private Connection conn;

    public Quantities(Connection conn)
    {
        this.conn = conn;
    }

    public boolean addItemQuantity(Quantity quantity)
    {
        try
        {
            PreparedStatement p_stmt = conn.prepareStatement("INSERT INTO Quantities(ItemID,ShopID,LOCATION,MINIMUM,ORDER_AMOUNT,WAREHOUSE," +
                    "STORE,DEFECTS) VALUES(?,?,?,?,?,?,?,?);");
            p_stmt.setInt(1,quantity.getItemID());
            p_stmt.setInt(2,quantity.getShopID());
            p_stmt.setString(3,quantity.getLocation());
            p_stmt.setInt(4,quantity.getMinimum());
            p_stmt.setInt(5,quantity.getAmount_to_order());
            p_stmt.setInt(6,quantity.getWarehouse());
            p_stmt.setInt(7,quantity.getStore());
            p_stmt.setInt(8,quantity.getDefects());
            p_stmt.executeUpdate();

            conn.commit();
            p_stmt.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    /*
        returning Quantities object by given id, or null if not exist
        when shopID is -1 , only items on that shop will be returned
    */
    public Quantity getQuantity(int id,int shopID)
    {
        Quantity q = null;

        try
        {
            String query = "SELECT * FROM QUANTITIES AS Q WHERE Q.ItemID = "+id +
                    shopID == -1 ? ";" : " AND Q.ShopID = "+shopID+";";
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            q = new Quantity(resultSet.getInt("ItemID"),resultSet.getInt("ShopID"),resultSet.getString("LOCATION"),
                    resultSet.getInt("DEFECTS"),resultSet.getInt("WAREHOUSE"),resultSet.getInt("MINIMUM"),
                    resultSet.getInt("STORE"),resultSet.getInt("ORDER_AMOUNT"));
            stmt.close();
        }
        catch (Exception e)
        {
            return null;
        }

        return q;
    }

    /*
        This method will update 'fieldName' to 'newValue'.
     */
    private boolean updateField(String fieldName, int itemID, Object newValue)
    {
        String query = "UPDATE QUANTITIES SET "+fieldName+" = '"+newValue+"' WHERE ItemID = "+itemID+" AND ShopID = "+BL.shopID+";";
        try
        {
            Statement stmt = conn.createStatement();
            boolean ans = stmt.executeUpdate(query) > 0;
            conn.commit();
            stmt.close();
            return ans;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    public boolean updateLocation(int id, String newLocation)
    {
        return updateField("LOCATION",id,newLocation);
    }

    public boolean updateMinimum(int id, int minimum_amount)
    {
        return updateField("MINIMUM",id,minimum_amount);
    }
    public boolean updateOrder(int id, int order_amount)
    {
        return updateField("ORDER_AMOUNT",id,order_amount);
    }

    public boolean updateWarehouse(int id, int warehouse_amount)
    {
        return updateField("WAREHOUSE",id,warehouse_amount);
    }

    public boolean updateStore(int id, int store_amount)
    {
        return updateField("STORE",id,store_amount);
    }

    public boolean updateDefects(int id, int defects_amount)
    {
        return updateField("DEFECTS",id,defects_amount);
    }


    /*
        Returns all defects Items
        When shopID is -1, all defects Items from all shops are returned.
     */
    public Quantity[] getAllDefectItems(int shopID)
    {
        Quantity[] items = null;
        List<Quantity> itemList = new ArrayList<>();
        String query =  shopID == -1 ? "SELECT * FROM QUANTITIES WHERE DEFECTS > 0;" :
                "SELECT * FROM QUANTITIES WHERE DEFECTS > 0 AND ShopID = " + shopID+";";

        try
        {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            int index = 0;
            while(result.next())
            {
                Quantity quantity = buildQuantityFromResultSet(result);
                if(quantity == null)
                {
                    return null;
                }
                itemList.add(quantity);
                index++;
            }
            items = new Quantity[index];
            return itemList.toArray(items);

        } catch (SQLException e)
        {
            return null;
        }
    }

    private Quantity buildQuantityFromResultSet(ResultSet result)
    {
        try
        {
            return new Quantity(result.getInt("ItemID"),result.getInt("ShopID"),result.getString("LOCATION"),
                    result.getInt("DEFECTS"),result.getInt("WAREHOUSE"),result.getInt("MINIMUM"),
                    result.getInt("STORE"),result.getInt("ORDER_AMOUNT"));
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
