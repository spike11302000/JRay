package com.ryan.jray.map;

import java.util.ArrayList;

import com.ryan.jray.entity.Entity;
import com.ryan.jray.utils.Vector2;

public class Map {
	public int WIDTH;
	public int HEIGHT;
	public MapObject[] map;
	public MapObject wall = new MapObject(MapObjectType.TEXTURE,0);
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Map(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.map = new MapObject[width * height];
		this.generateMap();
	}

	private void generateMap() {
		
		this.entities.add(new Entity(new Vector2(5,5),new Vector2(1,1)));
	}
	public void update() {
		for(Entity ent : this.entities)
			ent.update();
	}
	public MapObject checkPoint(Vector2 pos) {
		for(Entity ent : this.entities) {
			MapObject mo = ent.checkPoint(pos);
			if(mo!=null)
				return mo;
		}
		if (pos.x > 0 && pos.y > 0 && pos.x < this.WIDTH && pos.y < this.HEIGHT) {
			int index = (int) Math.floor(pos.x) + ((int) Math.floor(pos.y) * this.WIDTH);
			MapObject mo = map[index];
			return mo;
		}
		return wall;
	}

}
