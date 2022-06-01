package co.greendeck.entity;

/*
 * used to create object that can be sent as JSON
 * Used as entity or model
 */

public class CSVData {

	private String time;
	private String family;
	private String product;
	private String country;
	private String deviceType;
	private String os;
	private double checkOutFailure;
	private double paymentAPIFailure;
	private double purchaseCount;
	private double revenue;

	public CSVData() {}
	public CSVData(String time, String family, String product, String country, String deviceType, String os,
			double checkOutFailure, double paymentAPIFailure, double purchaseCount, double revenue) {

		this.time = time;
		this.family = family;
		this.product = product;
		this.country = country;
		this.deviceType = deviceType;
		this.os = os;
		this.checkOutFailure = checkOutFailure;
		this.paymentAPIFailure = paymentAPIFailure;
		this.purchaseCount = purchaseCount;
		this.revenue = revenue;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = "ECOM_200";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = "Mobile";
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = "iOS";
	}

	public double getCheckOutFailure() {
		return checkOutFailure;
	}

	public void setCheckOutFailure(double checkOutFailure) {
		this.checkOutFailure = checkOutFailure;
	}

	public double getPaymentAPIFailure() {
		return paymentAPIFailure;
	}

	public void setPaymentAPIFailure(double paymentAPIFailure) {
		this.paymentAPIFailure = paymentAPIFailure;
	}

	public double getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(double purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
}
