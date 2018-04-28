package com.ryan.jray.entity;

import com.ryan.jray.utils.Vector2;

public class MPlayer extends Entity{
	public double rotation;
	public MPlayer(Vector2 pos, double rot) {
		super(pos);
		this.rotation = rot;
		this.size = new Vector2(1,1);
		this.textureID = 0;
	}
	public void update() {
		
	}

}
