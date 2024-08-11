package com.SpringBoot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
	
	@Query(value="SELECT * FROM sinhvien s WHERE s.khoa_id = ?1", nativeQuery = true)
	List<Student> getStudentByKhoa(long khoa_id);

}
