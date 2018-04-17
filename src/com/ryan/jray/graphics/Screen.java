package com.ryan.jray.graphics;

public class Screen {
	public int WIDTH;
	public int HEIGHT;
	public double ASPECT;
	public int[] pixels;
	public Screen(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
		this.ASPECT = (double)this.WIDTH/(double)this.HEIGHT;
		System.out.println(this.ASPECT);
	}

	public void renderColum(int color, int x, int height) {
		if (height > (this.HEIGHT))
			height = (this.HEIGHT);

		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		for (int i = (HEIGHT / 2) - (height / 2); i < (HEIGHT / 2) + (height / 2); i++)
			this.pixels[i * (WIDTH) + x] = color;

	}

	public void clear() {
		for (int i = 0; i < pixels.length/2; i++) {
			this.pixels[i] = 0xababab;
		}
		for (int i = pixels.length/2; i < pixels.length; i++) {
			this.pixels[i] = 0x101010;
		}
	}
}
