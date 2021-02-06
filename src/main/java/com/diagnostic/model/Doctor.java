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
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Id;

	@NotBlank
	String name;

	@NotBlank
	String degree;

	@NotBlank
	String description;

	@NotBlank
	String selectionCode;

	@ManyToOne(optional = false)
	private Provider provider;

	public String getSelectionCode() {
		return selectionCode;
	}

	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Provider getProvider() {
//		return provider;
//	}
//
//	public void setProvider(Provider provider) {
//		this.provider = provider;
//	}

}
