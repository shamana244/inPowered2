package com.inpowered.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inpowered.service.container.IHttpServer;
import com.inpowered.service.container.HttpServerFactory;

public class ServerMain
{
	private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

	public static void main(String[] args)
	{
		logger.info("Starting up server");
		ServerMain serverMain = new ServerMain();
		serverMain.start();
	}

	private void start()
	{
		initializeWebServer(); // This is a blocking call
	}

	private void initializeWebServer()
	{
		HttpServerFactory httpServiceFactory = new HttpServerFactory();

		IHttpServer httpService = httpServiceFactory.getHttpServer();

		httpService.start(); // This is a blocking call
	}
}