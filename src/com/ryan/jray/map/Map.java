package com.ryan.jray.map;

import com.ryan.jray.utils.Color;
import com.ryan.jray.utils.Vector2;

public class Map {
	public int WIDTH;
	public int HEIGHT;
	public MapObject[] map;
	public MapObject wall = new MapObject(0xFF00ff);

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
			int index = (int)Math.floor(pos.x) + ((int)Math.floor(pos.y) * this.WIDTH);
			return map[index];
		}
		return new MapObject(new Color((int) ((pos.x-Math.floor(pos.x))*255),0,(int) ((pos.y-Math.floor(pos.y))*255) ).toInt());
	}

}