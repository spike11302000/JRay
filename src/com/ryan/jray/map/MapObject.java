package com.ryan.jray.map;

import com.ryan.jray.utils.Vector2;

public class MapObject {
	public MapObjectType type;
	public int color;
	public boolean visible; 
	public Vector2 imageVec;
	public MapObject() {
		this.color = 0xF0F0F0;
		this.type = MapObjectType.COLOR;
		this.visible = true;
	}
	public MapObject(int color) {
		this.color = color;
		this.type = MapObjectType.COLOR;
		this.visible = true;
	}
	public MapObject(int color,boolean visible) {
		this.color = color;
		this.type = MapObjectType.COLOR;
		this.visible = visible;
	}
	public MapObject(Vector2 imgVec) {
		this.color = 0x0000;
		this.type = MapObjectType.TEXTURE;
		this.visible = true;
		this.imageVec = imgVec;
	}
}
