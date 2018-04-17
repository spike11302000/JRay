package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;
import com.ryan.jray.controls.Keyboard;
import com.ryan.jray.graphics.Camera;

public class Player extends Entity {
	public double rotation;
	private Keyboard key;
	private Camera cam;
	public double speed;

	public Player(Vector2 pos, double rot, Keyboard key, Camera cam) {
		this.position = pos;
		this.rotation = rot;
		this.key = key;
		this.cam = cam;
		this.speed = .1;
	}

	public void update() {
		if (this.key.rotR)
			this.rotation += 2;
		if (this.key.rotL)
			this.rotation -= 2;
		Vector2 vel = new Vector2(Math.sin(Math.toRadians(this.rotation)) * this.speed,
				-Math.cos(Math.toRadians(this.rotation)) * this.speed);
		if ((this.key.up && this.key.left) || (this.key.up && this.key.right) || (this.key.down && this.key.left)
				|| (this.key.down && this.key.right))
			vel.div(new Vector2(2, 2));

		if (this.key.up)
			this.position.add(vel);

		if (this.key.down)
			this.position.sub(vel);

		if (this.key.left) {
			this.position.x += vel.y;
			this.position.y -= vel.x;
		}
		if (this.key.right) {
			this.position.x -= vel.y;
			this.position.y += vel.x;
		}
		this.cam.position = this.position;
		this.cam.rotation = this.rotation;
	}
}
