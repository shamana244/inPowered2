package com.inpowered.model.core;

public class AylienInfo {

	private Double polarityConfidence;	

	private Double subjectivityConfidence;

	private String subjectivity;
	
	public AylienInfo(Double polarityConfidence, Double subjectivityConfidence, String subjectivity) {
		super();
		this.polarityConfidence = polarityConfidence;
		this.subjectivityConfidence = subjectivityConfidence;
		this.subjectivity = subjectivity;
	}
	
	public Double getPolarityConfidence() {
		return polarityConfidence;
	}

	public Double getSubjectivityConfidence() {
		return subjectivityConfidence;
	}

	public String getSubjectivity() {
		return subjectivity;
	}
}
