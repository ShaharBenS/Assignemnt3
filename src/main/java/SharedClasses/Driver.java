package SharedClasses;

public class Driver extends Worker
{
	private int  license;
	
	public Driver(int id, String lname, String fname, String startDate, String termsOfEmployment, int salary, String role, int BankNumber, int BankAccountNumber, int license,int shop) {
		super(id, lname, fname, startDate, termsOfEmployment, salary, "Driver",BankNumber, BankAccountNumber,shop);
		this.license = license;
	}

	public Driver(Worker w,int license){
		super(w.getId(),w.getLname(),w.getFname(),w.getStartDate(),w.getTerms(),w.getSalary(),"Driver",w.getBankNumber(),w.getBankAccountNumber(),w.getWorkPlace());
		this.license=license;
	}

	public int getLicense() {
		return license;
	}

	public void setLicense(int license) {
		this.license = license;
	}

	public String toString() {
		return super.toString()+" license:"+license;
	}
}
