package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.ParamGroup;

public interface ParamGroupRepository extends JpaRepository<ParamGroup, Integer> {

	List<ParamGroup> findByDepartmentsIdAndSubGroupsId(int departmentId, int subGroupId);

}
