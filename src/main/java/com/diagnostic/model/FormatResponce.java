package com.diagnostic.model;

public class FormatResponce {
	
	String format,sample,referenceValue;

	public FormatResponce(String format, String referenceValue, String sample) {
		this.format = format;
		this.sample = sample;
		this.referenceValue = referenceValue;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
	
	
	
	

}
