package com.diagnostic.model;

import java.util.Arrays;

public class AdminInvestigationPost {
	
	String patientName, reportText, mobile, investigationName, imageFileName, gender, age, gmail;
	int referenceId;
	byte[] reportImage;
	boolean billStatus;
	
//	{"patientName":"name","mobile":"mobile","reportText":"reportText","investigationName":"Blood glucose test","referenceId":1}
	
	
	
	

	public String getpatientName() {
		return patientName;
	}


	public String getGmail() {
		return gmail;
	}


	public void setGmail(String gmail) {
		this.gmail = gmail;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public boolean isBillStatus() {
		return billStatus;
	}

	public void setBillStatus(boolean billStatus) {
		this.billStatus = billStatus;
	}

	public void setpatientName(String patientName) {
		this.patientName = patientName;
	}
	
	

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getReportText() {
		return reportText;
	}

	public void setReportText(String reportText) {
		this.reportText = reportText;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getInvestigationName() {
		return investigationName;
	}

	public void setInvestigationName(String investigationName) {
		this.investigationName = investigationName;
	}


	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	public byte[] getReportImage() {
		return reportImage;
	}

	public void setReportImage(byte[] reportImage) {
		this.reportImage = reportImage;
	}

	@Override
	public String toString() {
		return "AdminInvestigationPost [name=" + patientName + ", reportText=" + reportText + ", mobile=" + mobile
				+ ", investigationName=" + investigationName + ", imageFileName=" + imageFileName + ", referenceId="
				+ referenceId + ", reportImage=" + Arrays.toString(reportImage) + "]";
	}
	
	
	
	

}
