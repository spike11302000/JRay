package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.ryan.jray.entity.Entity;
import com.ryan.jray.entity.MPlayer;
import com.ryan.jray.entity.Player;
import com.ryan.jray.map.Map;
import com.ryan.jray.network.packet.*;
import com.ryan.jray.utils.Config;
import com.ryan.jray.utils.Serializer;

public class Server implements Runnable {
	public Map map;
	public Config config;
	public ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

	private Thread t;
	private byte[] buffer = new byte[1024 * 2];
	private DatagramSocket socket;

	public Server(Config cfg) {
		this.config = cfg;
		System.out.println(config);
		try {
			socket = new DatagramSocket(this.config.getInt("port"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to start server, check to see if you have another app open on port: "
					+ config.getString("port") + "!");
			System.exit(0);
		}
	}

	int tick = 0;

	public void update() {
		map.update();
		// System.out.println(this.clients.size());
		tick++;
		for (int i = 0; i < this.clients.size(); i++) {
			ServerClient c = this.clients.get(i);
			c.pingCounter--;
			if (c.pingCounter <= 0)
				c.isConnected = false;
			if (!c.isConnected) {
				try {
					System.out.println(c.from+" left the game.");
					this.broadcast(new PacketMessage(c.from+" left the game."));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.map.removedEntities.add(c.player.ID);
				this.map.entities.remove(c.player);
				this.clients.remove(c);
				continue;
			}
		}
	
		if (tick % 1 == 0) {
			for (int i = 0; i < this.map.removedEntities.size(); i++) {
				try {
					this.broadcast(new PacketEntityRemove(this.map.removedEntities.get(i)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.map.removedEntities.clear();
			for (int i = 0; i < this.map.entities.size(); i++) {
				try {
					this.broadcast(new PacketEntity(this.map.entities.get(i)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void broadcast(Packet packet) throws IOException {
		for (int i = 0; i < this.clients.size(); i++) {
			ServerClient c = this.clients.get(i);
			c.send(packet);
		}
	}

	public void run() {
		System.out.println("test");
		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
				socket.receive(packet);
				this.handlePacket(packet);
			} catch (IOException | ClassNotFoundException e) {
				// e.printStackTrace();
			}
		}
	}

	public ServerClient getClient(String from) {
		for (int ii = 0; ii < this.clients.size(); ii++) {
			if (this.clients.get(ii).from.equals(from))
				return this.clients.get(ii);
		}
		return null;
	}

	public void handlePacket(DatagramPacket p) throws IOException, ClassNotFoundException {
		Packet packet = (Packet) Serializer.deserialize(p.getData());
		if (packet == null) {
			System.out.println("null packet");
			return;
		}
		if (packet instanceof PacketJoin && packet.from != null) {

			boolean exist = false;
			for (int ii = 0; ii < this.clients.size(); ii++) {
				if (this.clients.get(ii).from.equals(packet.from))
					exist = true;
			}
			if (!exist) {
				ServerClient c = new ServerClient(this.socket, p.getAddress(), p.getPort(), packet.from);
				this.clients.add(c);
				System.out.println(c.from + " joined the server!");
				broadcast(new PacketMessage(c.from + " joined the server!"));

			}
			return;
		}
		ServerClient c = this.getClient(packet.from);
		if (c == null)
			return;
		c.pingCounter=300;
		if (packet instanceof PacketMessage) {
			System.out.println(((PacketMessage) packet).msg);
		} else if (packet instanceof PacketEntity) {
			// System.out.println("test123");
			Entity ent = ((PacketEntity) packet).entity;
			if (ent instanceof MPlayer)
				c.player = (MPlayer) ent;

			int index = this.map.EntityIndex(ent.ID);
			if (index == -1) {
				this.map.entities.add(ent);
			} else {
				this.map.entities.get(index).position = ent.position;
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
