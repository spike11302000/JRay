package com.ryan.jray.graphics;

import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObject;
import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.utils.Vector2;

public class RayCaster {
	public Vector2 position; // Starting position of the ray.
	private Vector2 rayPositon; // Current position of the ray.
	public double angle; // Angle of the ray in degrees;
	public double maxDistance;// Max distance the ray can go.
	public double step; // the step size of the ray.
	private Map map; // The current map.
	private Vector2 rayVelocity = new Vector2(); // The ray's velocity.
	public RayObject test = new RayObject(0, new MapObject(0x0a0a0a));

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
		int side = 0;
		Vector2 sideDist = new Vector2();
		this.rayPositon.x = this.position.x;// Setting the current position with
											// the starting position.
		this.rayPositon.y = this.position.y;
		double rad = Math.toRadians(angle); // Convert degrees to radians.
		this.rayVelocity.x = Math.sin(rad) * this.step; // Calculate the
														// velocity of the ray.
		this.rayVelocity.y = -Math.cos(rad) * this.step;
		if (rayVelocity.x < 0)
			sideDist.x = this.position.x - Math.floor(this.position.x)*this.rayVelocity.x;
		else
			sideDist.x = Math.floor(this.position.x + 1 - this.position.x)*this.rayVelocity.x;

		if (rayVelocity.y < 0)
			sideDist.y = this.position.y - Math.floor(this.position.y)*this.rayVelocity.y;
		else
			sideDist.y = Math.floor(this.position.y + 1 - this.position.y)*this.rayVelocity.y;

		if (sideDist.x < sideDist.y)
			side = 0;
		else
			side = 1;
		double texX;
		double dist;
		do {
			dist = this.position.distance(this.rayPositon); // Gets the distance of the ray from the starting point.
			this.rayPositon.add(this.rayVelocity); // Adds the velocity to the current ray position.
			MapObject mo = map.checkPoint(this.rayPositon); // Checks if there is a object and return the MapObject.
			if (mo != null && mo.visible) // Check is the object is there or not.
				if(mo.type == MapObjectType.COLOR)
					return new RayObject(dist, mo);
				else{
					if(side==1)
						texX = this.rayPositon.y-Math.floor(this.rayPositon.y);
					else
						texX = this.rayPositon.x-Math.floor(this.rayPositon.x);
					return new RayObject(dist, mo,texX); // Return a RayObject with distance and MapObject.
				}
			this.rayVelocity.mult(new Vector2(1 + this.step / 10, 1 + this.step / 10));
		} while (dist < this.maxDistance); // Checks if the max distance has been pasted.

		test.distance = this.maxDistance;
		return test;
	}

}
