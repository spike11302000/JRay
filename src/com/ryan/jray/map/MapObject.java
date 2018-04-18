package com.ryan.jray.map;

public class MapObject {
	public MapObjectType type;
	public int color;
	public boolean visible; 
	public int textureID;
	public MapObject() {
		this.color = 0xF0F0F0;
		this.type = MapObjectType.COLOR;
		this.visible = true;
	}
	public MapObject(MapObjectType type,int color) {
		this.color = color;
		this.textureID = color;
		this.type = type;
		this.visible = true;
	}
	public MapObject(MapObjectType type,int color,boolean visible) {
		this.color = color;
		this.textureID = color;
		this.type = type;
		this.visible = visible;
	}
	
	/*public MapObject(Vector2 imgVec) {
		this.color = 0x0000;
		this.type = MapObjectType.TEXTURE;
		this.visible = true;
		this.imageVec = imgVec;
	}
	public MapObject setImageVector(Vector2 imgVec){
		this.imageVec = imgVec;
		return this;
	}*/
}
