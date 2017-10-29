package com.inpowered.service.api.service;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inpowered.bll.converters.PageInfoConverter;
import com.inpowered.bll.processors.AylienAnalyzer;
import com.inpowered.bll.processors.PageAnalyzer;
import com.inpowered.dal.caches.PageInfoCacheDal;
import com.inpowered.model.core.AylienInfo;
import com.inpowered.model.core.WebPageInfo;
import com.inpowered.service.api.dto.model.PageAnalysisDto;
import com.inpowered.service.api.dto.model.UrlDto;

public class UrlAnalysisService {

	private PageAnalyzer pageAnalyzer;
	private PageInfoConverter pageInfoConverter;
	private AylienAnalyzer aylienAnalyzer;
	private PageInfoCacheDal pageInfoCacheDal;
	
	private static final Logger logger = LoggerFactory.getLogger(UrlAnalysisService.class);

	
	public UrlAnalysisService(PageAnalyzer pageAnalyzer, PageInfoConverter pageInfoConverter,
			AylienAnalyzer aylienAnalyzer, PageInfoCacheDal pageInfoCacheDal)
	{
		this.pageAnalyzer = pageAnalyzer;
		this.pageInfoConverter = pageInfoConverter;
		this.aylienAnalyzer = aylienAnalyzer;
		this.pageInfoCacheDal = pageInfoCacheDal;
	}
	
	public PageAnalysisDto analyseUrl(UrlDto urlDto) 
	{
		String url = urlDto.getUrl();
		final CountDownLatch latch = new CountDownLatch(2);
		
		//quick and dirty way to run 2 threads.
		Runnable webpageTask = () -> {
			WebPageInfo wpi = this.pageAnalyzer.analyzePage(url);
			this.pageInfoCacheDal.addWebPageInfo(url, wpi);		
			latch.countDown();
			logger.info("webpageTask finished");
		};
		
		
		Runnable aiTask = () -> {
			AylienInfo ai = this.aylienAnalyzer.analyzePage(url);
			this.pageInfoCacheDal.addAylienInfo(url, ai);
			latch.countDown();
			logger.info("aiTask finished");
		};
		
		webpageTask.run();
		aiTask.run();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("Error: ", e);
		}
		
		return findAnalysisByUrl(url);
	}

	public PageAnalysisDto findAnalysisByUrl(String url) 
	{
		if(url != null)
		{
			WebPageInfo wpi = this.pageInfoCacheDal.getWebPageInfo(url);
			AylienInfo ai = this.pageInfoCacheDal.getAylienInfo(url);
			
			return this.pageInfoConverter.convert(wpi, ai);
		}

		return null;
	}

}
