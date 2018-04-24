package com.ryan.jray.graphics;

public class Sprite {
	public final int SIZE;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	public static Sprite test = new Sprite(512, 0, 0, SpriteSheet.test);
	public static Sprite wall1 = new Sprite(16,4,1,SpriteSheet.tiles);
	public static Sprite wall2 = new Sprite(16,4,4,SpriteSheet.tiles);
	public static Sprite wall3 = new Sprite(16,4,7,SpriteSheet.tiles);
	public static Sprite wall4 = new Sprite(16,4,10,SpriteSheet.tiles);
	public static Sprite wall5 = new Sprite(16,4,13,SpriteSheet.tiles);
	public static Sprite wall6 = new Sprite(16,4,16,SpriteSheet.tiles);
	public static Sprite wall7 = new Sprite(16,4,19,SpriteSheet.tiles);
	
	public static Sprite error = new Sprite(16, 0xff00ff);

	public static Sprite getSprite(int id) {
		switch (id) {
		case 0:
			return test;
		case 1:
			return wall1;
		case 2:
			return wall2;
		case 3:
			return wall3;
		case 4:
			return wall4;
		case 5:
			return wall5;
		case 6:
			return wall6;
		case 7:
			return wall7;
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
