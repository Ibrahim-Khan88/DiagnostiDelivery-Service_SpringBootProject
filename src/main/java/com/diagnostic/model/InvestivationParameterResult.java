package com.diagnostic.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class InvestivationParameterResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	String result;

	@ManyToOne(optional = false)
	private InvestigationRequest investigationRequest;

	@ManyToOne(optional = false)
	private Parameter parameter;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public InvestigationRequest getInvestigationRequest() {
		return investigationRequest;
	}

	public void setInvestigationRequest(InvestigationRequest investigationRequest) {
		this.investigationRequest = investigationRequest;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

}
