package org.uqbar.asteroids.utils;

import com.uqbar.vainilla.appearances.Sprite;

public class AsteroidsSpriteManager {
	
	public static Sprite getSprite(int scale) {
		return getImage().crop(0, 0, ResourceUtil.getResourceInt("Asteroid.sprite.height"), ResourceUtil.getResourceInt("Asteroid.SPRITE_WIDTH")).scale(scale);
	}

	public static Sprite getSprite() {
		return getSprite(1);
	}
	
	private static Sprite getImage() {
		return Sprite.fromImage(ResourceUtil.getResourceString("Asteroid.SPRITE_FILE"));
	}
	
	
}
