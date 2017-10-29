package com.inpowered.service.container.jetty;

import java.io.IOException;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.inpowered.service.InPoweredServiceConfig;
import com.inpowered.service.container.IHttpServer;

public class JettyHttpServer implements IHttpServer
{
	private static final String WEB_APP_DIRECTORY = "/webapp";

	private static final Logger logger = LoggerFactory.getLogger(JettyHttpServer.class);

	private final Server server;

	public JettyHttpServer()
	{
		this.server = new Server(InPoweredServiceConfig.HTTP_PORT);
		configureJetty(this.server);
	}

	private void configureJetty(Server server)
	{
		Resource.setDefaultUseCaches(false);

		System.setProperty(InPoweredServiceConfig.SERVER_HOST, "localhost");

		final ServletContextHandler context = createContextHandler();

		final ServletContextHandler swagger = createSwaggerContextHandler();

		final HandlerList handlers = new HandlerList();
		handlers.addHandler(context);
		handlers.addHandler(swagger);

		server.setHandler(handlers);
	}

	private ServletContextHandler createContextHandler()
	{
		// Configuring Apache CXF servlet and Spring listener
		final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath(String.format("/%s", InPoweredServiceConfig.APPLICATION_NAME));
		//context.addServlet(servletHolder, String.format("/%s/*", InPoweredServiceConfig.API_CONTEXT_NAME));

		context.addServlet(servletHolder, String.format("/*"));

		configureSpringContext(context);

		return context;
	}

	private void configureSpringContext(final ServletContextHandler context)
	{
		context.addEventListener(new ContextLoaderListener());
		context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation", InPoweredServiceConfig.class.getName());
	}

	private ServletContextHandler createSwaggerContextHandler()
	{
		// Configuring Swagger as static web resource
		final ServletContextHandler swagger = new ServletContextHandler();
		swagger.setContextPath(String.format("/%s", InPoweredServiceConfig.SWAGGER_CONTEXT_NAME));
		try
		{
			final String resourceBase = new ClassPathResource(WEB_APP_DIRECTORY).getURI().toString();

			ResourceHandler resource_handler = new ResourceHandler();
			resource_handler.setDirectoriesListed(true);
			resource_handler.setWelcomeFiles(new String[] { "index.html" });
			resource_handler.setResourceBase(resourceBase);
			swagger.setHandler(resource_handler);
		}
		catch (IOException e)
		{
			logger.error("error setting swagger resource.", e);
			throw new RuntimeException(e);
		}
		return swagger;
	}

	@Override
	public void start()
	{
		try
		{
			server.start();
			logger.info("Server is up and running");
			server.join();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop()
	{
		try
		{
			server.stop();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}