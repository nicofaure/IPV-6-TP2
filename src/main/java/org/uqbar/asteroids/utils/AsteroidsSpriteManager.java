package org.uqbar.asteroids.utils;

import com.uqbar.vainilla.appearances.Sprite;

public class AsteroidsSpriteManager {
	
	public static Sprite getSprite(int scale) {
		return getImage().crop(0, 0, ResourceUtil.getResourceInt("Asteroid.sprite.height"), ResourceUtil.getResourceInt("Asteroid.sprite.width")).scale(scale);
	}

	public static Sprite getSprite() {
		return getSprite(1);
	}
	
	private static Sprite getImage() {
		return Sprite.fromImage(ResourceUtil.getResourceString("Asteroid.sprite.file"));
	}
	
	
}
