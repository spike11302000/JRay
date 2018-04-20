package com.ryan.jray.map;

import java.util.Random;

import com.ryan.jray.entity.Entity;
import com.ryan.jray.utils.Vector2;

public class RandomMap extends Map {
	private Random rand = new Random();

	public RandomMap(int width, int height) {
		super(width, height);
		generateMap();
	}

	public void generateMap() {
		
		for (int i = 0; i < map.length; i++) {
			if (rand.nextInt(100) < 10) {
				map[i] = new MapObject(MapObjectType.TEXTURE,rand.nextInt(3));
			} else
				map[i] = null;
		}
	}
}
