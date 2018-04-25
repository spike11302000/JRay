package com.ryan.jray.map;

import java.util.ArrayList;
import java.util.Random;

import com.ryan.jray.entity.AnimatedEntity;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.utils.Vector2;

public class Map {
	public int WIDTH;
	public int HEIGHT;
	public MapObject[] map;
	public MapObject wall = new MapObject(MapObjectType.TEXTURE, 1);
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Light> lights = new ArrayList<Light>();

	public Map() {
		this.WIDTH = 0;
		this.HEIGHT = 0;
		this.map = new MapObject[0];
		this.generateMap();
	}

	public Map(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.map = new MapObject[width * height];
		this.generateMap();
	}

	private void generateMap() {

		// Entity ent = new Entity(new Vector2(5,5));
		// ent.setSprite(new Random().nextInt(3));
		// Entity ent = new AnimatedEntity(new Vector2(5,5),new Vector2(1,1));
		// this.entities.add(ent);
	}

	public void update() {
		for (Entity ent : this.entities) {
			ent.update();
		}
		for (Entity ent : this.entities)
			if (ent.isDestroyed()) {
				this.entities.remove(ent);
				break;
			}
	}

	public MapObject checkPoint(Vector2 pos) {

		if (pos.x > 0 && pos.y > 0 && pos.x < this.WIDTH && pos.y < this.HEIGHT) {
			int index = (int) Math.floor(pos.x) + ((int) Math.floor(pos.y) * this.WIDTH);
			MapObject mo = map[index];
			return mo;
		}
		return null;
	}

	public void swap(int i, int j) {
		Entity tmp = this.entities.get(i);
		this.entities.set(i, this.entities.get(j));
		this.entities.set(j, tmp);
	}

	public void sortEntities(Vector2 pos) {
		int min;
		for (int i = 0; i < this.entities.size(); ++i) {
			min = i;
			for (int j = i + 1; j < this.entities.size(); ++j)
				if (this.entities.get(j).position.distance(pos) > this.entities.get(min).position.distance(pos))
					min = j;

			swap(i, min);
		}
	}
}
