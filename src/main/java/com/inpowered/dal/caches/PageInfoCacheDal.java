package com.inpowered.dal.caches;

import java.util.HashMap;

import com.inpowered.model.core.AylienInfo;
import com.inpowered.model.core.WebPageInfo;

//this class would check internal cache and database. However I ran out of 
//time to implement the database, so for now, it just uses an internal map as for caching purposes
//the maps are different because I would have stored the data in separate tables in the database.
//note: in database, url would not be the key, it would be denormalized with a primary key for each url
//and the associated aylieninfo and webpageinfo tables would refer to that key.


public class PageInfoCacheDal {
	
	private HashMap<String, AylienInfo> aylientInfoMap;
	private HashMap<String, WebPageInfo> webpageInfoMap;

	public PageInfoCacheDal()
	{
		this.aylientInfoMap = new HashMap<String, AylienInfo>();
		this.webpageInfoMap = new HashMap<String, WebPageInfo>();	
	}
	
	public AylienInfo getAylienInfo(String url)
	{
		return this.aylientInfoMap.get(url);
	}
	
	public WebPageInfo getWebPageInfo(String url)
	{
		return this.webpageInfoMap.get(url);
	}
	
	public void addAylienInfo(String url, AylienInfo ai)
	{
		if(url != null && ai != null && !this.aylientInfoMap.containsKey(url))
		{
			this.aylientInfoMap.put(url, ai);
		}
	}
	
	public void addWebPageInfo(String url, WebPageInfo wpi)
	{
		if(url != null && wpi != null && !this.webpageInfoMap.containsKey(url))
		{
			this.webpageInfoMap.put(url, wpi);
		}
	}

}
