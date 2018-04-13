package com.ryan.jray.graphics;

import com.ryan.jray.map.MapObject;

public class RayObject {
	public MapObject mapObject;
	public double distance;
	public RayObject(double dist,MapObject mo) {
		this.mapObject = mo;
		this.distance = dist;
	}
}
