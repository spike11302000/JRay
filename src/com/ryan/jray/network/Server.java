package com.ryan.jray.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	int tick = 0;

	public void update() {
		map.update();
		// System.out.println(this.clients.size());
		tick++;
		for (int ii = 0; ii < this.clients.size(); ii++) {
			ServerClient c = this.clients.get(ii);
			Packet packet = null;
			c.pingCounter--;
			// if(c.socket.getReuseAddress())c.disconnect=true;
			// if();

			if (c.pingCounter == 3000) {
				try {
					c.send(new PacketPing());
				} catch (IOException e) {
					c.disconnect = true;
					continue;
				}
			}
			if (tick % 4 == 0) {
				for(int i=0;i<this.map.removedEntities.size();i++) {
					try {
						c.send(new PacketEntityRemove(this.map.removedEntities.get(i)));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.map.removedEntities.clear();
				for (int i = 0; i < this.map.entities.size(); i++) {
					try {
						c.send(new PacketEntity(this.map.entities.get(i)));
					} catch (IOException e) {
						c.disconnect = true;
						continue;
					}
				}
			}

			if (c.pingCounter == 0) {
				c.disconnect = true;
				continue;
			}
			if (c.disconnect) {
				System.out.println(c.UserName + " left the server!");
				this.clients.remove(c);
				this.map.removeEntity(c.player.ID);
				try {

					broadcast(new PacketMessage(c.UserName + " left the server!"));
				} catch (IOException e) {
				}

			} else {

				// System.out.println(c.objIn.available());

				try {
					// System.out.println(c.dataIn.available());
					if (c.dataIn.available() == 0)
						continue;

					packet = (Packet) new ObjectInputStream(c.dataIn).readObject();

				} catch (ClassNotFoundException | IOException e) {

					c.disconnect = true;
					continue;
				}

				if (packet instanceof PacketMessage) {
					System.out.println(((PacketMessage) packet).msg);
				} else if (packet instanceof PacketJoin) {
					c.UserName = ((PacketJoin) packet).UserName;
					System.out.println(c.UserName + " joined the server!");
					try {
						broadcast(new PacketMessage(c.UserName + " joined the server!"));
					} catch (IOException e) {
					}
				} else if (packet instanceof PacketPing) {
					c.pingCounter = 3600;
				} else if (packet instanceof PacketEntity) {
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
				Socket server = socket.accept();
				System.out.println("Client Connected: " + server.getLocalAddress());
				ServerClient client = new ServerClient(server);
				client.dataOut = new DataOutputStream(server.getOutputStream());
				client.dataIn = new DataInputStream(server.getInputStream());
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
