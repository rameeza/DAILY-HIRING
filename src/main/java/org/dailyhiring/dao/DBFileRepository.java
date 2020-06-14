package org.dailyhiring.dao;

import java.util.List;

import org.dailyhiring.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	public List<DBFile> findByEmail(String email); 
	// study from this link
	// https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	//public DBFile findByEmail(String email);
}
