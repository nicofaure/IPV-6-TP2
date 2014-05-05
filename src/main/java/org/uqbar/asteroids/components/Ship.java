package org.uqbar.asteroids.components;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<AsteroidsScene>{

	private double radialSpeed = 150;
	
	
	public Ship(){
		super(Sprite.fromImage("images/ship.png"),10,10);
		this.setVector(this.buildVector());
		this.getVector().setX(1);
		this.getVector().setY(0);
		this.setX(200);
		this.setY(300);
		this.setSpeed(25);
	}
	
	
	private Vector2D buildVector() {
		return new Vector2D(1,0);
	}


	@Override
	public void update(DeltaState deltaState){
		double advance = this.getSpeed() * deltaState.getDelta();
		double radialSpeed = this.getRadialSpeed() * deltaState.getDelta();
		
		if(deltaState.isKeyBeingHold(Key.RIGHT)){
			Sprite sprite = Sprite.fromImage("images/ship.png");
			System.out.println("Antes:" + this.getVector().angle());
			this.getVector().rotate(radialSpeed/100);
			System.out.println("Despues:" + this.getVector().angle());
			this.setAppearance(sprite.rotate(this.getVector().angle()));
		}else if(deltaState.isKeyBeingHold(Key.LEFT)){
			Sprite sprite = Sprite.fromImage("images/ship.png");
			this.getVector().rotate(-1 * radialSpeed/100);
			this.setAppearance(sprite.rotate(this.getVector().angle()));
		}else if(deltaState.isKeyReleased(Key.SPACE)){
			this.shoot();
		}
		
		//this.move(advance * this.getVector().getX(), advance * this.getVector().getY());

	}

	private void shoot() {
		Bullet bullet = new Bullet(this.getVector().getX(),this.getVector().getY(), this.getX(), this.getY());
		this.getScene().addComponent(bullet);
	}


	private double getRadialSpeed() {
		return this.radialSpeed;
	}


	private void setRadialSpeed(double radialSpeed) {
		this.radialSpeed = radialSpeed;
	}
	
}
