package jp.co.internous.team2505.model.domain;

import jp.co.internous.team2505.model.form.DestinationForm;

/**
 * mst_destinationのドメイン
 * @author インターノウス
 *
 */
public class MstDestination {
	
	private String familyName;
	private String firstName;
	private String address;
	private String telNumber;
	private int id;
	private int userId;
	
	public MstDestination(DestinationForm f) {
		this.familyName = f.getFamilyName();
		this.firstName = f.getFirstName();
		this.address = f.getAddress();
		this.telNumber = f.getTelNumber();
		this.id = f.getId();
	}
	
	public MstDestination() {
		
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
}
