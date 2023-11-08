package com.his.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.EligDetailsEntity;

public interface EligDetailsRepository extends JpaRepository<EligDetailsEntity, Serializable> {

}
