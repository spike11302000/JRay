package com.ryan.jray.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.ryan.jray.Main;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.entity.Player;
import com.ryan.jray.map.Map;
import com.ryan.jray.network.packet.*;
import com.ryan.jray.utils.MathUtils;
import com.ryan.jray.utils.Serializer;

public class Client implements Runnable {
	public DatagramSocket socket;
	public InetAddress address;
	public int port;
	public Map map;
	public Player player;

	public boolean isConnected = false;

	private byte[] buffer = new byte[1024 * 2];
	private Thread t;

	public Client() {

	}

	public Client(String addr, int port) {
		this.port = port;
		this.isConnected = true;
		try {
			this.address = InetAddress.getByName(addr);
		} catch (UnknownHostException e) {
			this.isConnected = false;
			e.printStackTrace();
		}
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			this.isConnected = false;
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
				this.handlePacket((Packet) Serializer.deserialize(packet.getData()));
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(Packet packet) throws IOException {
		// System.out.println("sent packet");
		byte[] buf = Serializer.serialize(packet);
		DatagramPacket p = new DatagramPacket(buf, buf.length, this.address, this.port);
		this.socket.send(p);
	}

	public void handlePacket(Packet packet) {
		if (packet instanceof PacketMessage) {
			System.out.println(((PacketMessage) packet).msg);
		} else if (packet instanceof PacketEntity) {
			Entity ent = ((PacketEntity) packet).entity;
			int index = this.map.EntityIndex(ent.ID);
			if (ent.ID != Main.player.ID) {
				if (index == -1) {
					this.map.entities.add(ent);
				} else {
					Entity e = this.map.entities.get(index);
					ent.position.x = MathUtils.lerp(ent.position.x, e.position.x, 0.5);
					ent.position.y = MathUtils.lerp(ent.position.y, e.position.y, 0.5);
					this.map.entities.set(index, ent);
				}
			}
		} else if (packet instanceof PacketEntityRemove) {
			int index = this.map.EntityIndex(((PacketEntityRemove) packet).EntityID);
			if (index != -1)
				this.map.entities.remove(index);
		}
	}

	int tick = 0;

	public void update() {
		tick++;
		if (tick % 60 == 0)
			try {
				this.send(new Packet(this.player.username));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		if (this.player != null) {
			try {
				this.send(new PacketEntity(player.getMPlayer(), this.player.username));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
