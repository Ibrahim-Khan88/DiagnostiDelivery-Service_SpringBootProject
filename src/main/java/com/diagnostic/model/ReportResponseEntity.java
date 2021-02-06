package com.diagnostic.model;

import java.util.ArrayList;
import java.util.List;

public class ReportResponseEntity {

	int investigationRequestId;

	String investigationName, unit, format, refValue, reportResult;

	List<ParamGroup> paramGroupList = new ArrayList<ParamGroup>();

	public ReportResponseEntity() {

	}

	@Override
	public String toString() {
		return "ReportResponseEntity [investigationRequestId=" + investigationRequestId + ", investigationName="
				+ investigationName + ", unit=" + unit + ", format=" + format + ", refValue=" + refValue
				+ ", reportResult=" + reportResult + "]";
	}

	public List<ParamGroup> getParamGroupList() {
		return paramGroupList;
	}

	public void setParamGroupList(List<ParamGroup> paramGroupList) {
		this.paramGroupList = paramGroupList;
	}

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

}
