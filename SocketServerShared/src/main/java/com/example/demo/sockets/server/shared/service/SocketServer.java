package com.example.demo.sockets.server.shared.service;

/**
 * Socket Server interface
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public interface SocketServer {

	/**
	 * Run the server
	 */
	void run();

	/**
	 * Shutdown running server
	 */
	void shutdown();
	
}
