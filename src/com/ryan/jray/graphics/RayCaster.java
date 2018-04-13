package com.ryan.jray.graphics;

import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObject;
import com.ryan.jray.utils.Vector2;

public class RayCaster {
	public Vector2 position;
	private Vector2 rayPositon;
	public double angle; // in degrees;
	private Map map;
	public double maxDistance;
	private Vector2 rayVelocity = new Vector2();
	public double step;

	public RayCaster() {
		this.position = new Vector2();
		this.rayPositon = new Vector2();
		this.angle = 0;
		this.maxDistance = 0;
	}

	public RayCaster(Vector2 vec, double maxDist, double step) {
		this.position = vec;
		this.rayPositon = vec;
		this.angle = 0;
		this.maxDistance = maxDist;
		this.step = step;
	}

	public void setPos(Vector2 pos) {
		this.position = pos;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public RayObject test(double angle) {
		this.angle = angle;
		this.rayPositon = this.position;
		this.rayVelocity.x = Math.sin(Math.toRadians(this.angle)) * this.step;
		this.rayVelocity.y = -Math.cos(Math.toRadians(this.angle)) * this.step;
		while (this.position.distance(this.rayPositon) < this.maxDistance) {
			this.rayPositon = this.rayPositon.add(this.rayVelocity);
			MapObject mo = map.checkPoint(this.rayPositon);
			if (mo.visible)
				return new RayObject(this.position.distance(this.rayPositon),mo);
		}
		return new RayObject(this.maxDistance,new MapObject());
	}

}
