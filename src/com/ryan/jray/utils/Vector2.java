package com.ryan.jray.utils;

import java.io.Serializable;

public class Vector2 implements Serializable{
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
	}
	public void sub(Vector2 vec) {
		this.x = this.x - vec.x;
		this.y = this.y - vec.y;
	}
	public void mult(Vector2 vec) {
		this.x = this.x * vec.x;
		this.y = this.y * vec.y;
	}
	public void div(Vector2 vec) {
		this.x = this.x / vec.x;
		this.y = this.y / vec.y;
	}
	public String toString() {
		return "X:"+Math.floor(this.x*1000)/1000 + " Y:" + Math.floor(this.y*1000)/1000;
	}
}
