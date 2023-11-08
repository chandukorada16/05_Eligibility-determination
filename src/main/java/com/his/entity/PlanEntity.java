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
@Table(name="USER_MASTER")
public class PlanEntity {
	
	@Id
	@GeneratedValue
	private Integer planId;
	
	private String planName;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private String createdBy;
	
	private String updatedBy;
	
	@CreationTimestamp
	@Column(name="CREAT_DATE",updatable = false)
	private LocalDate createdDate;
	
	@UpdateTimestamp
	@Column(name="UPDA_DATE",insertable = false)
	private LocalDate updatedDate;

}
