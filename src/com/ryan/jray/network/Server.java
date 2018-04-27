package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.ryan.jray.map.Map;
import com.ryan.jray.network.packet.Packet;
import com.ryan.jray.network.packet.PacketMessage;
import com.ryan.jray.utils.Config;

public class Server implements Runnable {
	public Map map;
	public Config config;
	private Thread t;
	public ArrayList<ServerClient> clients = new ArrayList<ServerClient>();
	private ServerSocket socket;

	public Server(Config cfg) {
		this.config = cfg;
		System.out.println(config);
		try {
			socket = new ServerSocket(config.getInt("port"));
			socket.setSoTimeout(10000);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to start server, check to see if you have another app open on port: "
					+ config.getString("port") + "!");
			System.exit(0);
		}
	}

	public void update() throws IOException {
		// System.out.println(this.clients.size());
		for (int i = 0; i < this.clients.size(); i++) {
			ServerClient c = this.clients.get(i);
			System.out.println(c.objIn.available());
			Packet packet = null;

			try {
				packet = (Packet) c.objIn.readObject();
			} catch (ClassNotFoundException e) {
				c.disconnect = true;
			}
			if (packet != null) {
				if (packet instanceof PacketMessage)
					System.out.println(((PacketMessage) packet).msg);
			}
			if (c.disconnect) {
				System.out.println("User Disconnected: " + c.userName);
				this.clients.remove(c);
			}
		}

	}

	public void run() {
		System.out.println("test");
		while (true) {
			try {
				Socket server = socket.accept();
				System.out.println("Client Connected: " + server.getLocalAddress());
				ServerClient client = new ServerClient(server);
				client.objOut = new ObjectOutputStream(server.getOutputStream());
				client.objIn = new ObjectInputStream(server.getInputStream());

				client.objOut.writeObject(new PacketMessage("hello world!"));
				this.clients.add(client);
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
	}

	public void start() {
		System.out.println("Starting Socket Thread");
		if (t == null) {
			t = new Thread(this, "Socket Thread");
			t.start();
		}
	}
}
