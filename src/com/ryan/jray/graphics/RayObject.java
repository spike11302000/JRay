package com.ryan.jray.graphics;

import com.ryan.jray.map.MapObject;
import com.ryan.jray.utils.Vector2;

public class RayObject {
	public MapObject mapObject;
	public double distance;
	public Vector2 textureVector;;
	public RayObject(double dist,MapObject mo) {
		this.mapObject = mo;
		this.distance = dist;
	}
	public RayObject(double dist,MapObject mo,Vector2 texVec){
		this.mapObject = mo;
		this.distance = dist;
		this.textureVector = texVec;
	}
}
