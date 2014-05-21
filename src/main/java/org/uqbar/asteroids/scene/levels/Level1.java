package org.uqbar.asteroids.scene.levels;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.asteroids.components.Asteroid;
import org.uqbar.asteroids.components.Ship;
import org.uqbar.asteroids.scene.GameOverScene;
import org.uqbar.asteroids.scene.WinScene;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.components.PointsCounter;

public class Level1 extends GameScene {

	private static String BACKGROUND_IMAGE = ResourceUtil.getResourceString("Level1.BACKGROUND_IMAGE");
	
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
		GameComponent<GameScene> background = new GameComponent<GameScene>(getDefaultAppearance(),0 ,0);
		this.addComponent(background);
	}
	
	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage(BACKGROUND_IMAGE);
	}
	
	@Override
	public void initializeComponents() {
		this.initializeBackground();
		this.addComponent(this.getShip());
		
		for (int i = 0; i < ResourceUtil.getResourceInt("Asteroids.level1.asteroidCount"); i++) {
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
	
	public void win(){
		this.getShip().destroy();
		for(int i=0; i<this.getAsteroids().size();i++){
			Asteroid asteroid = this.getAsteroids().get(i);
			asteroid.destroy();
		}
		this.getGame().setCurrentScene(new WinScene(new PointsCounter()));
	}
	
	public void loseLife() {
			this.getShip().destroy();
			for(int i=0; i<this.getAsteroids().size();i++){
				Asteroid asteroid = this.getAsteroids().get(i);
				asteroid.destroy();
			}
			this.getGame().setCurrentScene(new GameOverScene(new PointsCounter()));
	}
	
	public int getWidth() {
		return this.getGame().getDisplayWidth();
	}

	public int getHeight() {
		return this.getGame().getDisplayHeight();
	}
}
