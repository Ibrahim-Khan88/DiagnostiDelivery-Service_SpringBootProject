package com.diagnostic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Provider {

	@Id
	@GeneratedValue
	int id;

	String medicalName;

	String note;

	@OneToMany(mappedBy = "provider")
	private List<Investigation> investigation;

	@OneToMany(mappedBy = "provider")
	private List<LabDoctor> labDoctor;

	public Provider() {
	}

	public Provider(int id, String medicalName, String note) {
		this.id = id;
		this.medicalName = medicalName;
		this.note = note;
	}

	public String getMedicalName() {
		return medicalName;
	}

	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
