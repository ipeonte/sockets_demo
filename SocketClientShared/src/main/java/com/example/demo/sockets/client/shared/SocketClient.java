package com.example.demo.sockets.client.shared;

import java.io.*;
import java.net.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.example.demo.sockets.client.shared.binding.ShipOrder;

/**
 * Socket client
 * 
 * @author Igor Peonte <igor.144@gmail.com>
 *
 */
public class SocketClient {

	// initialize socket and input output streams
	private Socket _socket = null;
	private DataInputStream _input = null;

	// Read buffer size
	private static final int BUFF_SIZE = 8096;

	// End of file
	private static final int EOF = -1;

	// XML parser
	private final Unmarshaller _um;

	public SocketClient(String address, int port) throws UnknownHostException, IOException, JAXBException {
		// establish a connection
		_socket = new Socket(address, port);

		// takes input from terminal
		_input = new DataInputStream(_socket.getInputStream());

		_um = JAXBContext.newInstance("com.example.demo.sockets.client.shared.binding").createUnmarshaller();
	}

	public String read() throws IOException {
		// Output buffer
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Input buffer
		byte[] buff = new byte[BUFF_SIZE];

		// keep reading until "Over" is input
		int len;
		while ((len = _input.read(buff)) != EOF)
			out.write(buff, 0, len);

		return new String(out.toByteArray());
	}

	public ShipOrder parse(String xml) throws JAXBException {
		ByteArrayInputStream data = new ByteArrayInputStream(xml.getBytes());
		return (ShipOrder) _um.unmarshal(data);
	}

	public void disconnect() throws IOException {
		_input.close();
		_socket.close();
	}

}
