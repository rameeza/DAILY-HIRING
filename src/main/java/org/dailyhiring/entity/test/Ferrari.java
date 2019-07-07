package org.dailyhiring.entity.test;

import javax.persistence.Entity;

@Entity
public class Ferrari extends FourWheeler {
	private String model;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
		

}
