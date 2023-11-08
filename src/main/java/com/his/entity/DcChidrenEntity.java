package com.his.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="DC_CHILD_ENTITY")
public class DcChidrenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer childrenId;
	
	private String childernName;
	
	private Integer childernAge;
	
	private Long childrenSsn;
	
	private Long caseNum;

}
