package org.uqbar.asteroids.components;

import java.awt.Color;
import java.util.Random;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Rectangle;
import com.uqbar.vainilla.utils.Vector2D;

public class Asteroid extends MovableComponent<AsteroidsScene>{

	public Asteroid(double x, double y){
		Random r = new Random();
		this.setAppearance(new Rectangle(new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256)), this.obtainRnd(10, 50), this.obtainRnd(10, 50)));
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(120, 320));
		this.setX(x);
		this.setY(y);
	}
	
	public Asteroid(){
		super();
		this.setAppearance(new Rectangle(Color.RED, 10, 150));
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(20, 120));
	}
	
	private Vector2D buildVector() {
		return new Vector2D(this.obtainRnd(-100, 100), this.obtainRnd(-100, 100)).asVersor();
	}
	
	public void setRndPosition(int widht, int height){
		this.setX(this.obtainRnd(0, widht));
		this.setY(this.obtainRnd(0, height));
	}
	

	private void doTeleport() {
		if(this.atBottomBorder()){
			this.setY(1-this.getAppearance().getHeight());
		}else if (this.atTopBorder()){
			this.setY(this.getGame().getDisplayHeight()-1);
		}else if (this.atLeftBorder()){
			this.setX(this.getGame().getDisplayWidth()-1);
		}else if (this.atRightBorder()){
			this.setX(1-this.getAppearance().getWidth());
		}
	}

	@Override
	public void update(DeltaState deltaState){
		double advance = this.getSpeed() * deltaState.getDelta();
		this.move(advance * this.getVector().getX(), advance * this.getVector().getY());
		this.doTeleport();
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

	private int obtainRnd(int min, int max){
		Random r = new Random();
		return r.nextInt(max-min) + min;
	}


}
