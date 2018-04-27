package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClient {

	public Socket socket;
	public ObjectInputStream objIn;
	public ObjectOutputStream objOut;
	public boolean disconnect = false;
	public String userName;

	public ServerClient(Socket socket) throws IOException {
		this.socket = socket;

	}
}
