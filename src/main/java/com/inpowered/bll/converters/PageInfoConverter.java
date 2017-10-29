package com.inpowered.bll.converters;

import com.inpowered.model.core.AylienInfo;
import com.inpowered.model.core.WebPageInfo;
import com.inpowered.service.api.dto.builders.PageAnalysisDtoBuilder;
import com.inpowered.service.api.dto.model.PageAnalysisDto;

// Given a Webpage info and Aylien info model object, converts them to DTOs
public class PageInfoConverter {

	private PageAnalysisDtoBuilder builder;
	
	public PageInfoConverter(PageAnalysisDtoBuilder builder)
	{
		this.builder = builder;
	}
	
	public PageAnalysisDto convert(WebPageInfo pageInfo, AylienInfo aylienInfo)
	{
		return this.builder.build(pageInfo.getPageTitle(), pageInfo.getDescription(),
				pageInfo.getAuthor(), pageInfo.getText(), aylienInfo.getSubjectivity(), 
				aylienInfo.getPolarityConfidence(), aylienInfo.getSubjectivityConfidence());
	}
}
