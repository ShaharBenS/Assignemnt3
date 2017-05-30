package DAL;

import SharedClasses.SupplierItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SupplierItems {

    Connection c;
    java.sql.Statement stmt;


    public SupplierItems(Connection c) {
        this.c = c;
        stmt = null;
    }

    public boolean addSupplierItem(SupplierItem supItem) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO SupplierItems (SupplierID, ItemID, CatalogNumber, Cost) " +
                    "VALUES (?,?,?,?);");
            ps.setInt(1, supItem.getSupplierID());
            ps.setInt(2, supItem.getItemID());
            ps.setInt(3, supItem.getCatalogNumber());
            ps.setDouble(4, supItem.getCost());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean setSupplierID(int id, int newId) {
        try {
            String sql = "UPDATE SupplierItems SET SupplierID = ? WHERE SupplierID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, newId);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setCatalogNumber(int supId, int ItemID, int CatalogNumber) {
        try {
            String sql = "UPDATE SupplierItems SET CatalogNumber = ? WHERE SupplierID = ? and ItemID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, CatalogNumber);
            pstmt.setInt(2, supId);
            pstmt.setInt(3, ItemID);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setCost(int supId, int itemId, double Cost) {
        try {
            String sql = "UPDATE SupplierItems SET Cost = ? WHERE SupplierID = ? and ItemID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setDouble(1, Cost);
            pstmt.setInt(2, supId);
            pstmt.setInt(3, itemId);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public SupplierItem getSupplierItem(int itemId, int supId) {
        SupplierItem supItem = null;
        try {
            String sqlQuary = "SELECT * FROM SupplierItems WHERE SupplierID = '" + supId + "'and ItemID = '" + itemId + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            supItem = new SupplierItem(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return supItem;
    }

    public int getCatalogNumber(int itemId, int supId){
        int catalog =0;
        try {
            String sqlQuary = "SELECT CatalogNumber FROM SupplierItems WHERE SupplierID = '" + supId + "'and ItemID = '" + itemId + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            catalog = rs.getInt(1);

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return catalog;
    }

    public double getCost(int itemId, int supId){
        double cost =0;
        try {
            String sqlQuary = "SELECT Cost FROM SupplierItems WHERE SupplierID = '" + supId + "'and ItemID = '" + itemId + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            cost = rs.getDouble(1);

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return cost;
    }
    
    public Integer[] getSuppliersID(int itemID){
        ArrayList<Integer> arrayList;
        try {
            String sqlQuery = "SELECT SupplierID FROM SupplierItems WHERE ItemID = " + itemID + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            arrayList = new ArrayList<>();
            while(rs.next())
            {
                arrayList.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception e) { return null; }
        return  arrayList.toArray(new Integer[arrayList.size()]);
    }

    public String getSupplierItems(int supId) {
        String ans = "";
        try {
            String sqlQuary = "SELECT * FROM SupplierItems WHERE SupplierID = '" + supId + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ans += "Supplier ID: " + rs.getInt(1) + "\n";
                ans += "Item ID:" + rs.getInt(2) + "\n";
                ans += "Catalog number:" + rs.getInt(3) + "\n";
                ans += "Cost:" + rs.getDouble(4) + "\n";
                ans += "\n";
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return ans;
    }

    public boolean removeSupplierItem(int itemId, int supId) {
        try {
            String sql = "DELETE FROM SupplierItems WHERE SupplierID = ? and ItemID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, supId);
            pstmt.setInt(2, itemId);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ifExist(int supId, int itemID) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SupplierItems where SupplierID =" + supId + " and ItemID = " + itemID + ";");
            if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }

    }

    public  boolean checkIfItemExist(int ItemID, int suppID){
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SupplierItems where ItemID = " + ItemID + " and ID = "+suppID+";");
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
