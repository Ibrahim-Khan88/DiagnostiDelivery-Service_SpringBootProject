package com.diagnostic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ParamGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	String name;

	boolean headingShow;

//	@Transient
//	public List<Parameter> selectedParameters;

	@ManyToMany(mappedBy = "paramGroup")
	@JsonIgnoreProperties("paramGroup")
	List<Investigation> investigation;

	@ManyToMany
	@JoinTable(name = "ParamgroupSubGroup")
	private List<SubGroup> subGroups;

	@ManyToMany
	@JoinTable(name = "ParamgroupDepartment")
	private List<Department> departments;

	@OneToMany(mappedBy = "paramGroup", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("paramGroup")
	private List<Parameter> parameters;

//	public List<Investigation> getInvestigation() {
//		return investigation;
//	}
//
//	public void setInvestigation(List<Investigation> investigation) {
//		this.investigation = investigation;
//	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public boolean isHeadingShow() {
		return headingShow;
	}

	public void setHeadingShow(boolean headingShow) {
		this.headingShow = headingShow;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public List<Parameter> getSelectedParameters() {
//		return selectedParameters;
//	}
//
//	public void setSelectedParameters(List<Parameter> selectedParameters) {
//		this.selectedParameters = selectedParameters;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
