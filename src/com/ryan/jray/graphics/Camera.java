package com.ryan.jray.graphics;

import com.ryan.jray.map.MapObjectType;
import com.ryan.jray.utils.Vector2;

public class Camera {
	public Vector2 position;
	public double rotation;
	public double FOV = 45;
	public RayCaster rayCaster = new RayCaster(new Vector2(), 50d, .01);

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
		rayCaster.setPos(this.position);
	}

	public void render(Screen screen) {
		for (double x = 0; x < screen.WIDTH; x++) {
			double rot = ((x / screen.WIDTH) * FOV) - FOV / 2;
			RayObject ro = rayCaster.test(rot + this.rotation);
			double z = ro.distance * Math.cos(Math.toRadians(rot));
			int height = (int) (screen.ASPECT * (screen.HEIGHT / z));
			if (ro.mapObject.type == MapObjectType.COLOR)
				screen.drawColum(ro.mapObject.color, (int) Math.floor(x), height);
			if (ro.mapObject.type == MapObjectType.TEXTURE) {
				if (ro.textureVector.y <= .05 && ro.textureVector.y <= .95)
					screen.drawColumSprite(Sprite.iron, (int) x, ro.textureVector.x, height);
				if (ro.textureVector.x <= .05 && ro.textureVector.x <= .95)
					screen.drawColumSprite(Sprite.iron, (int) x, ro.textureVector.y, height);
				if (ro.textureVector.y >= .95 && ro.textureVector.x >= .05)
					screen.drawColumSprite(Sprite.iron, (int) x, ro.textureVector.x, height);
				if (ro.textureVector.x >= .95 && ro.textureVector.y >= .05)
					screen.drawColumSprite(Sprite.iron, (int) x, ro.textureVector.y, height);

			}
		}
	}
}
