package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Contact
{
    private String id;
    private int supplierId;
    private String fullName;
    private String phoneNumber;
    private String email;

    public Contact(String id, int supplierId, String fullName, String phoneNumber, String email) {
        this.id = id;
        this.supplierId = supplierId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSupplierID() {
        return supplierId;
    }

    public void setSupplierID(int id) {
        this.supplierId = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}