package com.ryan.jray.map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextMap extends Map {
	private Object[] lines;

	public TextMap(String path) {
		InputStream in = TextMap.class.getResourceAsStream("/" + path);
		BufferedReader input = new BufferedReader(new InputStreamReader(in));
		this.lines = input.lines().toArray();
		String size = (String) lines[0];
		size = size.toLowerCase();
		String w = size.substring(0, size.indexOf("x"));
		String h = size.substring(size.indexOf("x") + 1, size.length());
		System.out.println("Loading map: " + path + " with a size of: " + w + "x" + h);
		this.WIDTH = Integer.parseInt(w);
		this.HEIGHT = Integer.parseInt(h);
		this.map = new MapObject[this.WIDTH * this.HEIGHT];
		generateMap();
	}

	private void generateMap() {
		for (int x = 0; x < this.WIDTH; x++) {
			for (int y = 0; y < this.HEIGHT; y++) {
				char i = ((String) lines[y+1]).charAt(x);
				//System.out.println(i);
				if (i == ' ') {
					this.map[y * (this.WIDTH) + x] = null;
				} else {
					this.map[y * (this.WIDTH) + x] = new MapObject(MapObjectType.TEXTURE,
							Integer.parseInt(Character.toString(i)));
				}
			}
		}
		//System.out.println(this.HEIGHT);
	}
}
