package com.diagnostic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class DropDownInput {

	@Id
	@GeneratedValue
	int id;

	String value;

	@ManyToMany
	@JoinTable(name = "DropDownInputParameters")
	private List<Parameter> parameters;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
