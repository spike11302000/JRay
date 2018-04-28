package com.ryan.jray;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ryan.jray.controls.Keyboard;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.entity.MPlayer;
import com.ryan.jray.entity.Player;
import com.ryan.jray.graphics.Camera;
import com.ryan.jray.graphics.Screen;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.TextMap;
import com.ryan.jray.network.Client;
import com.ryan.jray.network.Server;
import com.ryan.jray.network.packet.Packet;
import com.ryan.jray.network.packet.PacketEntity;
import com.ryan.jray.network.packet.PacketEntityRemove;
import com.ryan.jray.network.packet.PacketJoin;
import com.ryan.jray.network.packet.PacketMessage;
import com.ryan.jray.network.packet.PacketPing;
import com.ryan.jray.utils.Config;
import com.ryan.jray.utils.MathUtils;
import com.ryan.jray.utils.Serializer;
import com.ryan.jray.utils.Vector2;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 7556956091489761808L;
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	public static int Scale = 2;
	public final static String TITLE = "JRay";
	private JFrame frame;
	private Thread thread;
	private boolean running = false;
	private BufferedImage image;
	private int[] pixels;
	public static Config config;
	public static Main game;
	public static Screen screen;
	public static Camera camera;
	public Map map;
	public static String configPath = "client.txt";
	public static Keyboard key;
	public static Player player;
	public static Server server;
	public static boolean isServer = false;
	public static Client client = new Client();

	public static void main(String[] args) {

		if (args.length != 0)
			if (args.length > 1) {
				if (args[0].equals("server")) {
					isServer = true;
					configPath = args[1];
				}

			} else {
				System.out.println("Invalid Args");
				System.out.println("java -jar jray.jar server [config file]");
				System.exit(0);
			}

		game = new Main();
		if (!isServer) {

			game.frame = new JFrame();
			game.frame.setResizable(false);
			game.frame.setTitle(Main.TITLE);
			game.frame.add(game);
			game.frame.pack();
			game.frame.setLocationRelativeTo(null);
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setVisible(true);
		}
		game.start();
	}

	public Main() {
		config = new Config(configPath);
		if (isServer) {
			server = new Server(config);
			server.map = new TextMap("test.map");
			server.start();
		} else {

			WIDTH = config.getInt("width");
			HEIGHT = config.getInt("height");
			Scale = config.getInt("scale");
			image = new BufferedImage(WIDTH / Scale, HEIGHT / Scale, BufferedImage.TYPE_INT_RGB);
			pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
			Dimension size = new Dimension(WIDTH, HEIGHT);
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			// Map text = new TextMap("test.map");
			key = new Keyboard();
			map = new TextMap("test.map");
			// map = new Map(10,10);
			// map.entities.add(new Entity(new Vector2(2.5,9)));

			// map.entities.add(new Entity(new Vector2(7.5,3.5)));
			screen = new Screen(WIDTH / Scale, HEIGHT / Scale);
			camera = new Camera();
			camera.setMap(map);
			player = new Player(new Vector2(1.5, 1.5), 180, key, camera);
			player.setMap(map);
			addKeyListener(key);

		}

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "GameThread");
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int fps, ups;

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			if (!isServer)
				render();
			frames++;

			while (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				// System.out.println(TITLE + " | " + updates + " ups, " + frames + " fps");
				if (!isServer) {
					frame.setTitle(TITLE + " | " + updates + " ups, " + frames + " fps");

				}
				fps = frames;
				ups = updates;
				frames = 0;
				updates = 0;
			}
		}

	}

	@Override
	public void setSize(int width, int height) {
		System.out.println("setSize");
	}

	int tick = 0;

	public void update() {
		tick++;
		if (isServer) {
			server.update();
		} else {
			player.update();
			key.update();
			if (!client.isConnected) {
				map.update();
			}
			camera.update();
			if (key.keys[67]) {
				key.keys[67] = false;
				String[] addr = JOptionPane.showInputDialog("Server Address:").split(":");
				String username = JOptionPane.showInputDialog("UserName:");
				if (addr.length == 2) {
					client = new Client(addr[0], Integer.parseInt(addr[1]));
					client.UserName = username;
					player.client = client;
					try {
						client.send(new PacketJoin(client.UserName));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (client.isConnected) {
				client.pingCounter--;

				Packet packet = null;
				try {
					if (client.dataIn.available() > 0) {
						packet = (Packet) new ObjectInputStream(client.dataIn).readObject();
					}
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("Lost connection to server!");
					client.isConnected = false;
					try {
						client.socket.close();
					} catch (IOException e1) {
					}
				}
				if (client.pingCounter == 0) {
					// client.isConnected = false;
					System.out.println("Lost connection to server!");
					// packet = null;
				}
				if (packet != null) {
					if (packet instanceof PacketMessage) {
						System.out.println(((PacketMessage) packet).msg);
					} else if (packet instanceof PacketPing) {
						client.pingCounter = 3600;
						try {
							client.send(new PacketPing());
						} catch (IOException e) {
							e.printStackTrace();
						}
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
				if (client.isConnected && tick % 4 == 0) {
					try {
						client.send(new PacketEntity(player.getMPlayer()));
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		}

	}

	Font f = new Font("Dialog", Font.PLAIN, 15);

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setFont(f);
		g.setColor(Color.white);
		screen.clear();
		camera.render(screen);
		player.render(screen);
		for (int i = 0; i < (WIDTH / Scale) * (HEIGHT / Scale); i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.drawString(player.position.toString(), 5, 15);
		g.drawString("Entities: " + map.entities.size(), 5, 30);
		g.drawString("Lights: " + map.lights.size(), 5, 45);
		g.drawString("Rays Per Frame: " + Main.camera.rpf, 5, 60);
		g.dispose();
		bs.show();
	}

}
