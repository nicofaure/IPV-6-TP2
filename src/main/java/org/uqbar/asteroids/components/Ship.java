package org.uqbar.asteroids.components;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.asteroids.components.ship.rules.AccelerationShipRule;
import org.uqbar.asteroids.components.ship.rules.OutOfBoundShipRule;
import org.uqbar.asteroids.components.ship.rules.RotateLeftShipRule;
import org.uqbar.asteroids.components.ship.rules.RotateRightShipRule;
import org.uqbar.asteroids.components.ship.rules.ShipRule;
import org.uqbar.asteroids.components.ship.rules.ShootingShipRule;
import org.uqbar.asteroids.scene.levels.Level1;
import org.uqbar.asteroids.utils.BulletPoolSingleton;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.colissions.CollisionDetector;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.sound.Sound;
import com.uqbar.vainilla.sound.SoundBuilder;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<Level1> {

	
	// Configuraciones
	// TODO: Levantarlas de un archivo de propiedades	
	private List<ShipRule> rules;

	private static int SHOOTING_DELAY = ResourceUtil.getResourceInt("Ship.SHOOTING_DELAY");
	private static int ROTATION_STEP = ResourceUtil.getResourceInt("Ship.ROTATION_STEP");
	private static String ROCKET_SPRITE = ResourceUtil.getResourceString("Ship.ROCKET_SPRITE");
	private static String SHOOT_SOUND = ResourceUtil.getResourceString("Ship.SHOOT_SOUND");
	private static String COLLISION_SOUND = ResourceUtil.getResourceString("Ship.COLLISION_SOUND");
	
	private Vector2D accelerationVector;
	private int shootingDelay = ResourceUtil.getResourceInt("Ship.shootingDelay");
	private Sound shootSound = new SoundBuilder().buildSound(SHOOT_SOUND);
	private Sound collisionSound = new SoundBuilder().buildSound(COLLISION_SOUND);

	public Ship() {
		super(getDefaultAppearance(), 10, 10);
		this.setVector(new Vector2D(1, 0));
		this.setAccelerationVector(new Vector2D(1, 0));
		
	}

	@Override
	public void onSceneActivated() {
		this.initRules();
		this.alignHorizontalCenterTo(this.getGame().getDisplayWidth() / 2);
		this.alignVerticalCenterTo(this.getGame().getDisplayHeight() / 2);
		super.onSceneActivated();
	}
	
	private void initRules() {
		this.rules = new ArrayList<ShipRule>();
		
		this.rules.add(new OutOfBoundShipRule());
		this.rules.add(new RotateLeftShipRule());
		this.rules.add(new RotateRightShipRule());
		this.rules.add(new ShootingShipRule());
		this.rules.add(new AccelerationShipRule());
	}

	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage(ROCKET_SPRITE).scale(0.4).rotate(Math.PI / 2);
	}

	@Override
	public void update(DeltaState deltaState) {

		this.decreaseShootDelay(deltaState);
		
		for(ShipRule rule : this.getRules()) {
			if(rule.mustApply(this, deltaState)) {
				rule.apply(this, deltaState);
			}
		}

		this.move(deltaState);
		this.checkAsteroidCollision();
		// Chequea y se teletransporta si es necesario.
		// TODO: No esta bueno, deberia estar en otra parte!
	}

	private void checkAsteroidCollision() {
		for (Asteroid asteroid : this.getScene().getAsteroids()) {
			if (this.impactAsteroid(asteroid)) {
				this.playCollisionSound();
				//this.loseLife();
				System.out.println("Colision");
				break;
			}
		}
	}

	private void loseLife() {
		this.getScene().loseLife();
		this.destroy();
	}

	private boolean impactAsteroid(Asteroid asteroid) {
		return CollisionDetector
				.INSTANCE
				.collidesRectAgainstRect(
						this.getX(), 
						this.getY(), 
						this.getAppearance().getWidth(), 
						this.getAppearance().getHeight(), 
						asteroid.getX(), 
						asteroid.getY(), 
						asteroid.getAppearance().getWidth(), 
						asteroid.getAppearance().getHeight());	
	}

	@Override
	public void decreaseAcceleration() {
		this.setVector(this.getVector().producto(0.999));
	}
	
	public void actAcceleration(DeltaState deltaState) {
		// TODO Auto-generated method stub
		double deltaAcceleration = 0.5;
		double deltaX = 0;
		double deltaY = 0;
		
		if(this.getAccelerationVector().getX()>0) {
			deltaX = deltaAcceleration; 
		} else {
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
	public void actSpeed(DeltaState deltaState) {
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

	// ---
	// Rotation methods
	// ---

	private void rotate(double rotation) {
		this.getAccelerationVector().rotate(rotation);
		Sprite sprite = getDefaultAppearance();
		this.setAppearance(sprite.rotate(this.getAccelerationVector().angle()));
	}

	public void rotateLeft(double delta) {
		double rotation = ROTATION_STEP * delta * -1;
		this.rotate(rotation);
	}

	public void rotateRight(double delta) {
		double rotation = ROTATION_STEP * delta;
		this.rotate(rotation);
	}

	// ---
	// Shooting methods
	// ---
	public void shoot() {

		if (this.getShootingDelay() <= 0) {
			this.playShootSound();
			Bullet bullet = BulletPoolSingleton.getInstance().obtainBullet();
			bullet.setVector(new Vector2D(this.getAccelerationVector().getX(), this.getAccelerationVector().getY()));
			bullet.setX(this.getX() + this.getAppearance().getWidth()/2 -15);
			bullet.setY(this.getY() + this.getAppearance().getHeight()/2 -15);
			bullet.setSpeed(this.getSpeed());
			this.getScene().addComponent(bullet);
			
			this.setShootingDelay(SHOOTING_DELAY);
		}
	}

	protected void playShootSound() {
		this.shootSound.play(1);
	}
	
	protected void playCollisionSound(){
		this.collisionSound.play(1);
	}
	
	private void decreaseShootDelay(DeltaState deltaState) {
		if (this.getShootingDelay() > 0) {
			this.shootingDelay -= deltaState.getDelta() * 250;
		}
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

	public List<ShipRule> getRules() {
		return rules;
	}

	public void setRules(List<ShipRule> rules) {
		this.rules = rules;
	}

}
