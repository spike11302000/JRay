package com.ryan.jray.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public int[] pixels;
	public static SpriteSheet tiles = new SpriteSheet("/tiles.png",256);
	public static SpriteSheet test = new SpriteSheet("/color.png",512);
	public static SpriteSheet brick = new SpriteSheet("/brick.png",512);
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		this.pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
			System.out.println("Loaded: " + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
