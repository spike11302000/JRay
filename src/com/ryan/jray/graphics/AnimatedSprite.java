package com.ryan.jray.graphics;

public class AnimatedSprite {
	public int frame;
	private int counter;
	private int[] frames;
	private int speed;

	public AnimatedSprite(int[] frames, int speed) {
		this.frames = frames;
		this.speed = speed;
		this.frame = 0;
		this.counter = 0;
	}

	public int getTextureID() {
		return this.frames[frame];
	}

	public void update() {
		this.counter++;
		if (this.counter % this.speed == 0)
			this.frame++;
		this.frame %= frames.length;
	}
}
