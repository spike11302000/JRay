package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.ryan.jray.entity.MPlayer;
import com.ryan.jray.entity.Player;
import com.ryan.jray.network.packet.Packet;
import com.ryan.jray.utils.Serializer;

public class ServerClient {

	public DatagramSocket socket;
	public boolean isConnected = true;
	public String UserName;
	public int pingCounter = 300;
	public MPlayer player;
	public int port;
	public InetAddress address;
	public String from;

	public ServerClient(DatagramSocket socket,InetAddress addr, int port, String from) throws IOException {
		this.socket = socket;
		this.address = addr;
		this.port = port;
		this.from = from;
	}

	public void send(Packet packet) throws IOException {
		byte[] buf = Serializer.serialize(packet);
		DatagramPacket p = new DatagramPacket(buf, buf.length, address, port);
		this.socket.send(p);
		// this.dataOut.write(Serializer.serialize(packet));
	}
}
