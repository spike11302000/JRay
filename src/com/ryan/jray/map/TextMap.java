package com.ryan.jray.map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextMap extends Map {
	private Object[] lines;
	private int[] tileDef;
	private int tileDefEnd;
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
		this.tileDefEnd = 0;
		for(int i=0;i<this.lines.length;i++) {
			if(((String)this.lines[i]).equals("--")) {
				tileDefEnd = i;
				break;
			}
		}
		this.tileDef = new int[256];
		for(int i=1;i<this.tileDefEnd;i++) {
			String l = (String) this.lines[i];
			//tileDef[Integer.parseInt(l.substring(0, l.indexOf('-')))] = l.charAt(l.indexOf('-')+1);
			tileDef[l.charAt(l.indexOf('-')+1)] = Integer.parseInt(l.substring(0, l.indexOf('-')));
		}
		
		generateMap();
	}

	private void generateMap() {
		for (int x = 0; x < this.WIDTH; x++) {
			for (int y = 0; y < this.HEIGHT; y++) {
				char i = ((String) lines[y+this.tileDefEnd+1]).charAt(x);
				//System.out.println(i);
				if (i == ' ') {
					this.map[y * (this.WIDTH) + x] = null;
				} else {
					this.map[y * (this.WIDTH) + x] = new MapObject(MapObjectType.TEXTURE,this.tileDef[i]);
				}
			}
		}
		//System.out.println(this.HEIGHT);
	}
}