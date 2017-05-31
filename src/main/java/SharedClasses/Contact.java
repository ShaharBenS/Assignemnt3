package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Contact
{
    private String id;
    private int SiteID;
    private String fullName;
    private String phoneNumber;
    private String email;

    public Contact(String id, int SiteID, String fullName, String phoneNumber, String email) {
        this.id = id;
        this.SiteID = SiteID;
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

    public int getSiteID() {
        return SiteID;
    }

    public void setSiteID(int id) {
        this.SiteID = id;
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

    @Override
    public String toString()
    {
        String ans ="";
        ans+="Supplier ID: " + getSiteID()+ "\n";
        ans+= "Contact ID: " + getId() + "\n";
        ans += "Name: " + getFullName() + "\n";
        ans += "Phone number: " + getPhoneNumber() + "\n";
        ans += "Email: " + getEmail() + "\n";
        return ans;
    }
}