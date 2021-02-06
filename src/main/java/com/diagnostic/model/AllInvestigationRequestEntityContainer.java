package com.diagnostic.model;

import java.util.ArrayList;
import java.util.List;

public class AllInvestigationRequestEntityContainer {

	String name, billNumber, mobile;

	List<AllInvestigationRequestEntity> investigationRequestList = new ArrayList<AllInvestigationRequestEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<AllInvestigationRequestEntity> getRequestList() {
		return investigationRequestList;
	}

	public void setRequestList(List<AllInvestigationRequestEntity> investigationRequestList) {
		this.investigationRequestList = investigationRequestList;
	}

}
