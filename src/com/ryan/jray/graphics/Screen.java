package com.ryan.jray.graphics;

import com.ryan.jray.utils.MathUtils;

public class Screen {
	public int WIDTH;
	public int HEIGHT;
	public double ASPECT;
	public int[] pixels;

	public Screen(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
		this.ASPECT = (double) this.WIDTH / (double) this.HEIGHT;
		System.out.println(this.ASPECT);
	}

	public void drawColum(int color, int x, int height) {
		if (height > (this.HEIGHT))
			height = (this.HEIGHT);

		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		for (int i = (HEIGHT / 2) - (height / 2); i < (HEIGHT / 2) + (height / 2); i++)
			this.pixels[i * (WIDTH) + x] = color;

	}

	public void drawColumSprite(Sprite sprite, int x, double y, int height) {
		int oHeight = height;
		if (height > (this.HEIGHT))
			height = (this.HEIGHT);
		
		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		int yy = (int) Math.floor((y/100)*(sprite.SIZE-1));
		if(yy>sprite.SIZE)yy=sprite.SIZE-1;
		if(yy<0)yy=0;
		for (int i = (HEIGHT / 2) - (height / 2); i < (HEIGHT / 2) + (height / 2); i++) {
			int xx = MathUtils.map(i, (HEIGHT / 2) - (oHeight / 2), (HEIGHT / 2) + (oHeight / 2), 0, sprite.SIZE);
			this.pixels[i * (WIDTH) + x] = sprite.pixels[xx * (sprite.SIZE) + yy];
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length / 2; i++) {
			this.pixels[i] = 0xababab;
		}
		for (int i = pixels.length / 2; i < pixels.length; i++) {
			this.pixels[i] = 0x101010;
		}
	}
}
