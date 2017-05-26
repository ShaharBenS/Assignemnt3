package DAL;

import java.sql.*;

import SharedClasses.Date;
import SharedClasses.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rotem on 07/04/2017.
 */
public class Orders {

    private Connection c;
    private Statement stmt;

    public Orders(Connection c) {
        this.c = c;
        stmt=null;
    }

    public boolean addOrder(Order order) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Orders (OrderID,ShopID, SupplierID, Date, ContactID,OrderFrequency) " +
                    "VALUES (?,?,?,?,?,?);");
            ps.setInt(1, order.getOrderID());
            ps.setInt(2,order.getShopID());
            ps.setInt(3, order.getSupplier());
            ps.setString(4,order.getDate().toString());
            ps.setString(5,order.getContactID());
            ps.setInt(6,order.getFrequency());

            ps.executeUpdate();
            c.commit();
            ps.close();


            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public boolean setContactID(int orderID,String conID){
    	 try {
             String sql = "UPDATE Orders SET ContactID = ? WHERE OrderID = ?";

             PreparedStatement pstmt = c.prepareStatement(sql);

             // set the corresponding param
             pstmt.setString(1, conID);
             pstmt.setInt(2, orderID);
             // update
             pstmt.executeUpdate();

             c.commit();
             pstmt.close();
             return true;
         } catch (SQLException e) {
             return false;
         }
    }
    
    public Order getOrder(int orderID){
    	Order order = null;
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE OrderID = " + orderID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            order = new Order(rs.getInt(1),rs.getInt(2),rs.getInt(3),
                    new Date(rs.getString(4)),rs.getString(5),rs.getInt(6));
            rs.close();
            stmt.close();
        } catch (Exception ignored) {
        }
        return order;
    }
    
    public List<Order> getOrderSup(int supID){
    	List<Order> ordersSup = new ArrayList<>();
        try {
            String sqlQuary = "SELECT * FROM Orders WHERE SupplierID = " + supID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()){
                ordersSup.add(new Order(rs.getInt(1),rs.getInt(2),rs.getInt(3)
                ,new Date(rs.getString(4)),rs.getString(5),rs.getInt(6)));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return ordersSup;
    }
    
    public boolean removeOrder(int orderID){
    	try {
            String sql = "DELETE FROM Orders WHERE OrderID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, orderID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean checkOrderExist(int orderID){
    	try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Orders where OrderID = '" + orderID + "';");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean setArrivalDate(int i,Date newDate)
    {
        try {
            String sql = "UPDATE Orders SET ArrivalDate = ? WHERE OrderID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, newDate == null ? null : newDate.toString());
            pstmt.setInt(2, i);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Date getArrivalDate(int id)
    {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Orders.ArrivalDate FROM Orders where OrderID = '" + id + "';");

            java.sql.Date sql_date = rs.getDate("ArrivalDate");
            if(sql_date == null)
            {
                return null;
            }
            else
            {
                return new Date(sql_date);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public int getHighestOrderID()
    {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(Orders.OrderID) FROM Orders;");
            return rs.getInt(1);

        } catch (Exception e)
        {
            return 0;
        }
    }

    public Order[] getPeriodicOrders()
    {
        List<Order> orderList = new ArrayList<>();
        Order[] ordersArray;
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS WHERE ORDERS.OrderFrequency > 0;");
            int count = 0;
            while(rs.next())
            {
                count++;
                orderList.add(new Order(rs.getInt("OrderID"),rs.getInt("ShopID"),rs.getInt("SupplierID"),
                        new Date(rs.getString("Date")), rs.getString("ContactID"),
                        rs.getInt("OrderFrequency")));
            }
            ordersArray = new Order[count];
            ordersArray = orderList.toArray(ordersArray);
            return ordersArray;

        } catch (Exception e)
        {
            ordersArray = new Order[0];
            return ordersArray;
        }
    }

    public boolean setDate(int orderID, Date date)
    {
        try {
            String sql = "UPDATE Orders SET Date = ? WHERE OrderID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, date.toString());
            pstmt.setInt(2, orderID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public int getFrequency(int orderID)
    {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT OrderFrequency FROM Orders WHERE OrderID = "+orderID+";");
            return rs.getInt(1);

        } catch (Exception e)
        {
            return 0;
        }
    }
}
