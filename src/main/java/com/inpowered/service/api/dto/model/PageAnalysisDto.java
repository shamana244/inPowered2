package com.inpowered.service.api.dto.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Url", description = "Analysis results of parsed url")
public class PageAnalysisDto {

	@ApiModelProperty(value = "Page title", required = false)
	public String pageTitle;
	
	@ApiModelProperty(value = "Page description", required = false)
	public String description;
	
	@ApiModelProperty(value = "Author", required = false)
	public String author;
	
	@ApiModelProperty(value = "Subjectivity", required = true)
	public String subjectivity;
	
	@ApiModelProperty(value = "Page text", required = true)
	public String text;
	
	@ApiModelProperty(value = "Page polarity confidence", required = true)
	public Double polarityConfidence;
	
	@ApiModelProperty(value = "Page subjectivity confidence", required = true)
	public Double subjectivityConfidence;
	
	public PageAnalysisDto() {}

	public PageAnalysisDto(String pageTitle, String description, String author, String subjectivity, String text,
			Double polarityConfidence, Double subjectivityConfidence) {
		super();
		this.pageTitle = pageTitle;
		this.description = description;
		this.author = author;
		this.subjectivity = subjectivity;
		this.text = text;
		this.polarityConfidence = polarityConfidence;
		this.subjectivityConfidence = subjectivityConfidence;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubjectivity() {
		return subjectivity;
	}

	public void setSubjectivity(String subjectivity) {
		this.subjectivity = subjectivity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getPolarityConfidence() {
		return polarityConfidence;
	}

	public void setPolarityConfidence(Double polarityConfidence) {
		this.polarityConfidence = polarityConfidence;
	}

	public Double getSubjectivityConfidence() {
		return subjectivityConfidence;
	}

	public void setSubjectivityConfidence(Double subjectivityConfidence) {
		this.subjectivityConfidence = subjectivityConfidence;
	}
	
	
	
}
