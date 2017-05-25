package SharedClasses;

import java.util.Date;

public class Worker {
	
	private int ID;
	private String lname;
	private String fname;
	private String startDate;
	private String termsOfEmployment;	
	private int salary;
	private String role;
	private int BankNumber;
	private int BankAccountNumber;
	private int WorkPlace;

		
	public Worker(int id, String lname, String fname, String startDate, String termsOfEmployment, int salary, String role,
			int BankNumber, int BankAccountNumber, int WorkPlace) {
		this.ID = id;
		this.lname = lname;
		this.fname = fname;
		this.startDate = startDate;
		this.termsOfEmployment = termsOfEmployment;
		this.salary = salary;
		this.role = role;
		this.BankNumber = BankNumber;
		this.BankAccountNumber = BankAccountNumber;
		this.WorkPlace=WorkPlace;
	}

	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public int getBankNumber() {
		return BankNumber;
	}

	public void setBankNumber(int BankNumber) {
		this.BankNumber = BankNumber;
	}

	public int getBankAccountNumber() {
		return BankAccountNumber;
	}

	public void setBankAccountNumber(int BankAccountNumber) {
		this.BankAccountNumber = BankAccountNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTerms() {
		return termsOfEmployment;
	}

	public void setTerms(String terms) {
		this.termsOfEmployment = terms;
	}

	/**
	 * return the full name of the worker (first and last names)
	 * @return
	 */
	public String getName(){
		return this.fname+" "+this.lname;
	}

	public String toString() {
		return lname + " " + fname + " " + ID + " Salary: " + salary +" Terms Of Employment: "+ termsOfEmployment + " Number of bank branch: "
	+ BankNumber + " Number of bank account: " + BankAccountNumber + " role: " + role + " Work Place: " + WorkPlace;
	}

	public int getWorkPlace() {
		return WorkPlace;
	}

	public void setWorkPlace(int s) {
		this.WorkPlace=s;
	}

}
