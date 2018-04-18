package com.ryan.jray;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

import com.ryan.jray.controls.Keyboard;
import com.ryan.jray.entity.Player;
import com.ryan.jray.graphics.Camera;
import com.ryan.jray.graphics.Screen;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.RandomMap;
import com.ryan.jray.utils.Vector2;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 7556956091489761808L;
	public final int WIDTH = 600;
	public final int HEIGHT = 400;
	public final static String TITLE = "JRay";
	private JFrame frame;
	private Thread thread;
	private boolean running = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public static Main game;
	public static Screen screen;
	public static Camera camera;
	public static Map map;
	public static Keyboard key;
	public static Player player;
	public static void main(String[] args) {
		game = new Main();
		game.frame = new JFrame();
		game.frame.setResizable(false);
		game.frame.setTitle(Main.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setVisible(true);
		game.start();
	}

	public Main() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		key = new Keyboard();
		map = new RandomMap(100, 100);
		screen = new Screen(WIDTH, HEIGHT);
		camera = new Camera();
		camera.rayCaster.setMap(map);
		player = new Player(new Vector2(5,5),180,key,camera);
		addKeyListener(key);
		
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
			render();
			frames++;

			while (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				// System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(TITLE + " | " + updates + " ups, " + frames + " fps");
				// level.updateTimer();
				
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
	public void update() {
		camera.update();
		player.update();
		key.update();
	}
	Font f = new Font("Dialog", Font.PLAIN, 15);
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setFont(f);
		g.setColor(Color.white);
		screen.clear();
		camera.render(screen);
		player.render(screen);
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.drawString(player.position.toString(), 5, 15);
		g.dispose();
		bs.show();
	}

}
