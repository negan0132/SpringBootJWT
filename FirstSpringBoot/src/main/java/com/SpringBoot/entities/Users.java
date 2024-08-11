package com.SpringBoot.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String userId;
	String username;
	String email;
	String phone;
	String password;
	@ManyToMany
	Set<Role> roles;
	
	@OneToOne
	@JoinColumn(name="refreshID")
	RefreshToken refreshID;
}
