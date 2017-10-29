package com.inpowered.service.container;

public interface IHttpServer
{
	/**
	 * Blocking method
	 */
	void start();

	/**
	 * Non-blocking method
	 */
	void stop();
}
