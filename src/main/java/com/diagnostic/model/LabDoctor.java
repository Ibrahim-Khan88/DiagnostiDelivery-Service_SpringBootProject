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
public class LabDoctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	String title;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

//	public Provider getProvider() {
//		return provider;
//	}
//
//	public void setProvider(Provider provider) {
//		this.provider = provider;
//	}

	@Override
	public String toString() {
		return "LabDoctor [id=" + id + ", title=" + title + ", degree=" + degree + ", provider=" + provider + "]";
	}

}
