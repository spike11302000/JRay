package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;

public class Bullet extends Entity {
	public Vector2 velocity;
	public int lifeTime = 120;
	public int textureID = 1;
	public Bullet(Vector2 pos,Vector2 vel){
		super(pos,new Vector2(.1,.1));
		this.velocity = vel;
	}
	int tick = 0;
	public void update(){
		tick++;
		if(tick>=lifeTime)
			this.destroy();
		this.position.add(this.velocity);
	}
}
