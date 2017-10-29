package com.inpowered.model.core;

public class WebPageInfo {

public String pageTitle;
	
	private String description;
	
	private String author;
	
	private String text;

	public WebPageInfo(String pageTitle, String description, String author, String text) {
		super();
		this.pageTitle = pageTitle;
		this.description = description;
		this.author = author;
		this.text = text;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}


	public String getText() {
		return text;
	}
	
	
	
}
