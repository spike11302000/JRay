package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.ryan.jray.entity.MPlayer;
import com.ryan.jray.entity.Player;
import com.ryan.jray.network.packet.Packet;
import com.ryan.jray.utils.Serializer;

public class ServerClient {

	public Socket socket;
	public DataInputStream dataIn;
	public DataOutputStream dataOut;
	public boolean disconnect = false;
	public String UserName;
	public int pingCounter = 3600;
	public MPlayer player;
	public ServerClient(Socket socket) throws IOException {
		this.socket = socket;

	}
	public void send(Packet packet) throws IOException {
		this.dataOut.write(Serializer.serialize(packet));
	}
}
