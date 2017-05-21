package SharedClasses;

/**
 * Created by keren on 4/6/2017.
 */
public class Supplier {
    private int id;
    private String name;
    private int bankNum;
    private int branchNum;
    private int accountNum;
    private String payment;
    private String deliveryMethod;
    private String supplyTime;
    private String address;

    public Supplier(int id, String name, int bankNum, int branchNum, int accountNum, String payment, String deliveryMethod, String supplyTime, String address){
        this.id = id;
        this.name = name;
        this.bankNum = bankNum;
        this.branchNum = branchNum;
        this.accountNum = accountNum;
        this.payment = payment;
        this.deliveryMethod = deliveryMethod;
        this.supplyTime = supplyTime;
        this.address=address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    
    public String getAddress(){
    	return address;
    }
    
    public void setAddress(String address){
    	this.address=address;
    }

}