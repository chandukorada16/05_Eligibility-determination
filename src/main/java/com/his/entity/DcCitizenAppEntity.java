package com.his.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;


@Data
@Entity
@Table(name="CITIZEN_APPS")
public class DcCitizenAppEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appId;
	
	private String fullName;
	
	private String email;
	
	private Long phNum;
	
	private Long ssn;
	
	private Character gender;
	
	private String stateName;
	
	private LocalDate dob;
	
	private String createdBy;
	
	private String updatedBy;
	
	@CreationTimestamp
	@Column(name="CREATE_DT",updatable = false)
	private LocalDate createDate;
	
	@UpdateTimestamp
	@Column(name="UPDATE_DT",insertable = false)
	private LocalDate updatedDate;

}
