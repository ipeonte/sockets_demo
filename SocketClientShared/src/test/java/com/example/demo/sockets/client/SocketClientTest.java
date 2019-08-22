package com.example.demo.sockets.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.sockets.client.shared.SocketClient;
import com.example.demo.sockets.client.shared.binding.ShipOrder;
import com.example.demo.sockets.server.shared.service.SocketServer;
import com.example.demo.sockets.server.shared.service.impl.SocketServerImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SocketServerImpl.class })
@TestPropertySource("classpath:test.properties")
public class SocketClientTest {

	// Init flag
	private static SocketServerThread _st = null;

	@Autowired
	private SocketServer _server;

	@Value("${socket.server.host}")
	private String host;

	@Value("${socket.server.port}")
	private int port;
	
	@Before
	public void init() {
		if (_st == null) {
			// Start server in separate thread
			_st = new SocketServerThread(_server);
			_st.start();
		}
	}

	@Test
	public void test() {
SocketClient client = null;
		
		String server =  host + ":" + port;
		
		try {
			client = new SocketClient(host, port);
		} catch (IOException | JAXBException e) {
			fail("Error connecting to server -" + e.getMessage());;
		}
		

		ShipOrder order = null;
		try {
			try {
				order = client.parse(client.read());
			} catch (IOException | JAXBException | ClassCastException e) {
				fail("Data read from " + server + " failed - " + e.getMessage());
			}
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				// Ignore exception
			}
		}

		assertEquals("ZZ-99", order.getOrderId());
		assertEquals("Some Person", order.getOrderPerson());
		assertEquals(2, order.getItem().size());
	}

	@AfterClass
	public static void clean() {
		if (_st != null)
			_st.shutdown();
	}
}

class SocketServerThread extends Thread {

	private SocketServer server;

	public SocketServerThread(SocketServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		server.run();
	}
	
	public void shutdown() {
		if (server != null)
			server.shutdown();
	}

}