package DAL;

import SharedClasses.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Discounts {
    Connection c;
    java.sql.Statement stmt;


    public Discounts(Connection c) {
        this.c = c;
        stmt = null;
    }

    public boolean addDiscount(Discount dis) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Discounts (SupplierID, ItemID, Quantity, DiscountPercentage) " +
                    "VALUES (?,?,?,?);");
            ps.setInt(1, dis.getSupplierID());
            ps.setInt(2, dis.getItemID());
            ps.setInt(3, dis.getQuantity());
            ps.setInt(4, dis.getDiscountPercentage());
            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    public boolean setDiscountPercentage(int supId, int itemId,int quantity, int DiscountPercentage) {
        try {
            String sql = "UPDATE Discounts SET DiscountPercentage = ? WHERE SupplierID = ? and ItemID = ? and Quantity=?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, DiscountPercentage);
            pstmt.setInt(2, supId);
            pstmt.setInt(3, itemId);
            pstmt.setInt(4, quantity);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public Discount getDiscount(int supplirId, int itemId, int quantity) {
        Discount dis = null;
        try {
            String sqlQuary = "SELECT * FROM Discounts WHERE SupplierID = " + supplirId + " and ItemID = " + itemId + " and Quantity = " + quantity + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            dis = new Discount(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return dis;
    }

    public String getDiscounts(int supId, int ItemId) {
        String ans = "";
        try {
            String sqlQuary = "SELECT * FROM Discounts WHERE SupplierID = " + supId + " and ItemID = " + ItemId + ";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ans += "Supplier ID: " + rs.getInt(1) + "\n";
                ans += "Item ID:" + rs.getInt(2) + "\n";
                ans += "Quantity:" + rs.getInt(3) + "\n";
                ans += "Discount percentage:" + rs.getInt(4) + "\n";
                ans += "\n";
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return ans;
    }


    public int getDiscountPer( int supId,int itemId, int quantity){
        int discount =0;
        try {
            String sqlQuary = "SELECT MAX(DiscountPercentage) " +
                    "FROM Discounts WHERE SupplierID = " + supId + " and ItemID = " + itemId +  " and Quantity <= " + quantity +";";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            if(rs.next())
              discount = rs.getInt(1);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            return 0;
        }
        return discount;
    }

    public boolean removeDiscount(int supplirId, int itemId, int quantity) {
        try {
            String sql = "DELETE FROM Discounts WHERE SupplierID = ? and ItemID = ? and Quantity = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, supplirId);
            pstmt.setInt(2, itemId);
            pstmt.setInt(3, quantity);
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

    public boolean ifExist(int supID, int itemID, int Quantity) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Discounts where SupplierID = " + supID + " and ItemID = " + itemID + " and Quantity = " + Quantity + ";");
            if (rs.next()) {
                stmt.close();
                rs.close();
                return true;
            } else return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


}
