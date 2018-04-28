package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.ryan.jray.network.packet.Packet;
import com.ryan.jray.utils.Serializer;

public class Client {
	public Socket socket;
	public String UserName;
	public DataInputStream dataIn;
	public DataOutputStream dataOut;
	public boolean isConnected = false;
	public int pingCounter = 3600; //60 seconds
	public Client() {
		
	}
	public Client(String ip,int port) {
		try {
			socket = new Socket(ip,port);
			this.isConnected = true;
		} catch (IOException e) {
			this.isConnected = false;
			System.out.println("Unable to connect to server!");
		}
		try {
			this.dataOut = new DataOutputStream(this.socket.getOutputStream());
			this.dataIn = new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void send(Packet packet) throws IOException {
		this.dataOut.write(Serializer.serialize(packet));
	}
	
}
