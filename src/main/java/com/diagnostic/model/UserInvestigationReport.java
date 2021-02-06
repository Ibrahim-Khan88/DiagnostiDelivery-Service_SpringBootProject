 package com.diagnostic.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

public class UserInvestigationReport {
	
	String patientName, investigationName, reportTextValue, gender, age, reportDate, categoryName, billNumber;
	int investigationRequestID, referenceId, rate;
	byte[] reportImageValue;
	boolean reportImage = false;
	boolean reportText = false;
	boolean billStatus = false;

	SimpleDateFormat simpleDateFormat =
	        new SimpleDateFormat("E dd,MMM,yy");

	
	
	
	public UserInvestigationReport(int investigationRequestID, String billNumber, String patientName, String age, String gender, String investigationName, String categoryName,  int rate, Date reportDate,
			int referenceId, byte[] reportImage, String reportText ) {
			
		
		this.billNumber = billNumber;
		this.investigationRequestID = investigationRequestID;
		this.patientName = patientName;
		this.investigationName = investigationName;
		this.rate = rate;
		this.referenceId = referenceId;
		this.gender = gender;
		this.age = age;
		this.categoryName = categoryName;
		
		Date temp = reportDate;	
		this.reportDate = simpleDateFormat.format(temp);
		
		
//		System.out.println("created_date " + created_date.toString() + " " + investigationRequestID);
//		System.out.println("reportImage " + reportImage + " " + patientName +  "");
		
		if(reportImage != null && reportImage.length > 0) {
//			String extension = FilenameUtils.getExtension("Ai.jpg");
//			String encodeBase64 = Base64.getEncoder().encodeToString(reportImage);
//			System.out.println("reportImage " + patientName + " " + encodeBase64 +  "\n");
			this.reportImage = true;
			this.reportImageValue = reportImage;
		}
		
		if(reportText != null && !reportText.isEmpty()) {
			this.reportText = true;
			this.reportTextValue = reportText;
		}
	}


	@Override
	public String toString() {
		return "UserInvestigationReport [patientName=" + patientName + ", investigationName=" + investigationName
				+ ", reportTextValue=" + reportTextValue + ", gender=" + gender + ", age=" + age + ", reportDate="
				+ reportDate + ", investigationRequestID=" + investigationRequestID + ", referenceId=" + referenceId
				+ ", rate=" + rate + ", reportImage=" + reportImage + ", reportText=" + reportText + "]";
	}


	


	public String getBillNumber() {
		return billNumber;
	}


	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}


	public boolean isBillStatus() {
		return billStatus;
	}


	public void setBillStatus(boolean billStatus) {
		this.billStatus = billStatus;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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




	public byte[] getReportImageValue() {
		return reportImageValue;
	}




	public void setReportImageValue(byte[] reportImageValue) {
		this.reportImageValue = reportImageValue;
	}


	


	public String getReportTextValue() {
		return reportTextValue;
	}



	public void setReportTextValue(String reportTextValue) {
		this.reportTextValue = reportTextValue;
	}



	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
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

	public int getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}
	public boolean isReportImage() {
		return reportImage;
	}
	public void setReportImage(boolean reportImage) {
		this.reportImage = reportImage;
	}
	public boolean isReportText() {
		return reportText;
	}
	public void setReportText(boolean reportText) {
		this.reportText = reportText;
	}
	
	


	public int getInvestigationRequestID() {
		return investigationRequestID;
	}


	public void setInvestigationRequestID(int investigationRequestID) {
		this.investigationRequestID = investigationRequestID;
	}

	
}
