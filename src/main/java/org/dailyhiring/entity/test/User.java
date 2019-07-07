package org.dailyhiring.entity.test;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

@Entity
public class User {

	/* @NotNull(message = "Name can not be null") */
	@Size( message = "Name must be at least 2 characters long" , min = 2, max = 60)
	private String name;

	/* @NotNull */
	@Id
	@Email(message = "Email should be valid!")
	@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	private String email;

	/* @NotNull */
	@Size(min=5, message="Password must be at least 5 characters long")
	private String password;
	
	@Pattern(regexp="^[0-9]{10}$", message="Enter a valid 10 Digit phone no")
	private String phoneNumber;

	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String toString() {
		return "Person(Name: " + this.name + ", Email: " + this.email + " , Password: " + this.password 
				+ " , PhoneNumber: " + this.phoneNumber + ")";
	}
}
