package com.diagnostic.model;

import java.util.ArrayList;
import java.util.List;

public class ReportResponse {

	String department;

	String type;

	List<ReportResponseEntity> requestList = new ArrayList<ReportResponseEntity>();

	public ReportResponse() {
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ReportResponseEntity> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<ReportResponseEntity> requestList) {
		this.requestList = requestList;
	}

}
