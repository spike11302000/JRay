package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;

public class Entity {
	public Vector2 position;
	public Vector2 size;
	public int textureID;
	private boolean destroyed = false;
	public Entity() {
		this.position = new Vector2();
	}

	public Entity(Vector2 vec) {
		this.position = vec;
		this.size = new Vector2(1, 1);
	}

	public Entity(Vector2 vec, Vector2 size) {
		this.position = vec;
		this.size = size;

	}
	public void collide() {
	
	}
	public void setSprite(int texID) {
		this.textureID = texID;
	}

	public void update() {

	}

	public void destroy(){
		this.destroyed = true;
	}
	public boolean isDestroyed(){
		return this.destroyed;
	}

}
