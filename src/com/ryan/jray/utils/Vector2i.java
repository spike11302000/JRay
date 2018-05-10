package com.ryan.jray.utils;

import java.io.Serializable;

public class Vector2i implements Serializable {
	public int x;
	public int y;

	public Vector2i() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int distance(Vector2i vec) {
		return (int) Math.sqrt((vec.x - this.x) * (vec.x - this.x) + (vec.y - this.y) * (vec.y - this.y));
	}

	public void add(Vector2i vec) {
		this.x = this.x + vec.x;
		this.y = this.y + vec.y;
	}

	public void sub(Vector2i vec) {
		this.x = this.x - vec.x;
		this.y = this.y - vec.y;
	}

	public void mult(Vector2i vec) {
		this.x = this.x * vec.x;
		this.y = this.y * vec.y;
	}

	public void div(Vector2i vec) {
		this.x = this.x / vec.x;
		this.y = this.y / vec.y;
	}

	public String toString() {
		return "X:" + this.x + " Y:" + this.y;
	}
}
