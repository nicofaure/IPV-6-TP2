package org.uqbar.asteroids.components;

import java.awt.Color;
import java.util.Random;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Circle;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.utils.Vector2D;

public class Bullet extends MovableComponent<AsteroidsScene> {

	private int radius = 10;

	public Bullet() {
		this.setAppearance(new Circle(Color.GREEN, 2 * this.radius));
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(120, 300));
		this.setX(0);
		this.setY(0);
	}

	private Vector2D buildVector() {
		return new Vector2D(this.obtainRnd(-100, 100),
				this.obtainRnd(-100, 100)).asVersor();
	}

	private int obtainRnd(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}

	@Override
	public void update(DeltaState deltaState) {
		this.checkRebound();
		double advance = this.getSpeed() * deltaState.getDelta();
		this.move(advance * this.getVector().getX(), advance
				* this.getVector().getY());
		for (Asteroid asteroid : this.getScene().getAsteroids()) {
			if (CollisionDetector.INSTANCE.collidesCircleAgainstRect(this
					.getX(), this.getY(), this.getRadius(), asteroid.getX(),
					asteroid.getY(), asteroid.getAppearance().getWidth(),
					asteroid.getAppearance().getHeight())) {
				this.getScene().addAsteroid(
						new Asteroid(asteroid.getX(), asteroid.getY()));
				this.getScene().addAsteroid(
						new Asteroid(asteroid.getX(), asteroid.getY()));
				this.getScene().addAsteroid(
						new Asteroid(asteroid.getX(), asteroid.getY()));
				this.getScene().removeAsteroid(asteroid);
				this.destroy();
				break;
			}

		}

		super.update(deltaState);
	}

	private void checkRebound() {
		if (this.atBottomBorder()) {
			this.inverseVerticalDirection();
			this.setY(this.getGame().getDisplayHeight() - this.radius * 2);
		} else if (this.atTopBorder()) {
			this.inverseVerticalDirection();
			this.setY(0);
		} else if (this.atLeftBorder()) {
			this.inverseHorizontalDirection();
			this.setX(0);
		} else if (this.atRightBorder()) {
			this.inverseHorizontalDirection();
			this.setX(this.getGame().getDisplayWidth() - this.radius * 2);
		}
	}

	private boolean atBottomBorder() {
		return this.getGame().getDisplayHeight() <= this.obtainAbsoluteY();
	}

	private boolean atTopBorder() {
		return this.getY() <= 0;
	}

	private boolean atRightBorder() {
		return this.getGame().getDisplayWidth() <= this.obtainAbsoluteX();
	}

	public double obtainAbsoluteX() {
		return this.getX() + this.radius * 2;
	}

	private boolean atLeftBorder() {
		return this.getX() <= 0;
	}

	public double obtainAbsoluteY() {
		return this.getY() + this.radius * 2;
	}

	public void goFaster(double x) {
		this.setSpeed(this.getSpeed() + x);
	}

	public int getCenterX() {
		return (int) this.getX() + this.getRadius();
	}

	public int getCenterY() {
		return (int) this.getY() + this.getRadius();
	}

	public void inverseHorizontalDirection() {
		this.getVector().setX(this.getVector().getX() * -1);
	}

	public void inverseVerticalDirection() {
		this.getVector().setY(this.getVector().getY() * -1);
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}
