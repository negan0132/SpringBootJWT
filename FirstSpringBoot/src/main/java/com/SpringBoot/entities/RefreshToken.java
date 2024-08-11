package com.SpringBoot.entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String refreshId;
	
	Date expirationDate;
	
	@OneToOne(mappedBy = "refreshID", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	Users user;
}
