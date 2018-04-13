package com.ryan.jray.map;

import com.ryan.jray.utils.Vector2;

public class Map {
	public int WIDTH;
	public int HEIGHT;
	public MapObject[] map;
	public MapObject wall = new MapObject();
	public Map(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.map = new MapObject[width * height];
		this.generateMap();
	}
	private void generateMap() {
		
	}
	public MapObject checkPoint(Vector2 pos) {
		if (pos.x > 0 && pos.y > 0 && pos.x < this.WIDTH && pos.y < this.HEIGHT) {
			int xx = (int) Math.floor(pos.x);
			int yy = (int) Math.floor(pos.y);
			int index = xx + (yy * this.WIDTH);
			return map[index];
		}
		return wall;
	}
	
}
