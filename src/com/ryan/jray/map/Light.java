package com.ryan.jray.map;

import com.ryan.jray.utils.Color;
import com.ryan.jray.utils.Vector2;

public class Light {
	public Vector2 position;
	public double brightness;
	public Light(Vector2 pos,double b) {
		this.position = pos;
		this.brightness = b;
	}
	
}
