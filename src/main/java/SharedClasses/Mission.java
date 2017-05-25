package SharedClasses;

public class Mission 
{
	private int amount;
	private int amountActuallySent;
	private Site goTo;
	private Item item;
	
	/**
	 * Constructor
	 * @param amount how much to get from/ bring to {@value Site}
	 * @param goTo where to go
	 * @param item what to get from/ bring to {@value Site}
	 */
	public Mission(int amount, Site goTo, Item item) {
		this.amount = amount;
		this.goTo = goTo;
		this.item = item;
		
		// assuming until no change was reported all the needed amount was delivered.
		this.amountActuallySent = this.amount; 
	}

	public Mission(int amount, int amountActuallySent, Item item, Site s) {
		this.amount = amount;
		this.goTo = s;
		this.item = item;
		this.amountActuallySent = amountActuallySent;
	}

	/***********************GETERS*****************************/
	public int getAmountActuallySent() {return amountActuallySent;}
	public void setAmountActuallySent(int amountActuallySent) {this.amountActuallySent = amountActuallySent;}
	public int getAmount() {return amount;}
	public Site getTo() {return goTo;}
	public Item getItem() {return item;}
	public double getWeight() {return this.amount*this.item.getWeight();}
	/**********************************************************/

	public String change() 
	{
		return this.amount + " " + this.item.getDescription() + " " + this.goTo.change() + 
				" but were taken only " + this.amountActuallySent +
				(this.amountActuallySent==0 ?
						" \n	-> the truk will not go to this site."
						: "");
	}
}
