package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Address {
	@Id
	@GeneratedValue
	private Integer id;

	private String buildingName;

	private String landmark;
	private String streetAddress;
	private String locality;
	// todo - add photograph field

	private String state;

	private String countryName;
	private String postalCode;

	public Address() {
		super();
	}

	public Address(String buildingName, String landmark, String streetAddress, String countryName, String postalCode) {
		super();
		this.buildingName = buildingName;
		this.landmark = landmark;
		this.streetAddress = streetAddress;
		this.countryName = countryName;
		this.postalCode = postalCode;
	}

	public Address(String buildingName, String landmark, String streetAddress, String locality, String state,
			String countryName, String postalCode) {
		super();
		this.buildingName = buildingName;
		this.landmark = landmark;
		this.streetAddress = streetAddress;
		this.locality = locality;
		this.state = state;
		this.countryName = countryName;
		this.postalCode = postalCode;
	}

	public Address(Integer id, String buildingName, String landmark, String streetAddress, String locality,
			String state, String countryName, String postalCode) {
		super();
		this.id = id;
		this.buildingName = buildingName;
		this.landmark = landmark;
		this.streetAddress = streetAddress;
		this.locality = locality;
		this.state = state;
		this.countryName = countryName;
		this.postalCode = postalCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

}
