package com.inpowered.service.api.dto.builders;

import com.inpowered.service.api.dto.model.PageAnalysisDto;

public class PageAnalysisDtoBuilder {

	public PageAnalysisDto build(String pageTitle, String description, String author, String subjectivity, String text,
			Double polarityConfidence, Double subjectivityConfidence)
	{
		return new PageAnalysisDto(pageTitle, description, author, subjectivity, text,
			polarityConfidence, subjectivityConfidence);
	}
}
