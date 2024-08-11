package com.SpringBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.entities.Khoa;

@Repository
public interface KhoaRepo extends JpaRepository<Khoa, Long>{

}
