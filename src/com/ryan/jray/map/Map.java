package com.ryan.jray.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.ryan.jray.entity.AnimatedEntity;
import com.ryan.jray.entity.Bullet;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.utils.Vector2;

public class Map {
	public int WIDTH;
	public int HEIGHT;
	public MapObject[] map;
	public MapObject wall = new MapObject(MapObjectType.TEXTURE, 1);
	public Random random = new Random();
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Light> lights = new ArrayList<Light>();
	public ArrayList<Integer> removedEntities = new ArrayList<Integer>();

	public ArrayList<Vector2> blueSpawnPoints = new ArrayList<Vector2>();
	public ArrayList<Vector2> redSpawnPoints = new ArrayList<Vector2>();

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
		for (Light l : this.lights)
			l.update();

		for (int i = 0; i < this.entities.size(); i++) {
			Entity ent = this.entities.get(i);
			if (ent != null) {
				ent.update();
				if (this.checkPoint(ent.position) != null)
					ent.collide();
			}
		}

		for (int i = 0; i < this.entities.size(); i++) {
			Entity ent = this.entities.get(i);
			if (ent != null)
				break;
			if (ent.isDestroyed()) {
				this.removedEntities.add(ent.ID);
				this.entities.remove(ent);
				break;
			}
		}
	}

	public Vector2 getSpawnPoint() {
		ArrayList<Vector2> tmp = new ArrayList<Vector2>();
		tmp.addAll(this.redSpawnPoints);
		tmp.addAll(this.blueSpawnPoints);
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);
		Collections.shuffle(tmp);// just to make sure its well shuffled
		return tmp.get(random.nextInt(tmp.size()));
	}

	public Vector2 getSpawnPoint(boolean team) {// blue: false, red: true
		if (team)
			return this.redSpawnPoints.get(random.nextInt(this.redSpawnPoints.size()));
		else
			return this.blueSpawnPoints.get(random.nextInt(this.blueSpawnPoints.size()));
	}

	public MapObject checkPoint(Vector2 pos) {

		if (pos.x > 0 && pos.y > 0 && pos.x < this.WIDTH && pos.y < this.HEIGHT) {

			int index = (int) Math.floor(pos.x) + ((int) Math.floor(pos.y) * this.WIDTH);
			MapObject mo = map[index];
			return mo;

		}
		return null;
	}

	public boolean checkCollion(Vector2 pos) {

		if (pos.x > 0 && pos.y > 0 && pos.x < this.WIDTH && pos.y < this.HEIGHT) {

			int index = (int) Math.floor(pos.x) + ((int) Math.floor(pos.y) * this.WIDTH);
			MapObject mo = map[index];
			if (mo == null)
				return false;
			if (mo.type == MapObjectType.ABERRATION)
				return false;
			return true;

		}
		return false;
	}

	public void removeEntity(int id) {
		for (int i = 0; i < this.entities.size(); i++)
			if (this.entities.get(i).ID == id) {
				this.removedEntities.add(this.entities.get(i).ID);
				this.entities.remove(this.entities.get(i));
			}
	}

	public int EntityIndex(int id) {
		for (int i = 0; i < this.entities.size(); i++) {
			if (this.entities.get(i) != null)
				if (id == this.entities.get(i).ID)
					return i;
		}
		return -1;
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
			for (int j = i + 1; j < this.entities.size(); ++j) {
				if (this.entities.get(j) == null || this.entities.get(min) == null)
					return;
				if (this.entities.get(j).position.distance(pos) > this.entities.get(min).position.distance(pos))
					min = j;
			}
			swap(i, min);
		}
	}
}
