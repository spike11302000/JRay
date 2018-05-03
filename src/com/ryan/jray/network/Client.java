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
import com.ryan.jray.map.TextMap;
import com.ryan.jray.network.packet.*;
import com.ryan.jray.utils.Logger;
import com.ryan.jray.utils.MathUtils;
import com.ryan.jray.utils.Serializer;

public class Client implements Runnable {
	public DatagramSocket socket;
	public InetAddress address;
	public int port;
	public Map map;
	public Player player;
	public int pingCounter = 600;

	public boolean isConnected = false;
	public boolean mapUpdate = false;
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
		this.pingCounter = 600;
		if (packet instanceof PacketMessage) {
			Logger.println(((PacketMessage) packet).msg, Logger.CLIENT);
		} else if (packet instanceof PacketEntity) {
			Entity ent = ((PacketEntity) packet).entity;
			int index = this.map.EntityIndex(ent.ID);
			if (ent.ID != this.player.ID) {
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
			if (index != -1) {
				if (index <= this.map.entities.size())
					this.map.entities.remove(index);
			}
		} else if (packet instanceof PacketMap) {
			mapUpdate = true;
			PacketMap m = ((PacketMap) packet);
			this.map = new TextMap(m.name);
		} else if (packet instanceof PacketSetPlayer) {
			PacketSetPlayer p = ((PacketSetPlayer) packet);
			if (p.position != null)
				this.player.position = p.position;
			if (p.rotation != -1)
				this.player.rotation = p.rotation;
			if (p.health != -1) {
				this.player.health = p.health;
				if(p.health==0) {
					this.player.isAlive = false;
				}else {
					this.player.isAlive = true;
				}
			}
		}
	}

	int tick = 0;

	public void update() {
		tick++;
		this.pingCounter--;
		if (this.pingCounter <= 0)
			this.isConnected = false;

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
				e.printStackTrace();
			}
		}
	}

	public void start() {
		Logger.println("Starting Client Thread", Logger.CLIENT);
		if (t == null) {
			t = new Thread(this, "Client Thread");
			t.start();
		}
	}
}
