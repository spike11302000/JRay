package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;

import java.io.IOException;

import com.ryan.jray.controls.Keyboard;
import com.ryan.jray.graphics.Camera;
import com.ryan.jray.graphics.Screen;
import com.ryan.jray.graphics.Sprite;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObject;
import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.network.Client;
import com.ryan.jray.network.packet.PacketEntity;

public class Player extends Entity {
	public double rotation;
	private Keyboard key;
	private Camera cam;
	public double speed;
	public Map map;
	public Client client;
	public String username;
	public boolean isAlive = true;

	public Player(Vector2 pos, double rot) {
		this.position = pos;
		this.rotation = rot;
	}

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
		MapObject mo = map.checkPoint(this.position);
		Vector2 moveScale = new Vector2(1,1);
		if(mo !=null && mo.type == MapObjectType.ABERRATION) {
			moveScale = mo.aberrationScale.clone();
			moveScale.x = 1/moveScale.x;
			moveScale.y = 1/moveScale.y;
		}
		Vector2 bulletVel = new Vector2(vel.x * .2, vel.y * .2);
		if ((this.key.up && this.key.left) || (this.key.up && this.key.right) || (this.key.down && this.key.left)
				|| (this.key.down && this.key.right))
			moveVel.div(new Vector2(2, 2));

		if (this.key.up && isAlive) {
			if (!map.checkCollion(new Vector2(this.position.x + (moveVel.x * 4), this.position.y)))
				this.position.x += moveVel.x*moveScale.x;
			if (!map.checkCollion(new Vector2(this.position.x, this.position.y + (moveVel.y * 4))))
				this.position.y += moveVel.y*moveScale.y;
		}

		if (this.key.down && isAlive) {
			if (!map.checkCollion(new Vector2(this.position.x - (moveVel.x * 4), this.position.y)))
				this.position.x -= moveVel.x*moveScale.x;
			if (!map.checkCollion(new Vector2(this.position.x, this.position.y - (moveVel.y * 4))))
				this.position.y -= moveVel.y*moveScale.y;
		}
		if (this.key.left && isAlive) {
			if (!map.checkCollion(new Vector2(this.position.x + (moveVel.y * 4), this.position.y)))
				this.position.x += moveVel.y*moveScale.x;
			if (!map.checkCollion(new Vector2(this.position.x, this.position.y - (moveVel.x * 4))))
				this.position.y -= moveVel.x*moveScale.y;
		}
		if (this.key.right && isAlive) {
			if (!map.checkCollion(new Vector2(this.position.x - (moveVel.y * 4), this.position.y)))
				this.position.x -= moveVel.y*moveScale.x;
			if (!map.checkCollion(new Vector2(this.position.x, this.position.y + (moveVel.x * 4))))
				this.position.y += moveVel.x*moveScale.y;
		}

		if (this.key.keys[32] && tick % 6 == 0 && this.isAlive) {
			Entity e = new Bullet(new Vector2(this.position.x + vel.x, this.position.y + vel.y), bulletVel);

			map.entities.add(e);
			if (this.client != null) {
				try {
					e.Owner = this.client.player.Owner;
					this.client.send(new PacketEntity(e, this.username));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {

			}
		}
		this.cam.position = this.position;
		this.cam.rotation = this.rotation;
	}

	public void setMap(Map m) {
		this.map = m;
	}

	public MPlayer getMPlayer() {
		MPlayer mp = new MPlayer(this.position, this.rotation);
		mp.ID = this.ID;
		mp.Owner = this.Owner;
		return mp;
	}

	public void render(Screen screen) {
		// screen.drawSprite(Sprite.test, 0, screen.renderHeight,
		// screen.WIDTH,screen.HEIGHT-screen.renderHeight);
	}
}
