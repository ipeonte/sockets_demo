package com.example.demo.sockets.client;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.sockets.client.shared.SocketClient;
import com.example.demo.sockets.client.shared.binding.ShipOrder;


/**
 * Client launcher
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
@SpringBootApplication
public class ClientApp implements CommandLineRunner {

	@Value("${socket.server.host}")
	private String host;

	@Value("${socket.server.port}")
	private int port;

	private static Logger LOG = LoggerFactory.getLogger(ClientApp.class);

	public static void main(String[] args) {
		SpringApplication.run(ClientApp.class, args);
		LOG.info("Finished application,");
	}

	public void run(String... args) {
		
		SocketClient client = null;
		
		String server =  host + ":" + port;
		
		try {
			client = new SocketClient(host, port);
			LOG.info("Connected to " + server + " OK");
		} catch (IOException | JAXBException e) {
			LOG.error("Server connection " + server + " failed - " + e.getMessage());
			System.exit(1);
		}
		

		String data = null;
		try {
			try {
				data = client.read();
				LOG.debug("Read " + data.length() + " bytes out of remote connection.");
				LOG.trace(data.replaceAll("\\n", ""));
			} catch (IOException e) {
				LOG.error("Data read from " + server + " failed - " + e.getMessage());
			}
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				// Ignore exception
			}
		}
		
		if (data == null) {
			LOG.error("Nothing to read");
			System.exit(2);
		}
		
		if (data.length() == 0) {
			LOG.error("Invalid xml file received.");
			System.exit(3);
		}
		
		// Parse xml file
		ShipOrder order = null;
		try {
			order = client.parse(data);
			LOG.debug("XML file parsing - OK");
		} catch (JAXBException e) {
			LOG.error("Error parsing xml file - " + e.getMessage());
			System.exit(4);
		}
		
		// Print some order information from xml file
		LOG.info("== Order Info Start ==");
		LOG.info("Order Id: " + order.getOrderId());
		LOG.info("Ship To: " + order.getShipTo().getName());
		LOG.info("# of items " + order.getItem().size());
		LOG.info("== Order Info End ==");
	}
}
