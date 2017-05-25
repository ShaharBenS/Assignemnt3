package SharedClasses;

public class Bank {

	private String bankName;
	private int bankNumber;
	
	public Bank(String bankName, int bankNumber) {

		this.bankName = bankName;
		this.bankNumber = bankNumber;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(int BankNumber) {
		this.bankNumber = BankNumber;
	}
}
