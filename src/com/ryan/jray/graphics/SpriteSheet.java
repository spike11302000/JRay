package com.ryan.jray.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.ryan.jray.utils.Logger;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public int[] pixels;
	public static SpriteSheet test = new SpriteSheet("/color.png",512);
	public static SpriteSheet tiles = new SpriteSheet("/bricks.db32.png",336);
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
			Logger.println("Loaded: " + path,Logger.SYSTEM);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
