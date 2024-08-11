package com.SpringBoot.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "lop")
public class Class {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	 String lopID;
	
	@Column
	 String tenLop;
    
    @OneToMany(mappedBy="lop", cascade = CascadeType.REMOVE)
    @JsonBackReference
	 Set<Student> listSV = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name="khoa_id")
    @JsonManagedReference
     Khoa khoa;
}
