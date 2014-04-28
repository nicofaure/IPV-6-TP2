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
	
	public void addAsteroid(Asteroid asteroid) {
		this.asteroids.add(asteroid);
		this.addComponent(asteroid);
	}
	
	@Override
	public void initializeComponents() {
		for (int i = 0; i < 10; i++) {
			this.addAsteroid(new Asteroid());
		}
	}
}
