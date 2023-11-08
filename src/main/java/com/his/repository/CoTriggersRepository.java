package com.his.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.CoTriggersEntity;

public interface CoTriggersRepository extends JpaRepository<CoTriggersEntity, Serializable> {

}
