package com.inpowered.model.core.buiders;

import com.inpowered.model.core.WebPageInfo;

public class WebPageInfoBuilder {

	public WebPageInfo build(String pageTitle, String description, String author, String text) {
		return new WebPageInfo(pageTitle, description, author, text);
	}
}
