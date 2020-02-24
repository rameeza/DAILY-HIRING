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

	
}
