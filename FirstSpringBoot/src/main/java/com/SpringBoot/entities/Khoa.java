package com.SpringBoot.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "khoa")
public class Khoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 long id;
	
	@Column
	 String name;
    
    @OneToMany(mappedBy="khoa", cascade = CascadeType.REMOVE)
    @JsonBackReference
	 Set<Student> listSV = new HashSet<>();
    
    @OneToMany(mappedBy = "khoa", cascade = CascadeType.REMOVE)
    @JsonBackReference
     Set<Class> listClass = new HashSet<Class>();
}
