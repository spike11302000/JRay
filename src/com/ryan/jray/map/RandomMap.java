package com.ryan.jray.map;

import java.util.Random;

public class RandomMap extends Map {
	private Random rand = new Random();

	public RandomMap(int width, int height) {
		super(width, height);
		generateMap();
	}

	public void generateMap() {
		for (int i = 0; i < map.length; i++) {
			map[i] = new MapObject(rand.nextInt(0xFFFFFF));
			if (rand.nextInt(100) < 10)
				map[i].visible = false;
			else
				map[i].visible = false;
		}
	}
}
