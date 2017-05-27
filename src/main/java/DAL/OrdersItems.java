package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ProgramLauncher.ProgramLauncher;
import SharedClasses.OrderItem;
import javafx.util.Pair;

/**
 * Created by rotem on 07/04/2017.
 */
public class OrdersItems {

    private Connection c;
    private Statement stmt;

    public OrdersItems(Connection c) {
        this.c = c;
        stmt=null;
    }


    public boolean addOrderItem(OrderItem orderItem) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO OrdersItems (OrderID, ItemID, Quantity, FinalCost) " +
                    "VALUES (?,?,?,?);");
            ps.setInt(1, orderItem.getOrderID());
            ps.setInt(2, orderItem.getItemID());
            ps.setInt(3,orderItem.getQuantity());
            ps.setDouble(4,orderItem.getFinalCost());

            ps.executeUpdate();
            c.commit();
            ps.close();

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Orders.OrderFrequency FROM ORDERS WHERE Orders.OrderID = "+orderItem.getOrderID()+" ;");
            if(rs.getInt(1) > 0)
            {
                Iterator iterator = ProgramLauncher.alreadyWarned.iterator();
                while(iterator.hasNext())
                {
                    OrderItem oi = (OrderItem) iterator.next();
                    if(oi.getItemID() == orderItem.getItemID() && orderItem.getOrderID() == oi.getOrderID())
                    {
                        ProgramLauncher.alreadyWarned.remove(oi);
                    }
                }
                ProgramLauncher.checkPeriodicOrders.interrupt();
            }

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Pair[] getAllFinalPrices()
    {
        Pair[] allProducts;
        List<Pair> list = new ArrayList<>();
        try {
            String sqlQuary = "SELECT OI.ItemID, OI.FinalCost " +
                            "FROM OrdersItems as OI CROSS JOIN Orders as O " +
                            "WHERE OI.OrderID = O.OrderID " +
                            "GROUP BY OI.ItemID " +
                            "HAVING O.Date >=   (SELECT Max(Orders.Date) " +
                                                "FROM OrdersItems CROSS JOIN Orders " +
                                                "WHERE OrdersItems.OrderID = Orders.OrderID AND ItemID = OI.ItemID " +
                                                "GROUP BY ItemID);";
            Statement stmt1 = c.createStatement();
            ResultSet rs = stmt1.executeQuery(sqlQuary);
            int count = 0;
            while(rs.next())
            {
                count++;
                list.add(new Pair(rs.getInt(1),rs.getDouble(2)));
            }
            allProducts = new Pair[count];
            allProducts = list.toArray(allProducts);

            rs.close();
            stmt1.close();
        } catch (Exception e) { return null; }
        return allProducts;
    }
    
    public boolean setQuantity(int orderID,int itemID,int quantity){
   	 try {
            String sql = "UPDATE OrdersItems SET quantity = ? WHERE OrderID = ? and ItemID=?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, orderID);
            pstmt.setInt(3, itemID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
   }
    
    public OrderItem[] getOrderItems(int orderID){
    	OrderItem[] orderItems = null;
        try {
            String sqlQuary = "SELECT * FROM OrdersItems WHERE OrderID = " + orderID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            int count = 0;
            List<OrderItem> orderItemsList = new ArrayList<>();
            while(rs.next())
            {
                count++;
                orderItemsList.add(new OrderItem(rs.getInt("OrderID"),
                        rs.getInt("ItemID"), rs.getInt("Quantity"),rs.getDouble("FinalCost")));
            }
            orderItems= new OrderItem[count];
            orderItems = orderItemsList.toArray(orderItems);
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return orderItems;
    }

    public boolean removeOrderItem(int orderID, int itemID){
    	try {
            String sql = "DELETE FROM OrdersItems WHERE OrderID = ? and ItemID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, orderID);
            pstmt.setInt(2,itemID );
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkItemExistInOrder(int orderID, int itemID){
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OrdersItems where OrderID = '" + orderID + "' and itemID = '" + itemID + "';");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isItemOrdered(int itemID){
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM OrdersItems as oi CROSS JOIN Orders as o where o.OrderID = oi.OrderID and oi.itemID = " + itemID + " and o.ArrivalDate IS NULL");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }


}
