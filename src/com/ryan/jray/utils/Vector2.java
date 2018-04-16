package com.ryan.jray.utils;

public class Vector2 {
	public double x;
	public double y;

	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double distance(Vector2 vec) {
		return Math.sqrt((vec.x - this.x) * (vec.x - this.x) + (vec.y - this.y) * (vec.y - this.y));
	}

	public void add(Vector2 vec) {
		this.x = this.x + vec.x;
		this.y = this.y + vec.y;
		//return new Vector2(this.x + vec.x, this.y + vec.y);
	}

	public String toString() {
		return this.x + "," + this.y;
	}
}
