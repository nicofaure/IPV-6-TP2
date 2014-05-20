package org.uqbar.asteroids.components;


import org.uqbar.asteroids.scene.levels.Level1;
import org.uqbar.asteroids.utils.BulletPoolSingleton;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.colissions.CollisionDetector;

public class Bullet extends MovableComponent<Level1> {

	private static final int BULLET_SPEED = ResourceUtil.getResourceInt("Bullet.BULLET_SPEED");
	private static final String BULLET_SPRITE = ResourceUtil.getResourceString("Bullet.BULLET_SPRITE");
	private int radius = ResourceUtil.getResourceInt("Bullet.radius");

	public Bullet(){
		super();
		this.setAppearance(getDefaultAppearance());
		this.setSpeed(BULLET_SPEED);
	}
	
	public Bullet(double vx, double vy, double x, double y, double shipSpeed) {
//		super(getDefaultAppearance(),x,y);
		this.setSpeed(BULLET_SPEED + shipSpeed );
		this.setX(x);
		this.setY(y);
	}

	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage(BULLET_SPRITE);
	}

	@Override
	public void update(DeltaState deltaState) {
		
		if(this.getGame()!=null)
		{
			if(this.getScene().getAsteroids().size()>0)
			{
				if (this.isOutOfSpace()) {
					BulletPoolSingleton.getInstance().returnBullet(this);			
				} else {
					double advance = (this.getSpeed() + BULLET_SPEED) * deltaState.getDelta();
					this.move(advance * this.getVector().getX(), advance
							* this.getVector().getY());
					for (Asteroid asteroid : this.getScene().getAsteroids()) {
						if (this.impactAsteroid(asteroid)) {
							asteroid.hit();
							BulletPoolSingleton.getInstance().returnBullet(this);
							break;
						}
					}
				}
			}else{
				this.getScene().win();
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
	
	public void reset(){
		this.setDestroyPending(false);
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
