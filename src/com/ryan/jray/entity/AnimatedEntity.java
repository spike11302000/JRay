package com.ryan.jray.entity;

import com.ryan.jray.graphics.AnimatedSprite;
import com.ryan.jray.graphics.Sprite;
import com.ryan.jray.utils.Vector2;

public class AnimatedEntity extends Entity {
	public AnimatedSprite animatedSprite;
	
	public AnimatedEntity() {
		super();
		int[] i = {0,1};
		this.animatedSprite = new AnimatedSprite(i,60);
	}

	public AnimatedEntity(Vector2 vec) {
		super(vec);
		int[] i = {0,1};
		this.animatedSprite = new AnimatedSprite(i,60);
	}

	public AnimatedEntity(Vector2 vec, Vector2 size) {
		super(vec,size);
		int[] i = {0,1};
		this.animatedSprite = new AnimatedSprite(i,60);
	}
	public void update() {
		this.animatedSprite.update();
	}
}
