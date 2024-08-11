package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;

import com.SpringBoot.entities.Student;

public interface StudentServiceIF {
	
	Optional<List<Student>> getStudentByKhoa(long khoa_id);
	
	Optional<Student> getOneStudentById(long student_id);
	
	Optional<List<Student>> getAllStudent();
	
	Optional<Student> saveOneStudent(Student student);
	
	void deleteOne(Student student);
}
