package com.his.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.DcCitizenAppEntity;

public interface DcCitizenAppRepository extends JpaRepository<DcCitizenAppEntity, Serializable> {

}
