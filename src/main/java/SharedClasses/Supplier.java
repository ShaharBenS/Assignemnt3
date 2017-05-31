package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Supplier extends Site {
    private int id;
    private int bankNum;
    private int branchNum;
    private int accountNum;
    private String payment;
    private String deliveryMethod;
    private String supplyTime;
    private Contact[] Contacts;

    //private String address;

    public Supplier(int id,String name, int bankNum, int branchNum, int accountNum, String payment, String deliveryMethod, String supplyTime, String address) {
        super(id,name,address,"","");
        this.id = id;
        this.bankNum = bankNum;
        this.branchNum = branchNum;
        this.accountNum = accountNum;
        this.payment = payment;
        this.deliveryMethod = deliveryMethod;
        this.supplyTime = supplyTime;
    }
    public Supplier(int id, String name, int bankNum, int branchNum, int accountNum,
                    String payment, String deliveryMethod, String supplyTime, String address,Contact[] contacts) {
        super(id,name,address,"","");
        this.id = id;
        this.bankNum = bankNum;
        this.branchNum = branchNum;
        this.accountNum = accountNum;
        this.payment = payment;
        this.deliveryMethod = deliveryMethod;
        this.supplyTime = supplyTime;
        Contacts = new Contact[contacts.length];
        System.arraycopy(contacts, 0, Contacts, 0, contacts.length);
    }
    public int getId() {
        return super.getCode();
    }

    public void setId(int id) {
        super.code = id;
    }

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public int getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(int branchNum) {
        this.branchNum = branchNum;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getSupplyTime() {
        return supplyTime;
    }
    public int getSupplyTimeInNumber(){
        switch (supplyTime) {
            case "Sunday":
                return 1;
            case "Monday":
                return 2;
            case "Tuesday":
                return 3;
            case "Wednesday":
                return 4;
            case "Thursday":
                return 5;
            case "Friday":
                return 6;
            case "Saturday":
                return 7;
default: return 0;
        }
    }

    public void setSupplyTime(String supplyTime) {
        this.supplyTime = supplyTime;
    }

    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String openingForDoc() {
        return "Collect From:";
    }

    public Contact[] getContacts() {
        Contact [] contactsArray = new Contact[Contacts.length];
        System.arraycopy(Contacts, 0, contactsArray, 0, Contacts.length);
        return contactsArray;
    }

    public void setContacts(Contact[] contacts) {
        Contacts = new Contact[contacts.length];
        System.arraycopy(contacts,0,Contacts,0,contacts.length);
    }

    @Override
    public String toString() {
        String myclass = this.getClass().getSimpleName();
        return myclass + " " + this.code + "\n" +
                "	Address: " + getAddress() + "\n" +
                "	Contacts: \n" ;//TODO: add all contacts (name & phone)

    }

    @Override
    public String change() {
        return " were supposed to be taken from Supplier " + this.getCode();
    }
}