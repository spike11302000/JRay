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
		if ((this.key.up && this.key.left) || (this.key.up && this.key.right) || (this.key.down && this.key.left) || (this.key.down && this.key.right))
			moveVel.div(new Vector2(2, 2));

		if (this.key.up)
			this.position.add(moveVel);

		if (this.key.down)
			this.position.sub(moveVel);

		if (this.key.left) {
			this.position.x += moveVel.y;
			this.position.y -= moveVel.x;
		}
		if (this.key.right) {
			this.position.x -= moveVel.y;
			this.position.y += moveVel.x;
		}
		
		if (this.key.keys[32]&& tick%5==0)
			map.entities.add(new Bullet(new Vector2(this.position.x+vel.x,this.position.y+vel.y),bulletVel));
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
