package com.inpowered.service;

import java.util.Arrays;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.inpowered.bll.converters.PageInfoConverter;
import com.inpowered.bll.processors.AylienAnalyzer;
import com.inpowered.bll.processors.PageAnalyzer;
import com.inpowered.dal.caches.PageInfoCacheDal;
import com.inpowered.model.core.buiders.AylienInfoBuilder;
import com.inpowered.model.core.buiders.WebPageInfoBuilder;
import com.inpowered.service.api.dto.builders.PageAnalysisDtoBuilder;
import com.inpowered.service.api.dto.model.UrlDto;
import com.inpowered.service.api.resources.InPoweredApiApplication;
import com.inpowered.service.api.resources.UrlResource;
import com.inpowered.service.api.service.UrlAnalysisService;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;

@Configuration
public class InPoweredServiceConfig
{
	private static final Logger logger = LoggerFactory.getLogger(InPoweredServiceConfig.class);

	public static final int HTTP_PORT = 8080;
	public static final String APPLICATION_NAME = "inpowered";
	public static final String SERVER_HOST = "server.host";
	//public static final String API_CONTEXT_NAME = "rest";
	public static final String SWAGGER_CONTEXT_NAME = "swagger";

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf()
	{
		return new SpringBus();
	}

	@Bean
	@DependsOn("cxf")
	public org.apache.cxf.endpoint.Server jaxRsServer()
	{
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(inPoweredApiApplication(), JAXRSServerFactoryBean.class);
		factory.setServiceBeans(Arrays.<Object> asList(urlResource(), apiListingResourceJson()));
		factory.setAddress(factory.getAddress());
		factory.setProviders(Arrays.<Object> asList(jsonProvider(), resourceListingProvider(), apiDeclarationProvider()));
		factory.getInInterceptors().add(new LoggingInInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		return factory.create();
	}
	@Bean
	public UrlResource urlResource()
	{
		return new UrlResource();
	}

	@Bean
	public UrlAnalysisService urlAnalysisService()
	{
		//Note: this would all be hooked up by Spring DI, but I ran out of time to do this. 
		//however I'm still using DI.
		
		PageAnalyzer pa = new PageAnalyzer(new WebPageInfoBuilder());
		PageInfoConverter pic = new PageInfoConverter(new PageAnalysisDtoBuilder());
		AylienAnalyzer aa = new AylienAnalyzer(new AylienInfoBuilder());
		PageInfoCacheDal pageInfoCacheDal = new PageInfoCacheDal();
		
		return new UrlAnalysisService(pa, pic, aa, pageInfoCacheDal);
	}
	
	@Bean
	public Application inPoweredApiApplication()
	{
		return new InPoweredApiApplication();
	}

	@Bean
	@Autowired
	public BeanConfig swaggerConfig(Environment environment)
	{
		final BeanConfig config = new BeanConfig();

		config.setTitle("InPowered Server api");
		config.setDescription("Restful api's for the application servers");
		config.setVersion("1.0.0");
		config.setScan(true);
		config.setResourcePackage(UrlDto.class.getPackage().getName());

		final String applicationPath = jaxRsServer().getEndpoint().getEndpointInfo().getAddress();
		final String hostname = environment.getProperty(SERVER_HOST);

		// ServiceApi = http://localhost:8080/inpowered/rest/api
		//final String serviceApi = String.format("http://%s:%s/%s/%s%s", hostname, HTTP_PORT, APPLICATION_NAME, API_CONTEXT_NAME, applicationPath);

		final String serviceApi = String.format("http://%s:%s/%s%s", hostname, HTTP_PORT, APPLICATION_NAME, applicationPath);

		logger.info(String.format("Setting swagger base path to: %s", serviceApi));

		config.setBasePath(serviceApi);

		return config;
	}

	@Bean
	public ApiDeclarationProvider apiDeclarationProvider()
	{
		return new ApiDeclarationProvider();
	}

	@Bean
	public ApiListingResourceJSON apiListingResourceJson()
	{
		return new ApiListingResourceJSON();
	}

	@Bean
	public ResourceListingProvider resourceListingProvider()
	{
		return new ResourceListingProvider();
	}

	@Bean
	public JacksonJsonProvider jsonProvider()
	{
		return new JacksonJsonProvider();
	}
}