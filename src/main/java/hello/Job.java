package hello;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Job {
	@Id
	private String jobDesignation;
	
	private Double hours;
	private Date date;
	public String getJobDesignation() {
		return jobDesignation;
	}
	public void setJobDesignation(String jobDesignation) {
		this.jobDesignation = jobDesignation;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
