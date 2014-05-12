package org.uqbar.asteroids.components;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<AsteroidsScene> {

	// Configuraciones
	// TODO: Levantarlas de un archivo de propiedades
	private static int SHOOTING_DELAY = 100;
	private static int ROTATION_STEP = 5;

	private static double MAX_SPEED = 5;
	private static double MAX_ACCELERATION = 10;
	private static double MIN_ACCELERATION = -5;

	private Vector2D rotationVector;

	private Vector2D accelerationVector;

	private int shootingDelay = 0;

	public Ship() {
		super(getDefaultAppearance(), 10, 10);
		this.setVector(new Vector2D(1, 0));
		this.setRotationVector(new Vector2D(1, 0));
		/**
		 * Velocidad: speed + vector Aceleracion: acceleration +
		 * accelerationVector.asVersor
		 */
		this.setAccelerationVector(this.getRotationVector());
		this.setSpeed(0);
		this.setAccelerationStep(0.02);
	}

	@Override
	public void onSceneActivated() {
		this.alignHorizontalCenterTo(this.getGame().getDisplayWidth() / 2);
		this.alignVerticalCenterTo(this.getGame().getDisplayHeight() / 2);
		super.onSceneActivated();
	}

	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage("images/ship.png").rotate(Math.PI / 2);
	}

	@Override
	public void update(DeltaState deltaState) {

		this.decreaseShootDelay(deltaState);

		//actualizar velocidad
		this.actSpeed(deltaState);
		
		// Chequeo del control del giro de la nave.
		if (deltaState.isKeyBeingHold(Key.RIGHT)) {
			this.rotateRight(deltaState.getDelta());
		} else if (deltaState.isKeyBeingHold(Key.LEFT)) {
			this.rotateLeft(deltaState.getDelta());
		}

		// Chequeo del control del "propulsor" de la nave.
		if (deltaState.isKeyBeingHold(Key.UP)) {
			this.setAcceleration(1);
		} else if (deltaState.isKeyBeingHold(Key.DOWN)) {
			this.decreaseAcceleration();
			this.setAcceleration(MAX_ACCELERATION);
		} else {
			this.decreaseAcceleration();
		}

		if (deltaState.isKeyPressed(Key.SPACE)) {
			this.shoot();
		}

		this.move(deltaState);

		// Chequea y se teletransporta si es necesario.
		// TODO: No esta bueno, deberia estar en otra parte!
		this.doTeleport();
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

	// this.setSpeed( this.getSpeed() + this.getAcceleration() *
	// deltaState.getDelta() );
	// if(this.getSpeed() > 0) {
	//
	// this.move(this.getRotationVector().asVersor().getX() * this.getSpeed(),
	// this.getRotationVector().asVersor().getY() * this.getSpeed());
	// } else {
	// this.setSpeed(0);
	// }
	// }

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

	@Override
	public void increaseAcceleration() {
		if (this.getAcceleration() < MAX_ACCELERATION) {
			super.increaseAcceleration();
		}
	}

	@Override
	public void setSpeed(double speed) {
		if (speed <= MAX_SPEED) {
			super.setSpeed(speed);
		} else {
			super.setSpeed(MAX_SPEED);
		}
	}

	// ---
	// Rotation methods
	// ---

	private void rotate(double rotation) {
		this.getRotationVector().rotate(rotation);
		Sprite sprite = getDefaultAppearance();
		this.setAppearance(sprite.rotate(this.getRotationVector().angle()));
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
			Bullet bullet = new Bullet(this.getRotationVector().getX(), this
					.getRotationVector().getY(), this.getX(), this.getY(),
					this.getSpeed());
			this.getScene().addComponent(bullet);
			this.setShootingDelay(SHOOTING_DELAY);
		}
	}

	private void decreaseShootDelay(DeltaState deltaState) {
		if (this.getShootingDelay() > 0) {
			this.shootingDelay -= deltaState.getDelta();
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

	@Override
	public void decreaseAcceleration() {
		if (this.getAcceleration() > MIN_ACCELERATION) {
			super.decreaseAcceleration();
		}
	}

	public Vector2D getRotationVector() {
		return this.rotationVector;
	}

	public void setRotationVector(Vector2D rotationVector) {
		this.rotationVector = rotationVector;
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
