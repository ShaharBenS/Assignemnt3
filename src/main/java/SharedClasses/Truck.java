package SharedClasses;

public class Truck 
{
	private String palte;
	private String model;
	private double weight;
	private double maxWeight;
	private int licenseType;
	
	public Truck(String palte, String model, double weight, double maxWeight, int licenseType) {
		this.palte = palte;
		this.model = model;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.licenseType = licenseType;
	}

	public String getPalte() {
		return palte;
	}

	public String getModel() {
		return model;
	}

	public double getWeight() {
		return weight;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public int getLicenseType() {
		return licenseType;
	}
}
