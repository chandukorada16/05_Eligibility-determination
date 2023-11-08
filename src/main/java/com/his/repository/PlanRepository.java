package com.his.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable> {

}
