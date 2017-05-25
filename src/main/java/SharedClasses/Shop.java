package SharedClasses;

public class Shop extends Site 
{
	private String region;

	public Shop(int code, String address, String phone, String contact, String region) {
		super(code, address, phone, contact);
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public String openingForDoc() {
		return "Deliver to:";
	}

	@Override
	public String change() {
		return " were to be sent to the shop " + this.getCode();
	}
}
