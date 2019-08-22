package com.example.demo.sockets.server;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.sockets.server.shared.service.SocketServer;

/**
 * Server launcher
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */

@SpringBootApplication
public class ServerApp implements CommandLineRunner {

	@Autowired
	private SocketServer _server;
	
	private static Logger LOG = LoggerFactory.getLogger(ServerApp.class);

	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
		LOG.info("Application stopped.");
	}

	public void run(String... args) {
		_server.run();
	}
	
	@PreDestroy
    public void onDestroy() throws Exception {
		_server.shutdown();
		LOG.info("Socket Server closed.");
    }
}
