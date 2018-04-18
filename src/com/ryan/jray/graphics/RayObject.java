package com.ryan.jray.graphics;

import com.ryan.jray.map.MapObject;

public class RayObject {
	public MapObject mapObject;
	public double distance;
	public double TextureX;
	public RayObject(double dist,MapObject mo) {
		this.mapObject = mo;
		this.distance = dist;
	}
	public RayObject(double dist,MapObject mo,double texX){
		this.mapObject = mo;
		this.distance = dist;
		this.TextureX = texX;
	}
}
