package org.dailyhiring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
	@Id @GeneratedValue
	private String id;
	private String organisation;
	private String name;
	private Integer age;
	private String gender;
	private String email;
	private String phoneNo;
	private String address;
	private String idProof;
	
	public Person() {
		super();
	}
	public Person(String organisation, String name, Integer age, String gender, String email, String phoneNo,
			String address, String idProof) {
		super();
		this.organisation = organisation;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.phoneNo = phoneNo;
		this.address = address;
		this.idProof = idProof;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
