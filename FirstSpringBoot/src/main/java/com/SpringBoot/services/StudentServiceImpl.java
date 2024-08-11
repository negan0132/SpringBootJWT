package com.SpringBoot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.entities.Student;
import com.SpringBoot.repositories.StudentRepo;

@Service
public class StudentServiceImpl implements StudentServiceIF{

	@Autowired
	StudentRepo repo;
	
	@Override
	public Optional<List<Student>> getStudentByKhoa(long khoa_id) {
		return Optional.ofNullable(repo.getStudentByKhoa(khoa_id));
	}

	@Override
	public Optional<Student> getOneStudentById(long student_id) {
		return repo.findById(student_id);
	}

	@Override
	public Optional<List<Student>> getAllStudent() {
		return Optional.ofNullable(repo.findAll());
	}

	@Override
	public Optional<Student> saveOneStudent(Student student) {
		return Optional.ofNullable(repo.save(student));
	}

	@Override
	public void deleteOne(Student student) {
		repo.delete(student);
	}
	
	

}
