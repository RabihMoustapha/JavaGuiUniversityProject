package Models;

public class PhoneNumber {
	private String regionCode, number;
	
	public PhoneNumber(String regionCode, String number) {
		this.number = number;
		this.regionCode = regionCode;
	}
	
	//Getters
	public String getRegionCode() {
		return this.regionCode;
	}

	public String getNumber() {
		return this.number;
	}
	
	//Setters
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String toString() {
		return this.regionCode + " " + this.number;
	}
}
