package org.uqbar.asteroids.components;


import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<AsteroidsScene>{

	// Configuraciones
	// TODO: Levantarlas de un archivo de propiedades
	private static int SHOOTING_DELAY = 100;
	private static int RADIAL_STEP = 5; 
	
	private Vector2D rotationVector;
	private int shootingDelay = 0; 
	
	public Ship(){
		super(getDefaultAppearance(),10,10);
		this.setVector(new Vector2D(1,0));
		this.setRotationVector(new Vector2D(1, 0));
		this.setX(200);
		this.setY(300);
		this.setSpeed(25);
	}
	
	private static Sprite getDefaultAppearance() {
		return Sprite.fromImage("images/ship.png").rotate(Math.PI/2);
	}

	@Override
	public void update(DeltaState deltaState){
		
		this.decreaseShootDelay(deltaState);
		
		// Chequeo del control del giro de la nave.
		if(deltaState.isKeyBeingHold(Key.RIGHT)){
			rotateRight(deltaState.getDelta());
		} else if(deltaState.isKeyBeingHold(Key.LEFT)){
			rotateLeft(deltaState.getDelta());
		} 
		
		// Chequeo del control del "propulsor" de la nave.
		if(deltaState.isKeyBeingHold(Key.UP)) {
			this.increaseAcceleration();
			this.setVector(this.getRotationVector().asVersor().copy());
		} else if(deltaState.isKeyBeingHold(Key.DOWN)) {
			this.decreaseAcceleration();
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
	private void move(DeltaState deltaState) {
		double energy = this.getSpeed() * deltaState.getDelta() * this.getAcceleration()/2 * Math.pow(deltaState.getDelta(), 2);
		this.setSpeed(this.getSpeed() + this.getAcceleration() * deltaState.getDelta());
		this.move(this.getVector().getX() * energy, this.getVector().getY() * energy);
	}
	
	private void doTeleport() {
		if (this.atBottomBorder()) {
			this.alignBottomTo(1);
		}else if (this.atTopBorder()) {
			this.alignTopTo(this.getGame().getDisplayHeight()-1);
		}else if (this.atLeftBorder()){
			this.alignLeftTo(this.getGame().getDisplayWidth() - 1);
		}else if (this.atRightBorder()){
			this.setX(1 - this.getAppearance().getWidth());
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
		double rotation = RADIAL_STEP * delta * -1;
		this.rotate(rotation);
	}

	private void rotateRight(double delta) {
		double rotation = RADIAL_STEP * delta;
		this.rotate(rotation);
	}

	// ---
	// Shooting methods
	// ---
	private void shoot() {
		if (this.getShootDelay() <= 0) {
			Bullet bullet = new Bullet(this.getRotationVector().getX(),this.getRotationVector().getY(), this.getX(), this.getY());
			this.getScene().addComponent(bullet);
			this.setShootDelay(SHOOTING_DELAY);
		}
	}
	
	private void decreaseShootDelay(DeltaState deltaState) {
		if( this.getShootDelay() > 0) {
			this.shootingDelay -= deltaState.getDelta();
		}
	}	
	
	private boolean atBottomBorder() {
		return  this.getGame().getDisplayHeight() <= this.getY();
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
		if(this.getAcceleration() > 0) { 
			super.decreaseAcceleration();
		}
	}

	public Vector2D getRotationVector() {
		return rotationVector;
	}

	public void setRotationVector(Vector2D rotationVector) {
		this.rotationVector = rotationVector;
	}

	public int getShootDelay() {
		return shootingDelay;
	}

	public void setShootDelay(int shootDelay) {
		this.shootingDelay = shootDelay;
	}
	
}
