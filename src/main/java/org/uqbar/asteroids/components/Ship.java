package org.uqbar.asteroids.components;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;

public class Ship extends MovableComponent<AsteroidsScene>{

	private double radialSpeed = 10;
	
	
	public Ship(){
		super(Sprite.fromImage("images/ship.png").scale(0.5, 0.5),10,10);
	}
	
	
	@Override
	public void update(DeltaState deltaState){
		double advance = this.getSpeed() * deltaState.getDelta();
		this.move(advance * this.getVector().getX(), advance * this.getVector().getY());

	}


	private double getRadialSpeed() {
		return radialSpeed;
	}


	private void setRadialSpeed(double radialSpeed) {
		this.radialSpeed = radialSpeed;
	}
	
}
