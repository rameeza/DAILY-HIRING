package org.dailyhiring.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Worker extends Person {
	@OneToOne(cascade = {CascadeType.ALL})
	private Address address;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private DBFile profilepic;
	
	
	public DBFile getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(DBFile profilepic) {
		this.profilepic = profilepic;
	}

	
	private Integer defaultPayVisit; // Default per day pay for the worker according to his skill
	private String skillType; 
	private Double experience; // Experience-In-Years
	private Integer payAmount;
	private String typeOfPayAmount; // hourly, daily, monthly...
	private String certificate; //
	private String placePreference; // Localities where he likes working

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Worker() {
		super();
	}

	
	public Worker(Double latitude, Double longitude, String name, String gender, String language,
			String dateOfBirth, String email, String faxNumber, String telephoneNumber, String password,
			String buildingName, String landmark, String streetAddress, String countryName, String postalCode) {
		super(latitude, longitude, name, gender, language, dateOfBirth, email, faxNumber, telephoneNumber, password);
		Address address = new Address(buildingName, landmark, streetAddress, countryName, postalCode);
		this.setAddress(address);
	}

	public Worker(Integer defaultPayVisit, String skillType, Double experience, Integer payAmount,
			String typeOfPayAmount, String certificate, String placePreference, Double latitude, 
			Double longitude, String name, String gender, String language, String dateOfBirth, 
			String email, String faxNumber, String telephoneNumber, String password,
			String buildingName, String landmark, String streetAddress, 
			String countryName, String postalCode) {
		super(latitude, longitude, name, gender, language, dateOfBirth, email, faxNumber, telephoneNumber, password);
		Address address = new Address(buildingName, landmark, streetAddress, countryName, postalCode);
		this.setAddress(address);
		this.defaultPayVisit = defaultPayVisit;
		this.skillType = skillType;
		this.experience = experience;
		this.payAmount = payAmount;
		this.typeOfPayAmount = typeOfPayAmount;
		this.certificate = certificate;
		this.placePreference = placePreference;
		
	}

	
	public Worker(Integer defaultPayVisit, String skillType, Double experience, 
			Integer payAmount, String typeOfPayAmount, String certificate, 
			String placePreference, Double latitude, Double longitude, String name, 
			String gender, String language, String dateOfBirth, String email, 
			String faxNumber, String telephoneNumber, String password, 
			String buildingName, String landmark, String streetAddress,
			String locality, String state, String countryName, String postalCode) {
		super(latitude, longitude, name, gender, language, dateOfBirth, email, faxNumber, telephoneNumber, password);
		//Address address = new Address(buildingName, landmark, streetAddress, countryName, postalCode);
		Address address = new Address(buildingName, landmark, streetAddress, locality, 
				state, countryName, postalCode);
		this.setAddress(address);
		this.defaultPayVisit = defaultPayVisit;
		this.skillType = skillType;
		this.experience = experience;
		this.payAmount = payAmount;
		this.typeOfPayAmount = typeOfPayAmount;
		this.certificate = certificate;
		this.placePreference = placePreference;
	}

	public Integer getDefaultPayVisit() {
		return defaultPayVisit;
	}

	public void setDefaultPayVisit(Integer defaultPayVisit) {
		this.defaultPayVisit = defaultPayVisit;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public Double getExperience() {
		return experience;
	}

	public void setExperience(Double experience) {
		this.experience = experience;
	}

	public Integer getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public String getTypeOfPayAmount() {
		return typeOfPayAmount;
	}

	public void setTypeOfPayAmount(String typeOfPayAmount) {
		this.typeOfPayAmount = typeOfPayAmount;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getPlacePreference() {
		return placePreference;
	}

	public void setPlacePreference(String placePreference) {
		this.placePreference = placePreference;
	}

}
