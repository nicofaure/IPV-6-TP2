package org.uqbar.asteroids.components;


import org.uqbar.asteroids.scene.AsteroidsScene;
import org.uqbar.asteroids.utils.BulletPoolSingleton;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<AsteroidsScene> {

	
	// Configuraciones
	// TODO: Levantarlas de un archivo de propiedades
	private static int SHOOTING_DELAY = 50;
	private static int ROTATION_STEP = 5;
	private Vector2D accelerationVector;
	private int shootingDelay = 0;

	public Ship() {
		super(getDefaultAppearance(), 10, 10);
		this.setVector(new Vector2D(1, 0));
		this.setAccelerationVector(new Vector2D(1, 0));
	}

	@Override
	public void onSceneActivated() {
		this.alignHorizontalCenterTo(this.getGame().getDisplayWidth() / 2);
		this.alignVerticalCenterTo(this.getGame().getDisplayHeight() / 2);
		super.onSceneActivated();
	}

	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage("images/rocket2.png").scale(0.4).rotate(Math.PI / 2);
	}

	@Override
	public void update(DeltaState deltaState) {

		this.decreaseShootDelay(deltaState);
		
		// Chequeo del control del giro de la nave.
		if (deltaState.isKeyBeingHold(Key.RIGHT)) {
			this.rotateRight(deltaState.getDelta());
		} else if (deltaState.isKeyBeingHold(Key.LEFT)) {
			this.rotateLeft(deltaState.getDelta());
		}

		// Chequeo del control del "propulsor" de la nave.
		if (deltaState.isKeyBeingHold(Key.UP)) {
			this.actAcceleration(deltaState);
			//actualizar velocidad
			this.actSpeed(deltaState);
		} else {
			this.decreaseAcceleration();
		}

		if (deltaState.isKeyPressed(Key.SPACE)) {
			this.shoot();
		}

		this.move(deltaState);
		this.checkAsteroidCollision();
		// Chequea y se teletransporta si es necesario.
		// TODO: No esta bueno, deberia estar en otra parte!
		this.doTeleport();
	}

	private void checkAsteroidCollision() {
		for (Asteroid asteroid : this.getScene().getAsteroids()) {
			if (this.impactAsteroid(asteroid)) {
				this.loseLife();
				break;
			}
		}
	}

	private void loseLife() {
		this.getScene().loseLife();
		this.destroy();
	}

	private boolean impactAsteroid(Asteroid asteroid) {
		return CollisionDetector.INSTANCE.collidesRectAgainstRect(this.getX(), this.getY(), this.getAppearance().getWidth(), this.getAppearance().getHeight(), asteroid.getX(), asteroid.getY(), asteroid.getAppearance().getWidth(), asteroid.getAppearance().getHeight());	
	}

	@Override
	public void decreaseAcceleration() {
		this.setVector(this.getVector().producto(0.999));
	}
	
	private void actAcceleration(DeltaState deltaState) {
		// TODO Auto-generated method stub
		double deltaAcceleration = 0.5;
		double deltaX = 0;
		double deltaY = 0;
		
		if(this.getAccelerationVector().getX()>0) {
			deltaX = deltaAcceleration; 
		}
		else {
			deltaX = -deltaAcceleration;
		}
		
		if(this.getAccelerationVector().getY()>0) {
			deltaY = deltaAcceleration; 
		}
		else {
			deltaY = -deltaAcceleration;
		}
		this.setAccelerationVector(this.getAccelerationVector().suma(new Vector2D(deltaX, deltaY)));
	}

	// ---
	// Movement methods
	// ---
	private void actSpeed(DeltaState deltaState) {
		Vector2D deltaVelocidad = this.getAccelerationVector().producto(deltaState.getDelta());
		this.setVector(this.getVector().suma(deltaVelocidad));
	}

	private void move(DeltaState deltaState) {
		Vector2D positionUpdate = this.getPosition().suma(this.getVector().producto(deltaState.getDelta()));
		this.setX(positionUpdate.getX());
		this.setY(positionUpdate.getY());
	}

	private Vector2D getPosition() {
		return new Vector2D(this.getX(), this.getY());
	}

	private void doTeleport() {
		if (this.atBottomBorder()) {
			this.alignBottomTo(1);
		} else if (this.atTopBorder()) {
			this.alignTopTo(this.getGame().getDisplayHeight() - 1);
		} else if (this.atLeftBorder()) {
			this.alignLeftTo(this.getGame().getDisplayWidth() - 1);
		} else if (this.atRightBorder()) {
			this.setX(1 - this.getAppearance().getWidth());
		}
	}

	// ---
	// Rotation methods
	// ---

	private void rotate(double rotation) {
		this.getAccelerationVector().rotate(rotation);
		Sprite sprite = getDefaultAppearance();
		this.setAppearance(sprite.rotate(this.getAccelerationVector().angle()));
	}

	private void rotateLeft(double delta) {
		double rotation = ROTATION_STEP * delta * -1;
		this.rotate(rotation);
	}

	private void rotateRight(double delta) {
		double rotation = ROTATION_STEP * delta;
		this.rotate(rotation);
	}

	// ---
	// Shooting methods
	// ---
	private void shoot() {

		if (this.getShootingDelay() <= 0) {
			Bullet bullet = BulletPoolSingleton.getInstance().obtainBullet();
			bullet.setVector(new Vector2D(this.getAccelerationVector().getX(), this.getAccelerationVector().getY()));
			bullet.setX(this.getX() + this.getAppearance().getWidth()/2 -2);
			bullet.setY(this.getY() + this.getAppearance().getHeight()/2 -2);
			bullet.setSpeed(this.getSpeed());
			this.getScene().addComponent(bullet);
			this.setShootingDelay(SHOOTING_DELAY);
		}
	}

	private void decreaseShootDelay(DeltaState deltaState) {
		if (this.getShootingDelay() > 0) {
			this.shootingDelay -= deltaState.getDelta() * 250;
		}
	}

	private boolean atBottomBorder() {
		return this.getGame().getDisplayHeight() <= this.getY();
	}

	private boolean atTopBorder() {
		return this.obtainAbsoluteY() <= 0;
	}

	private boolean atRightBorder() {
		return this.getGame().getDisplayWidth() <= this.getX();
	}

	private boolean atLeftBorder() {
		return this.obtainAbsoluteX() <= 0;
	}

	public double obtainAbsoluteX() {
		return this.getX() + this.getAppearance().getWidth();
	}

	public double obtainAbsoluteY() {
		return this.getY() + this.getAppearance().getHeight();
	}


	public int getShootingDelay() {
		return this.shootingDelay;
	}

	public void setShootingDelay(int shootDelay) {
		this.shootingDelay = shootDelay;
	}

	public Vector2D getAccelerationVector() {
		return this.accelerationVector;
	}

	public void setAccelerationVector(Vector2D accelerationVector) {
		this.accelerationVector = accelerationVector;
	}

}
