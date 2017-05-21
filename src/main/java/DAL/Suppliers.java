package DAL;

import SharedClasses.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Suppliers {
    Connection c;
    java.sql.Statement stmt;


    public Suppliers(Connection c) {
        this.c = c;
        stmt = null;
    }

    public boolean addSupplier(Supplier sup) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Suppliers (ID, Name, BankNum, BranchNum, AccountNum, Payment, DeliveryMethod, SupplyTime, Address) " +
                    "VALUES (?,?,?,?,?,?,?,?,?);");

            ps.setInt(1, sup.getId());
            ps.setString(2, sup.getName());
            ps.setInt(3, sup.getBankNum());
            ps.setInt(4, sup.getBranchNum());
            ps.setInt(5, sup.getAccountNum());
            ps.setString(6, sup.getPayment());
            ps.setString(7, sup.getDeliveryMethod());
            ps.setString(8, sup.getSupplyTime());
            ps.setString(9, sup.getAddress());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }


    public boolean setID(int id, int newId) {
        try {
            String sql = "UPDATE Suppliers SET ID = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, newId);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setName(int id, String name){
        try {
            String sql = "UPDATE Suppliers SET Name = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setBankNum(int id, int BankNum) {
        try {
            String sql = "UPDATE Suppliers SET BankNum = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, BankNum);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setBranchNum(int id, int BranchNum) {
        try {
            String sql = "UPDATE Suppliers SET BranchNum = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, BranchNum);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setAccountNum(int id, int AccountNum) {
        try {
            String sql = "UPDATE Suppliers SET AccountNum = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, AccountNum);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean setPayment(int id, String payment) {
        try {
            String sql = "UPDATE Suppliers SET Payment = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, payment);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public boolean setDelivery(int id, String DeliveryMethod) {
        try {
            String sql = "UPDATE Suppliers SET DeliveryMethod = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, DeliveryMethod);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean setSupplyTime(int id, String SupplyTime) {
        try {
            String sql = "UPDATE Suppliers SET SupplyTime = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, SupplyTime);
            pstmt.setInt(2, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            return true;
        } catch (SQLException e)

        {
            System.out.println(e);

            return false;
        }
    }
    
    public boolean setAddress(int id, String address){
    	 try {
             String sql = "UPDATE Suppliers SET Address = ? WHERE ID = ?";

             PreparedStatement pstmt = c.prepareStatement(sql);

             // set the corresponding param
             pstmt.setString(1, address);
             pstmt.setInt(2, id);
             // update
             pstmt.executeUpdate();

             c.commit();
             pstmt.close();
             return true;
         } catch (SQLException e)

         {
             System.out.println(e);

             return false;
         }
    }


    public Supplier getSupplier(int id) {
        Supplier sup = null;
        try {
            String sqlQuary = "SELECT * FROM Suppliers WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);

            sup = new Supplier(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return sup;
    }


    public String getDeliveryMethod(int id) {
        String ans = "";
        try {
            String sqlQuary = "SELECT DeliveryMethod FROM Suppliers WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ans += rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return ans;
    }

    public String getSupplierName(int id){
        String ans = "";
        try {
            String sqlQuary = "SELECT Name FROM Suppliers WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ans += rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return ans;
    }

    public boolean removeSupplier(int id) {
        try {

            String sql = "DELETE FROM Suppliers WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, id);
            // update
            pstmt.executeUpdate();

            c.commit();
            pstmt.close();
            stmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean ifExist(int id) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Suppliers where ID =" + id + ";");
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
