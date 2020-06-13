package org.dailyhiring.entity;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "profilepics")
public class DBFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String email; // email id of user

    private String path;
    
    @Transient
    private MultipartFile  data;

    public DBFile() {

    }


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public DBFile(String email, MultipartFile data) {
    	this.email = email;
        this.data = data;
    }

    
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public MultipartFile getData() {
		return data;
	}

	public void setData(MultipartFile data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DBFile [id=" + id + ", email=" + email + ", data=" + data + "]";
	}
    
}