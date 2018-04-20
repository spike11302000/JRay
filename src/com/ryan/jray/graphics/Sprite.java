package com.ryan.jray.graphics;

public class Sprite {
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	public static Sprite test = new Sprite(512, 0, 0, SpriteSheet.test);
	public static Sprite iron = new Sprite(16, 6, 3, SpriteSheet.tiles);
	public static Sprite hdbrick = new Sprite(512, 0, 0, SpriteSheet.brick);

	public static Sprite error = new Sprite(16, 0xff00ff);

	public static Sprite getSprite(int id) {
		switch (id) {
		case 0:
			return test;
		case 1:
			return iron;
		case 2:
			return hdbrick;
		}
		return error;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}
