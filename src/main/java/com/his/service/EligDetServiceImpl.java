package com.his.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.binding.EligResponse;
import com.his.entity.CoTriggersEntity;
import com.his.entity.DcCasesEntity;
import com.his.entity.DcChidrenEntity;
import com.his.entity.DcCitizenAppEntity;
import com.his.entity.DcEducationEntity;
import com.his.entity.DcIncomeEntity;
import com.his.entity.EligDetailsEntity;
import com.his.entity.PlanEntity;
import com.his.repository.CoTriggersRepository;
import com.his.repository.DcCasesRepository;
import com.his.repository.DcChildrenRepository;
import com.his.repository.DcCitizenAppRepository;
import com.his.repository.DcEducationRepository;
import com.his.repository.DcIncomeRepository;
import com.his.repository.EligDetailsRepository;
import com.his.repository.PlanRepository;

@Service
public class EligDetServiceImpl implements EligDetService {
	
	@Autowired
	private DcCasesRepository casesRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private DcIncomeRepository incomeRepository;
	
	@Autowired
	private DcChildrenRepository childeRepository;
	
	@Autowired
	private DcCitizenAppRepository appRepository;
	
	@Autowired
	private DcEducationRepository educationRepository;
	
	@Autowired
	private EligDetailsRepository eligiRepository;
	
	@Autowired
	private CoTriggersRepository triggersRepository;

	@Override
	public EligResponse eligibiltyDetermine(Long caseNum) {
		
		Integer planId=null;
		String planName=null;
		Integer appId=null;
			
	Optional<DcCasesEntity> caseEntity = casesRepository.findById(caseNum);
	if(caseEntity.isPresent()) {
		 planId = caseEntity.get().getPlanId();
	}
	
	Optional<PlanEntity> planEntity = planRepository.findById(planId);
	if(planEntity.isPresent()) {
		 planName = planEntity.get().getPlanName();
	}
	
	Optional<DcCitizenAppEntity> appEntity = appRepository.findById(appId);
	Integer age=0;
	
	if(appEntity.isPresent()) {
		DcCitizenAppEntity appE=appEntity.get();
		LocalDate dob=appE.getDob();
		LocalDate now=LocalDate.now();
		age=Period.between(dob, now).getYears();
	}
			
		
		EligResponse executeDetermine = executeDetermine(planName,caseNum,age);
		EligDetailsEntity entity=new EligDetailsEntity();
		
		entity.setCaseNum(caseNum);
		entity.setHolderName(appEntity.get().getFullName());
		entity.setHolderSsn(appEntity.get().getSsn());
		
		BeanUtils.copyProperties(executeDetermine, entity);
		eligiRepository.save(entity);
		
		CoTriggersEntity triggersEntity=new CoTriggersEntity();
		triggersEntity.setCaseNum(caseNum);
		triggersEntity.setTrgStatus("Pending");
		
		triggersRepository.save(triggersEntity);
		
		return executeDetermine;
	}
	
	private EligResponse executeDetermine(String planName,Long caseNum,Integer appId) {
		
		EligResponse response=new EligResponse();
		DcIncomeEntity income = incomeRepository.findByCaseNum(caseNum);
		
		if("SNAP".equals(planName)) {
			
			Double empIncome = income.getEmpIncome();
			if(empIncome<=300) {
				response.setPlanStatus("Approved");
			}
			else {
				response.setPlanStatus("Denied");
				response.setDenialReason("High Income");
			}
			
		}else if("CCAP".equals(planName)) {
			
			List<DcChidrenEntity> childs = childeRepository.findByCaseNum(caseNum);
			
			boolean ageCondition=true;
			boolean childCount=false;
			
			if(!childs.isEmpty()) {
				for(DcChidrenEntity entity:childs) {
					childCount=true;
					Integer childernAge = entity.getChildernAge();
					if(childernAge>16) {
						ageCondition=false;
						break;
					}
				}
				if(income.getEmpIncome()<=300 && ageCondition && childCount) {
					response.setPlanStatus("Approved");
				}else {
					response.setPlanStatus("Denied");
					response.setDenialReason("Rules are not Satisfied");
				}
			}
			
			
		}else if("Medicaid".equals(planName)) {
			if(income.getEmpIncome()<=300&& income.getPropertyIncome()==0) {
				response.setPlanStatus("Approved");
			}else {
				response.setPlanStatus("Denied");
				response .setDenialReason("High Property income");
			}
		}else if("Medicare".equals(planName)) {
			
			LocalDate dob=null;
			Optional<DcCitizenAppEntity> appEntity = appRepository.findById(appId);
			
			if(appEntity.isPresent()) {
				dob=appEntity.get().getDob();
			}
			LocalDate now=LocalDate.now();
					int age = Period.between(dob, now).getYears();
					
					if(age>=65) {
						response.setPlanStatus("Approved");
					}else {
						response.setPlanStatus("Denied");
						response.setDenialReason("Age is Not Satisfied");
					}
			
		}else if("NJW".equals(planName)) {
			
			DcEducationEntity educationEntity = educationRepository.findByCaseNum(caseNum);
			Integer gradYear = educationEntity.getGradYear();
			
			int currentYear = LocalDate.now().getYear();
			if(income.getEmpIncome()==0 && gradYear<currentYear) {
				response.setPlanStatus("Approved");
			}else {
				response.setPlanStatus("Denied");
				response.setDenialReason("Rules are Not Satisfied");
			}
			if(response.getPlanStatus().equals("Approved"));
			response.setPlanStartDate(LocalDate.now());
			response.setPlanEndDate(LocalDate.now().plusMonths(6));
			response.setBenefitAmount(350.00);
		}
		return response;
		
	}

}
