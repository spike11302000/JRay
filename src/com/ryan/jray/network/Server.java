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

import com.ryan.jray.entity.Bullet;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.entity.MPlayer;
import com.ryan.jray.entity.Player;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.TextMap;
import com.ryan.jray.network.packet.*;
import com.ryan.jray.utils.Config;
import com.ryan.jray.utils.Logger;
import com.ryan.jray.utils.Serializer;
import com.ryan.jray.utils.Vector2;

public class Server implements Runnable {
	public Map map;
	public Config config;
	public ArrayList<ServerClient> clients = new ArrayList<ServerClient>();

	private Thread t;
	private byte[] buffer = new byte[1024 * 2];
	private DatagramSocket socket;
	private String CurrentMap;

	public Server(Config cfg) {
		this.config = cfg;
		CurrentMap = config.getString("default-map");
		this.map = new TextMap(CurrentMap + ".map");
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
		tick++;
		for (int i = 0; i < this.clients.size(); i++) {
			ServerClient c = this.clients.get(i);
			c.pingCounter--;
			if (!c.isAlive) {
				c.respawnTimer++;
			} else {
				c.respawnTimer = 0;
			}
			if (c.respawnTimer >= 60.0 * config.getDouble("respawn-time")) {
				c.player.health = 100;
				this.map.entities.get(this.map.EntityIndex(c.player.ID)).health = 100;
				try {
					c.send(new PacketSetPlayer(this.map.getSpawnPoint(), 0, 100));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.player.health = 100;
				c.isAlive = true;
			}
			if (c.pingCounter <= 0)
				c.isConnected = false;
			if (!c.isConnected) {
				try {
					System.out.println(c.username + " left the game.");
					this.broadcast(new PacketMessage(c.username + " left the game."));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// this.map.removedEntities.add(c.player.ID);
				this.map.removeEntity(c.player.ID);
				this.clients.remove(c);
				continue;
			}
		}
		if (tick % 60 == 0) {
			try {
				this.broadcast(new PacketPing());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 0; i < this.map.entities.size(); i++) {
			if (this.map.entities.get(i) instanceof Bullet) {

				Bullet b = (Bullet) this.map.entities.get(i);
				for (int ii = 0; ii < this.clients.size(); ii++) {
					ServerClient c = this.clients.get(ii);
					if (c.player.position.distance(b.position) < .5 && b.Owner != c.player.Owner) {
						c.player.health--;
						this.map.entities.get(this.map.EntityIndex(c.player.ID)).health--;
						if (this.map.entities.get(this.map.EntityIndex(c.player.ID)).health <= 0) {
							if (c.isAlive) {
								try {
									this.broadcast(new PacketMessage(c.username+ " was killed by " + b.Owner));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							c.isAlive = false;

						} else {
							c.isAlive = true;
						}
						// Logger.println("Bullet", Logger.DEBUG);
						b.destroy();

						try {
							c.send(new PacketSetPlayer(null, -1,
									this.map.entities.get(this.map.EntityIndex(c.player.ID)).health));
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}
				}
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
				Entity ent = this.map.entities.get(i);
				try {
					this.broadcast(new PacketEntity(ent));
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
		while (true) {
			try {
				DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
				socket.receive(packet);
				this.handlePacket(packet);
			} catch (IOException | ClassNotFoundException e) {
			}
		}
	}

	public ServerClient getClient(String username) {
		for (int ii = 0; ii < this.clients.size(); ii++) {
			if (this.clients.get(ii).username.equals(username))
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
				if (this.clients.get(ii).username.equals(packet.from))
					exist = true;
			}
			if (!exist) {
				ServerClient c = new ServerClient(this.socket, p.getAddress(), p.getPort(), packet.from);
				this.clients.add(c);
				
				Logger.println(c.username + " joined the server!", Logger.SERVER);
				broadcast(new PacketMessage(c.username + " joined the server!"));
				c.send(new PacketMap(CurrentMap + ".map"));
				c.send(new PacketSetPlayer(this.map.getSpawnPoint(), 0, 100));
			}
			return;
		}
		ServerClient c = this.getClient(packet.from);
		if (c == null)
			return;
		c.pingCounter = 600;
		if (packet instanceof PacketMessage) {
			System.out.println(((PacketMessage) packet).msg);
		} else if (packet instanceof PacketEntity) {
			// System.out.println("test123");
			Entity ent = ((PacketEntity) packet).entity;
			if (ent instanceof MPlayer) {
				c.player = (MPlayer) ent;
				if (c.player.health != ent.health) {
					c.send(new PacketSetPlayer(null, -1, c.player.health));
				}
			}
			int index = this.map.EntityIndex(ent.ID);
			if (index == -1) {
				this.map.entities.add(ent);
			} else {
				this.map.entities.get(index).position = ent.position;
			}
		}
	}

	public void start() {
		Logger.println("Starting Server Thread", Logger.SERVER);
		if (t == null) {
			t = new Thread(this, "Server Thread");
			t.start();
		}
	}
}
