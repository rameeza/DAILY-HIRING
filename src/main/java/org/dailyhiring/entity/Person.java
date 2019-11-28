package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Person {
	@Id
	@GeneratedValue
	private Integer id;

	private Double latitude;
	private Double longitude;

	// adding validation here in base class Person, works for child
	// classes Employer and Worker as well.
	// login fails after adding validation. test Login after adding validation.
	/*
	 * @NotNull
	 * 
	 * @Size( message = "Name must be at least 2 characters long" , min = 2, max =
	 * 60)
	 */
	// login fails after adding validation. test Login after adding validation.
	/*
	 * @NotNull
	 */
	private String name;
	private String gender;
	private String language;
	// todo - add photograph field

	private String dateOfBirth;

	private String email;
	private String faxNumber;
	private String telephoneNumber;
	private String password;

	public Person() {
		super();
	}

	public Person(Double latitude, Double longitude, String name, String gender, String language, String dateOfBirth,
			String email, String faxNumber, String telephoneNumber, String password) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.gender = gender;
		this.language = language;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.faxNumber = faxNumber;
		this.telephoneNumber = telephoneNumber;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
