package com.example.demo.sockets.server.shared.service.impl;

import java.io.*;
import java.net.*;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.sockets.server.shared.service.SocketServer;

/**
 * Single threaded socket server
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */

@Service
public class SocketServerImpl implements SocketServer {

	private static final String FILE_NAME = "/ship_order.xml";
	
	@Value("${socket.server.port}")
	private int port;
	
	// initialize socket and input output streams
	private ServerSocket server = null;

	// Cached xml file
	private String xml = null;

	// Read buffer size
	private static final int BUFF_SIZE = 8096;

	// End of file
	private static final int EOF = -1;

	private static Logger LOG = LoggerFactory.getLogger(SocketServerImpl.class);

	@PostConstruct
	public void init() throws IOException {
		// Cache xml file
		xml = readXmlFile();

		// Start server
		server = new ServerSocket(port);
	}

	public void run() {
		while (!server.isClosed()) {
			Socket client = null;
			try {
				client = server.accept();
				LOG.info("Accepted connection from " + client.getInetAddress() + ":" + client.getPort());

				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				out.print(xml);
				out.flush();
				LOG.debug("Sent " + xml.length() + " bytes to client.");

				client.close();
			} catch (IOException e) {
				// Same error happened if server terminated or client disconnected.
				if (client != null)
					LOG.error("Sending data to " + client.getPort() + " failed - " + e.getMessage());
			}
		}

	}

	public void shutdown() {
		try {
			server.close();
		} catch (IOException e) {
			// Ignore error
		}
	}

	private String readXmlFile() throws IOException {
		InputStream fpom = this.getClass().getResourceAsStream(FILE_NAME);

		if (fpom == null)
			throw new IOException(FILE_NAME + " resource file not found.");

		int len;
		byte[] buffer = new byte[BUFF_SIZE];
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		while ((len = fpom.read(buffer)) != EOF)
			data.write(buffer, 0, len);
		fpom.close();

		String result = new String(data.toByteArray());
		data.close();

		LOG.debug("Read & cache " + result.length() + " bytes from " + FILE_NAME + " file.");
		LOG.trace(result.replaceAll("\\n", ""));

		return result;
	}
}
