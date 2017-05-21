package DAL;

import SharedClasses.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts {

    Connection c;
    java.sql.Statement stmt;


    public Contacts(Connection c) {
        this.c = c;
        stmt = null;
    }


    public boolean addContact(Contact con) {
        try {
            PreparedStatement ps = c.prepareStatement("INSERT INTO Contacts (ID, SupplierID, FullName, PhoneNumber, Email) " +
                    "VALUES (?,?,?,?,?);");
            ps.setString(1, con.getId());
            ps.setInt(2, con.getSupplierID());
            ps.setString(3, con.getFullName());
            ps.setString(4, con.getPhoneNumber());
            ps.setString(5, con.getEmail());

            ps.executeUpdate();
            c.commit();
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }


    public boolean setName(String id, String name) {
        try {
            String sql = "UPDATE Contacts SET FullName = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, id);
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

    public boolean setPhoneNum(String id, String phoneNum) {
        try {
            String sql = "UPDATE Contacts SET PhoneNumber = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, phoneNum);
            pstmt.setString(2, id);
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

    public boolean setEmail(String id, String Email) {
        try {
            String sql = "UPDATE Contacts SET Email = ? WHERE ID = ?";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, Email);
            pstmt.setString(2, id);
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


    public Contact getContact(String id) {
        Contact con = null;
        try {
            String sqlQuary = "SELECT * FROM Contacts WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            con = new Contact(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));

            rs.close();
            stmt.close();
        } catch (Exception e) {
        }
        return con;
    }

    public String getSupplierContact(int id) {
        String ans = "";
        try {
            String sqlQuary = "SELECT * FROM Contacts WHERE SupplierID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            while (rs.next()) {
                ans += "ID: " + rs.getString(1) + "\n";

                ans += "SupplierID: " + rs.getInt(2) + "\n";
                ans += "Full Name: " + rs.getString(3) + "\n";
                ans += "Phone Number: " + rs.getString(4) + "\n";
                ans += "Email: " + rs.getString(5) + "\n";
                ans += "\n";
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("exce");
        }
        return ans;
    }

    public String getContactName(String id) {
        String ans = "";
        try {
            String sqlQuary = "SELECT FullName FROM Contacts WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            ans = rs.getString(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public String getContactPhone(String id) {
        String ans = "";
        try {
            String sqlQuary = "SELECT PhoneNumber FROM Contacts WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            ans = rs.getString(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public String getContactEmail(String id) {
        String ans = "";
        try {
            String sqlQuary = "SELECT Email FROM Contacts WHERE ID = '" + id + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            ans = rs.getString(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public String getContactID(int supId) {
        String ans = "";
        try {
            String sqlQuary = "SELECT ID FROM Contacts WHERE SupplierID = '" + supId + "';";
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuary);
            ans = rs.getString(1);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ans;
    }

    public boolean removeContact(String id, int supId) {
        try {
            String sql = "DELETE FROM Contacts WHERE ID = ? AND SupplierID = ?;";

            PreparedStatement pstmt = c.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, id);
            pstmt.setInt(2, supId);
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

    public boolean ifExist(String id) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contacts where ID = '" + id + "';");
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

    public boolean ifExist(int supID, String conID) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Contacts where ID = '" + conID + "' and SupplierID = '" + supID + "';");
            if (rs.next()) {
                stmt.close();
                rs.close();
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }

}
