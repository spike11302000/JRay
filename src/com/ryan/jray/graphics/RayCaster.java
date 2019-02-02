package com.ryan.jray.graphics;

import com.ryan.jray.entity.Entity;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObject;
import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.utils.Vector2;

public class RayCaster {
	public Vector2 position; // Starting position of the ray.
	private Vector2 rayPosition; // Current position of the ray.
	public double angle; // Angle of the ray in degrees;
	public double maxDistance;// Max distance the ray can go.
	public double step; // the step size of the ray.
	private Map map; // The current map.
	private Vector2 rayVelocity = new Vector2(); // The ray's velocity.
	public RayObject test = new RayObject(0, new MapObject(MapObjectType.COLOR, 0x0a0a0a));
	public int counter = 0;

	public RayCaster() {
		this.position = new Vector2();
		this.rayPosition = new Vector2();
		this.angle = 0;
		this.maxDistance = 0;
	}

	public RayCaster(Vector2 vec, double maxDist, double step) {
		this.position = vec;
		this.rayPosition = vec;
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

	/*
	 * public RayObject test_test(double angle) { double rad =
	 * Math.toRadians(angle)%(Math.PI*2.0); if (angle < 0) angle +=
	 * (Math.PI*2.0);
	 * 
	 * boolean right = (rad > (Math.PI * 2.0) * 0.75 || rad < (Math.PI * 2.0) *
	 * 0.25); boolean up = (rad < 0.0 || rad > Math.PI); this.rayVelocity = new
	 * Vector2(Math.sin(rad), Math.cos(rad)); double dist = 0; // double
	 * textureX; double slope = this.rayVelocity.x / this.rayVelocity.y; double
	 * dX = right ? 1.0 : -1.0; double dY = dX * slope; this.rayPosition.x =
	 * right ? Math.ceil(this.position.x) : Math.floor(this.position.x);
	 * this.rayPosition.y = this.position.y + (this.rayPosition.x -
	 * this.position.x) * slope;
	 * 
	 * 
	 * double texX; double texY; boolean isPortal = false; Vector2 portal = new
	 * Vector2(); do { double wallX = this.rayPosition.x + (right ? 0.0 : -1.0);
	 * double wallY = this.rayPosition.y;
	 * 
	 * MapObject mo = map.checkPoint(this.rayPosition); // Checks if there
	 * 
	 * if (mo != null && mo.visible) {
	 * 
	 * if (mo.type == MapObjectType.COLOR) return new RayObject(dist, mo); else
	 * if (mo.type == MapObjectType.ABERRATION) { // if(currentSpaceWarp !=
	 * mo.aberrationScale) { //currentSpaceWarp = mo.aberrationScale;
	 * //spaceWarp.div(mo.aberrationScale); // } } else if (mo.type ==
	 * MapObjectType.PORTAL) { this.rayPosition.x = mo.portalExit.x;
	 * this.rayPosition.y = mo.portalExit.y; isPortal = true; portal.x =
	 * mo.portalExit.x; portal.y = mo.portalExit.y; } else { texX =
	 * this.rayPosition.x % 1; texY = this.rayPosition.y % 1; return new
	 * RayObject(dist , mo, new Vector2(texX, texY), new
	 * Vector2(this.rayPosition.x, this.rayPosition.y)); } }
	 * 
	 * 
	 * 
	 * dist = dist + new Vector2().distance(new Vector2(dX,dY));
	 * this.rayPosition.add(new Vector2(dX,dY));
	 * 
	 * } while (dist < this.maxDistance); dist = 0; slope = this.rayVelocity.y /
	 * this.rayVelocity.x; dY = up ? -1.0 : 1.0; dX = dY * slope;
	 * this.rayPosition.y = up ? Math.floor(this.position.y) :
	 * Math.ceil(this.position.y); this.rayPosition.x = this.position.x +
	 * (this.rayPosition.y - this.position.y) * slope; do { double wallY =
	 * this.rayPosition.y + (up ? -1.0 : 0.0); double wallX =
	 * this.rayPosition.x;
	 * 
	 * MapObject mo = map.checkPoint(this.rayPosition); // Checks if there
	 * 
	 * if (mo != null && mo.visible) {
	 * 
	 * if (mo.type == MapObjectType.COLOR) return new RayObject(dist, mo); else
	 * if (mo.type == MapObjectType.ABERRATION) { // if(currentSpaceWarp !=
	 * mo.aberrationScale) { //currentSpaceWarp = mo.aberrationScale;
	 * //spaceWarp.div(mo.aberrationScale); // } } else if (mo.type ==
	 * MapObjectType.PORTAL) { this.rayPosition.x = mo.portalExit.x;
	 * this.rayPosition.y = mo.portalExit.y; isPortal = true; portal.x =
	 * mo.portalExit.x; portal.y = mo.portalExit.y; } else { texX =
	 * this.rayPosition.x % 1; texY = this.rayPosition.y % 1; return new
	 * RayObject(dist , mo, new Vector2(texX, texY), new
	 * Vector2(this.rayPosition.x, this.rayPosition.y)); } }
	 * 
	 * 
	 * 
	 * dist = dist + new Vector2().distance(new Vector2(dX,dY));
	 * this.rayPosition.add(new Vector2(dX,dY));
	 * 
	 * } while (dist < this.maxDistance); test.distance = this.maxDistance;
	 * return test; }
	 */
	public RayObject test(double angle) {
		counter++;
		this.rayPosition.x = this.position.x;// Setting the current position
												// with
												// the starting position.
		this.rayPosition.y = this.position.y;
		double rad = Math.toRadians(angle); // Convert degrees to radians.
		double bsteps = 25;
		this.rayVelocity.x = Math.sin(rad) / bsteps; // Calculate the velocity
														// of the ray.
		this.rayVelocity.y = -Math.cos(rad) / bsteps;
		double texX;
		double texY;
		double dist = 0;
		double dist2 = 0;
		boolean isPortal = false;
		Vector2 portal = new Vector2();
		boolean close = false;
		Vector2 currentSpaceWarp = new Vector2(1, 1);
		MapObjectType lastType = null;
		do {
			Vector2 spaceWarp = this.rayVelocity.clone();

			MapObject mo = map.checkPoint(this.rayPosition); // Checks if there
																// is a object
																// and
																// return the
																// MapObject.
			if (mo != null && mo.visible) { // Check is the object is there or
											// not.
				if (!close && (mo.type != MapObjectType.ABERRATION)) {
					close = true;
					spaceWarp.div(currentSpaceWarp);
					if (lastType == MapObjectType.ABERRATION)
						this.rayPosition.sub(spaceWarp);
					else
						this.rayPosition.sub(this.rayVelocity);

					this.rayVelocity.mult(new Vector2(this.step * bsteps, this.step * bsteps));
					// spaceWarp = this.rayVelocity.clone();
					continue;
				} else {
					if (mo.type == MapObjectType.COLOR)
						return new RayObject(dist + dist2, mo);
					else if (mo.type == MapObjectType.ABERRATION) {
						// if(currentSpaceWarp != mo.aberrationScale) {
						currentSpaceWarp = mo.aberrationScale;
						spaceWarp.div(mo.aberrationScale);
						// }
					} else if (mo.type == MapObjectType.PORTAL) {
						this.rayPosition.x = mo.portalExit.x;
						this.rayPosition.y = mo.portalExit.y;
						isPortal = true;
						portal.x = mo.portalExit.x;
						portal.y = mo.portalExit.y;
					} else {
						texX = this.rayPosition.x % 1;
						texY = this.rayPosition.y % 1;
						return new RayObject(dist + dist2, mo, new Vector2(texX, texY), new Vector2(this.rayPosition.x, this.rayPosition.y)); // Return
																																				// a
																																				// RayObject
																																				// with
																																				// distance
						// and
						// MapObject.
					}
				}

			}
			if (mo == null)
				lastType = null;
			else
				lastType = mo.type;
			dist = dist + new Vector2().distance(this.rayVelocity); // Gets the
																	// distance
																	// of the
																	// ray from
																	// the
			this.rayPosition.add(spaceWarp); // Adds the velocity to the current
												// ray position.
			// starting point.
			// dist = this.position.distance(spaceWarp);
			this.rayVelocity.mult(new Vector2(1 + this.step / 10, 1 + this.step / 10));
		} while (dist < this.maxDistance); // Checks if the max distance has
											// been pasted.

		test.distance = this.maxDistance;
		return test;
	}

	public Entity EntityTest(double angle) {
		this.rayPosition.x = this.position.x;// Setting the current position
												// with
												// the starting position.
		this.rayPosition.y = this.position.y;
		double rad = Math.toRadians(angle); // Convert degrees to radians.
		this.rayVelocity.x = Math.sin(rad) * (this.step * 2); // Calculate the
																// velocity of
																// the ray.
		this.rayVelocity.y = -Math.cos(rad) * (this.step * 2);

		return null;
	}
}
