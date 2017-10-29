package com.inpowered.service.container;

import com.inpowered.service.container.jetty.JettyHttpServer;

public class HttpServerFactory
{
	public IHttpServer getHttpServer()
	{
		return new JettyHttpServer();
	}
}