package com.SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Class;

@Repository
public interface ClassRepo extends JpaRepository<Class, String>{

}
