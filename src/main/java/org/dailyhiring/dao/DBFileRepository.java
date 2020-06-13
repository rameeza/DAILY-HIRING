package org.dailyhiring.dao;

import org.dailyhiring.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	public DBFile findByEmail(String email);

}
