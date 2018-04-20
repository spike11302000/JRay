package com.ryan.jray.entity;

import com.ryan.jray.map.MapObject;
import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.utils.Vector2;

public class Entity {
	public Vector2 position;
	public Vector2 size;

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

	public MapObject checkPoint(Vector2 pos) {
		if (this.position.x - (this.size.x / 2) < pos.x && this.position.y - (this.size.y / 2) < pos.y
				&& (this.position.x +this.size.x)- (this.size.x / 2) > pos.x && (this.position.y+ this.size.y)- (this.size.y / 2) > pos.y)
			return new MapObject(MapObjectType.TEXTURE, 1).setEntity(this);

		return null;
	}

	public void update() {

	}

}
