package org.uqbar.asteroids.components;

import java.awt.Color;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Rectangle;
import com.uqbar.vainilla.utils.Vector2D;

public class Asteroid extends MovableComponent<GameScene>{

	
	
	public Asteroid(){
		super();
		this.setAppearance(new Rectangle(Color.RED, 5, 5));
		this.setVector(new Vector2D(1, 1));
	}
	

	
	@Override
	public void update(DeltaState deltaState){
		double advance = this.getSpeed() * deltaState.getDelta();
		this.move(advance * this.getVector().getX(), advance * this.getVector().getY());
	}

}
