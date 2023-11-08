package com.his.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligResponse {
	
	private String planName;
	
	private String planStatus;
	
	private LocalDate planStartDate;
	
	private LocalDate planEndDate;
	
	private Double benefitAmount;
	
	private String denialReason;

}
