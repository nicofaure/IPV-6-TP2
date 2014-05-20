package org.uqbar.asteroids.scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.uqbar.asteroids.components.Asteroid;
import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Rectangle;
import com.uqbar.vainilla.components.PointsCounter;

public class AsteroidsScene extends GameScene {

	private final List<Asteroid> asteroids = new ArrayList<Asteroid>();
	private Ship ship = new Ship();

	public List<Asteroid> getAsteroids() {
		return this.asteroids;
	}
	
	public void addAsteroid(Asteroid asteroid) {
		this.asteroids.add(asteroid);
		this.addComponent(asteroid);
	}
	
	private void initializeBackground() {
		GameComponent<GameScene> background = new GameComponent<GameScene>(new Rectangle(Color.BLACK, this.getGame().getDisplayWidth(), this.getGame().getDisplayHeight()),0 ,0);
		this.addComponent(background);
	}
	
	@Override
	public void initializeComponents() {
		this.initializeBackground();
		this.addComponent(this.getShip());
		for (int i = 0; i < 10; i++) {
			Asteroid asteroid = new Asteroid(3);
			this.addAsteroid(asteroid);
		}
	}

	public void removeAsteroid(Asteroid asteroid) {
		this.asteroids.remove(asteroid);
		asteroid.destroy();
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		this.addComponent(ship);
	}

	public void addPoint() {
		// TODO Auto-generated method stub
	}

	public void loseLife() {
			this.getShip().destroy();
			this.getGame().setCurrentScene(new GameOverScene(new PointsCounter()));
	}
	
	public int getWidth() {
		return this.getGame().getDisplayWidth();
	}

	public int getHeight() {
		return this.getGame().getDisplayHeight();
	}
}
