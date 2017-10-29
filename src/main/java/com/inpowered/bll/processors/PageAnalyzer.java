package com.inpowered.bll.processors;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inpowered.model.core.WebPageInfo;
import com.inpowered.model.core.buiders.WebPageInfoBuilder;

public class PageAnalyzer {

	public static final String DESCRIPTION = "description";
	public static final String AUTHOR = "author";
	public static final String TEXT = "text";
	public static final String ERROR = "processing error";
	
	private WebPageInfoBuilder builder;

	private static final Logger logger = LoggerFactory.getLogger(PageAnalyzer.class);

	
	public PageAnalyzer(WebPageInfoBuilder builder)
	{
		this.builder = builder;
	}
	
	public WebPageInfo analyzePage(String url) 
	{
		WebPageInfo wpi = null;
		
		try {
			Document document = Jsoup.connect(url).get();
			
			String title = document.title();		
			String desc = getTag(document, DESCRIPTION);
			String author = getTag(document, AUTHOR);
			String text = getTag(document, TEXT);
			
			wpi = builder.build(title, desc, author, text);
			
		} catch (IOException e) {
			logger.error("failed to process jsoup doc." + e);
			wpi = builder.build(ERROR, ERROR, ERROR, ERROR);
		}
		
		return wpi;
	}
	
	private String getTag(Document document, String tag)
	{
		String tagValue = getTagInfo(document, tag);
		
		if (tagValue == null) 
			tagValue = getTagInfo(document, "og:" + tag);
		
		return tagValue;
	}
		
	private String getTagInfo(Document document, String attr) 
	{
	    String result = processElements(document.select("meta[name=" + attr + "]"));
	    
	    if(result == null) 
	    	result = processElements(document.select("meta[property=" + attr + "]"));
	    
	    return result;
	}
	
	private String processElements(Elements elements)
	{
		String content = null;
		
		for (Element element : elements) 
		{
	        content = element.attr("content");
	        if (content != null) 
	        	break;
	    }
		
		return content;
	}
	

}
