package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Supplier extends Site {
    // private int id;
    private String name;
    private int bankNum;
    private int branchNum;
    private int accountNum;
    private String payment;
    private String deliveryMethod;
    private String supplyTime;
    //private String address;
//TODO:: add contacts array
    public Supplier(int id, String name, int bankNum, int branchNum, int accountNum, String payment, String deliveryMethod, String supplyTime, String address) {
        super(id,address,"","");
        this.name = name;
        this.bankNum = bankNum;
        this.branchNum = branchNum;
        this.accountNum = accountNum;
        this.payment = payment;
        this.deliveryMethod = deliveryMethod;
        this.supplyTime = supplyTime;
    }

    public int getId() {
        return super.getCode();
    }

    public void setId(int id) {
        super.code = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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