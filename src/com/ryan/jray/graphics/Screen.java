package com.ryan.jray.graphics;

public class Screen {
	public int WIDTH;
	public int HEIGHT;
	public int[] pixels;

	public Screen(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
	}

	public void renderColum(int color, int x, int height) {
		if (x > 0 && x < this.WIDTH && height < (this.HEIGHT / 2))
			for (int i = (HEIGHT / 2) - (height / 2); i < (HEIGHT / 2) + (height / 2); i++)
				this.pixels[i * (WIDTH) + x] = color;

	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = 0;
		}
	}
}
