package SharedClasses;

import java.util.LinkedList;

public class Transport 
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
	public Transport(String date, String leavTime, int number) {
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
		for (Truck tr: this.signedTrucks)
			if (tr.getPalte().equals(t.getPalte()))
				throw new NituzException(9,"truck allredy sign to this trunsport");
		
		if (this.estimatedWeight > t.getMaxWeight())
			throw new NituzException (10, "this truck is unable to carry the lead");
		this.signedTrucks.add(t);
	}
	
	public void addMission(Mission m) throws NituzException
	{
		if (this.missions.contains(m))
			throw new NituzException(16,"mission allredy sign to this trunsport");
		
		double temp = this.estimatedWeight + m.getWeight();
		if (this.truck != null && this.truck.getMaxWeight()<temp)
			throw new NituzException(17,"truck " +  this.truck.getPalte() + " is unable to carry the lead: " + this.estimatedWeight);

		this.estimatedWeight = temp;
		this.missions.add(m);
	}

	public String makeDocForShopAndSupplier(Site shop, Site supplier) throws NituzException {
		String ans = this.details() + "\n\n";
		ans += supplier.details() + "\n\n";
		ans += shop.details() + "\n\n";
		ans += "Order Contents: \n";
		ans += "Item Name:" + "            " + "Amount:\n";
		String h = this.findItems(shop, supplier);
		if (h==null)
			throw new NituzException(14, "transport " + this.number +" not going to this site/s (" + shop.getCode() + ", " + supplier.getCode() + ")");
		return ans;
	}

	private String findItems(Site shop, Site supplier) {
		String res = "";

		LinkedList<Mission> shm = this.allMissionToSite(shop.getCode());
		if (shm.isEmpty())
			return null;

		LinkedList<Mission> spm = this.allMissionToSite(supplier.getCode());
		if (spm.isEmpty())
			return null;

		for (int h=0; h<shm.size(); h++)
		{
			for (int p=0; p<spm.size(); p++)
			{
				if (shm.get(h).getItem().getCode() == spm.get(p).getItem().getCode())
				{
					res += shm.get(h).getItem().getDescription() + "                   " +
							shm.get(h).getAmountActuallySent() + "\n";
					break;
				}
			}
		}

		return res;
	}

	private String details() {
		return "Trunsport Number " + this.number + "\n" +
				"Date: " + this.date + "          " +
				"Truck" +
				(this.truck!=null ?
						" plate: " + this.truck.getPalte()
						:
						": No truck was selected for this transport") + "\n" +
				"Leaving Time: " + this.leavTime  + "          " +
				"Driver: " +
				(this.driver!=null ?
						this.getDriver().getName() + " " +this.getDriver().getId()
						:
						"No driver was selected for this transport");
	}

	private LinkedList<Mission> allMissionToSite(int code) {
		LinkedList<Mission> res = new LinkedList<Mission>();
		for (Mission m: this.missions)
			if (m.getTo().getCode() == code)
				res.add(m);
		return res;
	}

	public void selectTruck(Truck t) throws NituzException{
		boolean b = false; 
		for(Truck tr: this.signedTrucks)
			if (tr.getPalte().equals(t.getPalte()))
				b = true;
		if (!b)
			throw new NituzException(14,"truck " +  t.getPalte() + " was not registered for this transport");
		
		if(t.getMaxWeight() < this.estimatedWeight)
			throw new NituzException(17,"he truck that selected for this transport is unable to carry the lead if you add this misson");
		
		if (this.driver != null && t.getLicenseType()>driver.getLicense())
			throw new NituzException(18,"driver license and truck license are unfit");
		
		this.truck = t;
	}
	
	
	public void setDriver(Driver driver) throws NituzException {
		if (this.truck == null)
			throw new NituzException (19,"no truck for this transport. select truck first.");
			
		if ( driver.getLicense()<this.truck.getLicenseType())
			throw new NituzException(18,"driver license and truck license not unfit");
		
		this.driver = driver;
	}

	public String toString(){
		return "Trunsport Number " + this.number + "\n" +
				"Date: " + this.date + " " + this.leavTime + "\n" +
				"Total estimated weight: " + this.estimatedWeight  + "\n" +
				"Truck" +
				(this.truck!=null ? 
						" plate: " + this.truck.getPalte() 
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
		
		ans += "\nThe truck that siged to this trunsport are: \n";
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
