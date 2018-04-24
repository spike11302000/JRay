package com.ryan.jray.graphics;

import com.ryan.jray.entity.AnimatedEntity;
import com.ryan.jray.entity.Entity;
import com.ryan.jray.map.Map;
import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.utils.MathUtils;
import com.ryan.jray.utils.Vector2;

public class Camera {
	public Vector2 position;
	public double rotation;
	public double FOV = 50;
	public RayCaster rayCaster = new RayCaster(new Vector2(), 50d, .01);
	private Map map;

	public Camera() {
		this.position = new Vector2();
		this.rotation = 0;

	}

	public Camera(Vector2 pos, double rot) {
		this.position = pos;
		this.rotation = rot;

	}

	public void setPos(Vector2 pos) {
		this.position = pos;
	}

	public void serRotation(double rot) {
		this.rotation = rot;
	}

	public void update() {
		this.rotation = this.rotation % 360;
		if (this.rotation < 0)
			this.rotation += 360;
		// System.out.println(this.rotation);
		rayCaster.setPos(this.position);
		this.map.sortEntities(this.position);
	}

	public void render(Screen screen) {
		double[] zBuffer = new double[screen.WIDTH];
		for (double x = 0; x < screen.WIDTH; x++) {
			double rot = ((x / screen.WIDTH) * FOV) - FOV / 2;
			RayObject ro = rayCaster.test(rot + this.rotation);
			double z = ro.distance * Math.cos(Math.toRadians((rot)));
			int height = (int) (screen.ASPECT * (screen.HEIGHT / z));
			zBuffer[(int) x] = ro.distance;
			if (ro.mapObject.type == MapObjectType.COLOR)
				screen.drawColum(ro.mapObject.color, (int) Math.floor(x), height);
			if (ro.mapObject.type == MapObjectType.TEXTURE) {

				Sprite sprite = Sprite.getSprite(ro.mapObject.textureID);
				double avg = (ro.textureVector.x + ro.textureVector.y) / 2.0;
				if (avg>.5)
					screen.drawColumSprite(sprite, (int) x, (avg * 2)-1, height);
				else
					screen.drawColumSprite(sprite, (int) x, (avg * 2), height);
			}

		}
		for (int i = 0; i < this.map.entities.size(); i++) {
			Entity ent = this.map.entities.get(i);
			double dist = this.position.distance(ent.position);
			Vector2 relative = new Vector2(ent.position.x - this.position.x, ent.position.y - this.position.y);
			double a = this.rotation - MathUtils.getAngle(relative) - 90;
			a %= 360;
			if (a > 180)
				a -= 360;
			if (a < -180)
				a += 360;

			int x = (int) MathUtils.map(-a, (-FOV / 2), (FOV / 2), 0, screen.WIDTH);
			int height = (int) (screen.ASPECT * (screen.HEIGHT / dist));
			int width = (int) (height * ent.size.x);
			height = (int) (height * ent.size.y);

			for (int xx = -width / 2; xx < width / 2; xx++) {
				if (x + xx > 0 && x + xx < screen.WIDTH && zBuffer[x + xx] > dist) {
					if (ent instanceof AnimatedEntity)
						screen.drawColumSprite(Sprite.getSprite(((AnimatedEntity) ent).animatedSprite.getTextureID()),
								x + xx, MathUtils.map(xx, -width / 2, width / 2, 0, 1d), height);
					else
						screen.drawColumSprite(Sprite.getSprite(ent.textureID), x + xx,
								MathUtils.map(xx, -width / 2, width / 2, 0, 1d), height);
				}
			}

		}
	}

	public void setMap(Map map) {
		this.map = map;
		this.rayCaster.setMap(map);
	}
}
