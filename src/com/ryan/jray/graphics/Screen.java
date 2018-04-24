package com.ryan.jray.graphics;

import com.ryan.jray.utils.Color;
import com.ryan.jray.utils.MathUtils;

public class Screen {
	public int WIDTH;
	public int HEIGHT;
	public double ASPECT;
	public int[] pixels;
	public int renderHeight;
	public Screen(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
		this.ASPECT = (double) this.WIDTH / (double) this.HEIGHT;
		
		this.renderHeight = (int) (this.HEIGHT*1);
		
		System.out.println(this.ASPECT);
	}

	public void drawColum(int color, int x, int height) {
		if (height > this.renderHeight)
			height = this.renderHeight;

		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		for (int i = (this.renderHeight / 2) - (height / 2); i < (this.renderHeight / 2) + (height / 2); i++)
			this.pixels[i * (WIDTH) + x] = color;

	}

	public void drawColumSprite(Sprite sprite, int x, double y, int height) {
		int oHeight = height;
		if (height > this.renderHeight)
			height = this.renderHeight;
		
		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		int yy = (int) Math.floor(y*sprite.SIZE);
		if(yy>sprite.SIZE-1)yy=sprite.SIZE-1;
		if(yy<0)yy=0;
		
		for (int i = (this.renderHeight / 2) - (height / 2); i < (this.renderHeight / 2) + (height / 2); i++) {
			int xx = MathUtils.map(i, (this.renderHeight / 2) - (oHeight / 2), (this.renderHeight / 2) + (oHeight / 2), 0, sprite.SIZE);
			this.pixels[i * (WIDTH) + x] = sprite.pixels[xx * (sprite.SIZE) + yy];
		}
	}
	public void drawColumSprite(Sprite sprite, int x, double y, int height,double brightness) {
		int oHeight = height;
		if (height > this.renderHeight)
			height = this.renderHeight;
		
		if (x < 0)
			x = 0;
		if (x > this.WIDTH)
			x = this.WIDTH;

		int yy = (int) Math.floor(y*sprite.SIZE);
		if(yy>sprite.SIZE-1)yy=sprite.SIZE-1;
		if(yy<0)yy=0;
		
		for (int i = (this.renderHeight / 2) - (height / 2); i < (this.renderHeight / 2) + (height / 2); i++) {
			int xx = MathUtils.map(i, (this.renderHeight / 2) - (oHeight / 2), (this.renderHeight / 2) + (oHeight / 2), 0, sprite.SIZE);
			this.pixels[i * (WIDTH) + x] = Color.lerp(new Color(0,0,0), new Color(sprite.pixels[xx * (sprite.SIZE) + yy]), brightness).toInt();
		}
	}
	public void drawSprite(Sprite sprite,int x,int y,int w,int h) {
		for(int sx =0;sx<w;sx++) {
			for(int sy =0;sy<h;sy++) {
				int xx = MathUtils.map(sx, 0, w, 0, sprite.SIZE);
				int yy = MathUtils.map(sy, 0, h, 0, sprite.SIZE);
				this.pixels[(sy+y) * (WIDTH) + (sx+x)] = sprite.pixels[yy * (sprite.SIZE) + xx];
			}
		}
	}
	public void clear() {
		for (int i = 0; i < pixels.length/2; i++) {
			this.pixels[i] = 0xababab;
		}
		for (int i = (int) pixels.length/2; i < pixels.length; i++) {
			this.pixels[i] = 0x101010;
		}
	}
}
