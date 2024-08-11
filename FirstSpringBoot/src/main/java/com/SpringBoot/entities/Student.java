package com.SpringBoot.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sinhvien")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 long mssv;
	
	@Column(name = "name")
	 String name;
	
	@Column(name = "birthday")
	 String birthday;
	
	@Column(name = "gender")
	 int gender;
	
	@ManyToOne
	@JoinColumn(name = "khoa_id")
	@JsonManagedReference
	 Khoa khoa;
	
	@ManyToOne
	@JoinColumn(name="lop_id")
	@JsonManagedReference
	 Class lop;
}
