package com.diagnostic.model;

public class AllInvestigationRequestEntity {

	int investigationRequestId, rate;

	String investigationName, departmentName;

	public int getInvestigationRequestId() {
		return investigationRequestId;
	}

	public void setInvestigationRequestId(int investigationRequestId) {
		this.investigationRequestId = investigationRequestId;
	}

	public String getInvestigationName() {
		return investigationName;
	}

	public void setInvestigationName(String investigationName) {
		this.investigationName = investigationName;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
