package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Person {
	@Id @GeneratedValue
	private Integer id;
	private String organization;

	// 	adding validation here in base class Person, works for child 
	//	classes Employer and Worker as well. 
	// login fails after adding validation. test Login after adding validation.
	/*
	 * @NotNull
	 * 
	 * @Size( message = "Name must be at least 2 characters long" , min = 2, max =
	 * 60)
	 */	
	private String firstName;
	private String lastName;
	// login fails after adding validation. test Login after adding validation.
	/*
	 * @NotNull
	 */
	private Integer age;
	private String gender;
	private String email;
	private String phoneNo;
	private String address;
	private String idProof;
	private String password;
	public Person() {
		super();
	}

	public Person(String organization, String firstName, String lastName, Integer age, String gender, String email,
			String phoneNo, String address, String idProof) {
		super();
		this.organization = organization;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.address = address;
		this.idProof = idProof;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdProof() {
		return idProof;
	}
	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
