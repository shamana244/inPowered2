package com.inpowered.service.api.dto.model;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Url", description = "Url of the webpage to parse")
public class UrlDto {
	@ApiModelProperty(value = "Url to parse", required = true)
	private String url;
	
	@ApiModelProperty(value = "Callback url", required = false)
	private String callbackUrl;
	
	public UrlDto()
	{
	}
	
	public UrlDto(String url, String callbackUrl)
	{
		super();
		this.url = url;
		this.callbackUrl = callbackUrl;
	}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

}
