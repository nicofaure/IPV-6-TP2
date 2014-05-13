package org.uqbar.asteroids.components;

import java.awt.Color;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Circle;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.utils.Vector2D;

public class Bullet extends MovableComponent<AsteroidsScene> {

	private static final int BULLET_SPEED = 500;
	private int radius = 2;

	public Bullet(double vx, double vy, double x, double y, double shipSpeed) {
		this.setAppearance(this.getDefaultAppearance());
		this.setVector(new Vector2D(vx, vy));
		this.setSpeed(BULLET_SPEED + shipSpeed );
		this.setX(x);
		this.setY(y);
	}

	private Circle getDefaultAppearance() {
		return new Circle(Color.GREEN, 2 * this.radius);
	}

	@Override
	public void update(DeltaState deltaState) {
		if (this.isOutOfSpace()) {
			this.destroy();
		} else {
			double advance = this.getSpeed() * deltaState.getDelta();
			this.move(advance * this.getVector().getX(), advance
					* this.getVector().getY());
			for (Asteroid asteroid : this.getScene().getAsteroids()) {
				if (this.impactAsteroid(asteroid)) {
					asteroid.hit();
					this.destroy();
					break;
				}
			}
		}
	}

	private boolean impactAsteroid(Asteroid asteroid) {
		return CollisionDetector.INSTANCE.collidesCircleAgainstRect(this
				.getX(), this.getY(), this.getRadius(),
				asteroid.getX(), asteroid.getY(), asteroid
						.getAppearance().getWidth(), asteroid
						.getAppearance().getHeight());
	}

	private boolean isOutOfSpace() {
		return !CollisionDetector.INSTANCE.collidesCircleAgainstRect(this
				.getX(), this.getY(), this.getRadius(), 0, 0, this.getGame()
				.getDisplayWidth(), this.getGame().getDisplayHeight());
	}

	public double obtainAbsoluteX() {
		return this.getX() + this.radius * 2;
	}

	public double obtainAbsoluteY() {
		return this.getY() + this.radius * 2;
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
