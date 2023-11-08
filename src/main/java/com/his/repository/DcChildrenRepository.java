package com.his.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.entity.DcChidrenEntity;

public interface DcChildrenRepository extends JpaRepository<DcChidrenEntity, Serializable> {

	public List<DcChidrenEntity> findByCaseNum(Long caseNum);
}
