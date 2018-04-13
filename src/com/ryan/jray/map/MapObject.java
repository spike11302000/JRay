package com.ryan.jray.map;

public class MapObject {
	private MapObjectType type;
	public int color;
	public boolean visible; 
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
}
