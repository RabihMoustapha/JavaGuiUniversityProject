package Models;

public class PhoneNumber {
	private int regionCode, number;
	
	public PhoneNumber(int regionCode, int number) {
		this.number = number < 0 ? number : 00-000000;
		this.regionCode = regionCode < 0 ? regionCode : 961;
	}
	
	// Getters
	public int getRegionCode() {
		return this.regionCode;
	}

	public int getNumber() {
		return this.number;
	}
	
	// Setters
	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return this.regionCode + " " + this.number;
	}
}
