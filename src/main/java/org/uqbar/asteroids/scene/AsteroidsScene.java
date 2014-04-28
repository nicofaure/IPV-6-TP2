package org.uqbar.asteroids.scene;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.asteroids.components.Asteroid;

import com.uqbar.vainilla.GameScene;

public class AsteroidsScene extends GameScene {

	private final List<Asteroid> asteroids = new ArrayList<Asteroid>();

	public List<Asteroid> getAsteroids() {
		return this.asteroids;
	}
}
