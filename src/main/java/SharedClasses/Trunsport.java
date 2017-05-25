package SharedClasses;

import java.util.LinkedList;

public class Trunsport 
{
	private String date;
	private String leavTime;
	private int number;
	private double estimatedWeight;
	private LinkedList<Truck> signedTrucks;
	private Truck truck;
	private LinkedList<Mission> missions;
	private Driver driver;

	/**
	 * Contractor
	 * @param date
	 * @param leavTime
	 * @param number
	 */
	public Trunsport(String date, String leavTime, int number) {
		this.date = date;
		this.leavTime = leavTime;
		this.number = number;
		
		this.estimatedWeight = 0;
		this.signedTrucks = new LinkedList<Truck>(); 
		this.missions = new LinkedList<Mission>(); 
	}

	/***********************GETERS*****************************/
	public int getNumber() {return number;}
	public Driver getDriver() {return driver;}
	public LinkedList<Truck> getSignedTrucks() {return signedTrucks;}
	public String getDate() {return date;}
	public void setDate(String date) {this.date = date;}
	public String getLeavTime() {return leavTime;}
	public void setLeavTime(String leavTime) {this.leavTime = leavTime;}
	public double getEstimatedWeight() {return estimatedWeight;}
	public Truck getTruck() {return truck;}
	public LinkedList<Mission> getMissions() {return missions;}
	/**********************************************************/

	public void signTrck(Truck t) throws NituzException
	{
		if (this.signedTrucks.contains(t))
			throw new NituzException(9,"truck allredy sign to this trunsport");
		this.signedTrucks.add(t);
	}
	
	public void addMission(Mission m) throws NituzException
	{
		if (this.signedTrucks.contains(m))
			throw new NituzException(10,"allredy sign to this trunsport");
		
		double temp = this.estimatedWeight + m.getWeight();
		if (this.truck != null && this.truck.getMaxWeight()<temp)
			throw new NituzException(15,"truck " +  this.truck.getPalte() + " is unable to carry the lead: " + this.estimatedWeight);

		this.estimatedWeight = temp;
		this.missions.add(m);
	}
 
	public void selectTruck(Truck t) throws NituzException{
		if (!this.signedTrucks.contains(t))
			throw new NituzException(14,"truck " +  t.getPalte() + " was not registered for this transport");
		if(t.getMaxWeight() > this.estimatedWeight)
			throw new NituzException(15,"truck " +  t.getPalte() + " is unable to carry the lead: " + this.estimatedWeight);
		if (this.driver != null && t.getLicenseType()>driver.getLicense())
			throw new NituzException(24,"driver license and truck license not unfit");
		
		this.truck = t;
	}
	
	
	public void setDriver(Driver driver) throws NituzException {
		if (this.truck != null && driver.getLicense()<this.truck.getLicenseType())
			throw new NituzException(24,"driver license and truck license not unfit");
		
		this.driver = driver;
	}

	public String toString(){
		return "Trunsport Number " + this.number + "\n" +
				"Date: " + this.date + " " + this.leavTime + "\n" +
				"Total estimated weight: " + this.estimatedWeight  +
				"Truck" +
				(this.truck!=null ? 
						"\n plate: " + this.truck.getPalte() 
						: 
							": No truck was selected for this transport");
	}

	public String makeDocForSite(Site s) 
	{	
		String ans = "Docoment Number: " + this.number + "-" + s.getCode() + "\n\n" +
					this.toString() + "\n\n" + 
					s.openingForDoc() + " " + s.toString() + "\n\n" +
					"Items: " + "\n";
		
		boolean check = false;
		for (Mission m: this.missions){
			if (m.getTo().equals(s))
			{
				ans += m.getItem().toString() + "\n    Amount: " + m.getAmount() + "\n";
				check = true;
			}
		}
		
		if (check)
			return ans;
		
		return null;
	}

	public String makeDoc() 
	{
		String ans = this.toString();
		
		ans += "The truck that siged to this trunsport are: \n";
		for (Truck t: this.signedTrucks)
			ans += "	" + t.getPalte() + "\n";
		
		ans += "The sites codes that are incloded in this trunsport are: \n";
		for (Mission m: this.missions)
			if(m.getAmountActuallySent() > 0)
				ans += "	" + m.getTo() + "\n";
		
		ans += "Changes: \n";
		for (Mission m: this.missions)
		{
			if (m.getAmountActuallySent() < m.getAmount())
				ans += m.change() + "\n";
		}
		
		return ans;
	}
}
