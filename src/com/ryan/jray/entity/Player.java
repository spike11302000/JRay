package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;
import com.ryan.jray.controls.Keyboard;
import com.ryan.jray.graphics.Camera;
import com.ryan.jray.graphics.Screen;
import com.ryan.jray.graphics.Sprite;
import com.ryan.jray.map.Map;

public class Player extends Entity {
	public double rotation;
	private Keyboard key;
	private Camera cam;
	public double speed;
	public Map map;

	public Player(Vector2 pos, double rot, Keyboard key, Camera cam) {
		this.position = pos;
		this.rotation = rot;
		this.key = key;
		this.cam = cam;
		this.speed = 0.1;
	}

	int tick = 0;

	public void update() {
		// this.rotation++;
		tick++;
		if (this.key.rotR)
			this.rotation += 2;
		if (this.key.rotL)
			this.rotation -= 2;
		Vector2 vel = new Vector2(Math.sin(Math.toRadians(this.rotation)), -Math.cos(Math.toRadians(this.rotation)));
		Vector2 moveVel = new Vector2(vel.x * this.speed, vel.y * this.speed);
		Vector2 bulletVel = new Vector2(vel.x * .2, vel.y * .2);
		if ((this.key.up && this.key.left) || (this.key.up && this.key.right) || (this.key.down && this.key.left)
				|| (this.key.down && this.key.right))
			moveVel.div(new Vector2(2, 2));

		if (this.key.up) {
			if (map.checkPoint(new Vector2(this.position.x + (moveVel.x * 4), this.position.y)) == null)
				this.position.x += moveVel.x;
			if (map.checkPoint(new Vector2(this.position.x, this.position.y + (moveVel.y * 4))) == null)
				this.position.y += moveVel.y;
		}

		if (this.key.down) {
			if (map.checkPoint(new Vector2(this.position.x - (moveVel.x * 4), this.position.y)) == null)
				this.position.x -= moveVel.x;
			if (map.checkPoint(new Vector2(this.position.x, this.position.y - (moveVel.y * 4))) == null)
				this.position.y -= moveVel.y;
		}
		if (this.key.left) {
			if (map.checkPoint(new Vector2(this.position.x + (moveVel.y * 4), this.position.y)) == null)
				this.position.x += moveVel.y;
			if (map.checkPoint(new Vector2(this.position.x, this.position.y - (moveVel.x * 4))) == null)
				this.position.y -= moveVel.x;
		}
		if (this.key.right) {
			if (map.checkPoint(new Vector2(this.position.x - (moveVel.y * 4), this.position.y)) == null)
				this.position.x -= moveVel.y;
			if (map.checkPoint(new Vector2(this.position.x, this.position.y + (moveVel.x * 4))) == null)
				this.position.y += moveVel.x;
		}

		if (this.key.keys[32] && tick % 1 == 0)
			map.entities.add(new Bullet(new Vector2(this.position.x + vel.x, this.position.y + vel.y), bulletVel));
		this.cam.position = this.position;
		this.cam.rotation = this.rotation;
	}

	public void setMap(Map m) {
		this.map = m;
	}

	public void render(Screen screen) {
		// screen.drawSprite(Sprite.test, 0, screen.renderHeight,
		// screen.WIDTH,screen.HEIGHT-screen.renderHeight);
	}
}
