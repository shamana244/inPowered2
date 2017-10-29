package com.inpowered.bll.processors;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aylien.textapi.TextAPIClient;
import com.aylien.textapi.TextAPIException;
import com.aylien.textapi.parameters.SentimentParams;
import com.aylien.textapi.responses.Sentiment;
import com.inpowered.model.core.AylienInfo;
import com.inpowered.model.core.buiders.AylienInfoBuilder;

public class AylienAnalyzer {

	private AylienInfoBuilder builder;
	
	// All fields below should be loaded from a config file. I ran out of time to do this.
	private static final String APPID = "49cdff63";
	private static final String KEY = "b5482ea844a930d2b666400385f151c0";
	private static final String ENDPOINT = "https://api.aylien.com/api/v1";
	
	private static final Logger logger = LoggerFactory.getLogger(AylienAnalyzer.class);

	
	
	public AylienAnalyzer(AylienInfoBuilder builder)
	{
		this.builder = builder;
	}

	public AylienInfo analyzePage(String url) 
	{
		try 
		{
			TextAPIClient client = new TextAPIClient(APPID, KEY);
			
		    SentimentParams.Builder builder = SentimentParams.newBuilder();
			
		    builder.setUrl(new URL(url));
			
			Sentiment sentiment = client.sentiment(builder.build());
			
			return this.builder.build(sentiment.getPolarityConfidence(), sentiment.getSubjectivityConfidence(), sentiment.getSubjectivity());
			
		} catch (MalformedURLException | TextAPIException e) {
			logger.error("Error occured:", e);
		}
	 
		return this.builder.build(0.0, 0.0, "ERROR");
	}
}
