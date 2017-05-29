package SharedClasses;

public abstract class Site 
{
	protected int code;
	private String name;
	private String address;
	private String phone;
	private String contact;

	public void setCode(int code){
		this.code = code;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setAddress(String address){
		this.address = address;
	}

	
	protected Site(int code,String name, String address, String phone, String contact) {
		this.code = code;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.contact = contact;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getAddress() {
		return address;
	}
	
	public boolean equals(Site o){
		return this.code == o.code;
	}
	
	public abstract String openingForDoc();
	
	public String toString(){
		String myclass = this.getClass().getSimpleName();
		return myclass + " " + this.code + "\n" +
				"	Address: " + this.address + "\n" +
				"	Contact: " + this.contact + " " + this.phone;
	}
	public String details(){
		String myclass = this.getClass().getSimpleName();
		return myclass + " " + this.code + "\n" +
				"Address: " + this.address + "     " +
				"Contact: " + this.contact + "     " +
				"Phone: " + this.phone;
	}

	public abstract String change();
}
