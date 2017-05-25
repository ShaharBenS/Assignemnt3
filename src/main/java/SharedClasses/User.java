package SharedClasses;

public class User {

	private int ID;
	private String role;
	private int workplace;


	public User(int ID, String role) {
		this.ID = ID;
		this.role = role;
	}
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public int getWorkplace() {
		return workplace;
	}
}
