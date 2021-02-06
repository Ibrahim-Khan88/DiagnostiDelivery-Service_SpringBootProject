package com.diagnostic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Parameter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	String name;

	String refValue;

	String unit;

	@NotNull
	String resultType;

	@Transient
	String reportResult;

	int shortingId;

	public Parameter() {
	}

	public Parameter(int id, @NotBlank String name, @NotNull String refValue, @NotNull String resultType,
			String reportResult) {

		this.id = id;
		this.name = name;
		this.refValue = refValue;
		this.resultType = resultType;
		this.reportResult = reportResult;
	}

	@ManyToMany(mappedBy = "parameters")
	@JsonIgnoreProperties({ "parameters", "paramGroup", "investigation" })
	List<Investigation> investigation;

	@ManyToMany(mappedBy = "parameters")
	List<DropDownInput> dropDownInput;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({ "parameters", "investigation" })
	private ParamGroup paramGroup;

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

//	public List<Investigation> getInvestigation() {
//		return investigation;
//	}
//
//	public void setInvestigation(List<Investigation> investigation) {
//		this.investigation = investigation;
//	}

	public ParamGroup getParamGroup() {
		return paramGroup;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<DropDownInput> getDropDownInput() {
		return dropDownInput;
	}

	public void setDropDownInput(List<DropDownInput> dropDownInput) {
		this.dropDownInput = dropDownInput;
	}

	public void setParamGroup(ParamGroup paramGroup) {
		this.paramGroup = paramGroup;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public int getShortingId() {
		return shortingId;
	}

	public void setShortingId(int shortingId) {
		this.shortingId = shortingId;
	}

	@Override
	public String toString() {
		return "Parameter [id=" + id + ", name=" + name + ", refValue=" + refValue + ", resultType=" + resultType
				+ ", reportResult=" + reportResult + "]";
	}

}
