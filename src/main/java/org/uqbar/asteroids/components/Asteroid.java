package org.uqbar.asteroids.components;

import java.awt.Color;
import java.util.Random;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Rectangle;
import com.uqbar.vainilla.utils.Vector2D;

public class Asteroid extends MovableComponent<AsteroidsScene>{
	private int life;
	

	public Asteroid(int life){
		this.setAppearance(new Rectangle(Color.BLUE, 20, 20));
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(50, 150));
		this.life = life;
	}
	
	public Asteroid(double x, double y, int life){
		this.setAppearance(new Rectangle(Color.BLUE, 20, 20));
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(50, 150));
		this.setX(x);
		this.setY(y);
		this.life = life;
	}
	
	@Override
	public void onSceneActivated() {
		this.positioningOutsideTheSpace();
		super.onSceneActivated();
	}
	
	private void positioningOutsideTheSpace() {
		int width = this.getScene().getWidth();
		int randomX = this.obtainRnd(0, width);
		if(randomX > width/2){
			this.setX(randomX);
			this.setY(0);
		}else {
			this.setX(0);
			this.setY(this.obtainRnd(0, this.getScene().getHeight()));
		}
		
	}

	private Vector2D buildVector() {
		return new Vector2D(this.obtainRnd(-100, 100), this.obtainRnd(-100, 100)).asVersor();
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

	public void hit() {
		this.getScene().addPoint();                   
		if (this.getLife() > 1) {
			this.life--;
			this.divide();
		}else {
			this.destroy();
		}
		this.getScene().removeAsteroid(this);		
	}
	
	private void divide() {
		this.destroy();
		this.getScene().addAsteroid(new Asteroid(this.getX(), this.getY(), this.getLife()));
		this.getScene().addAsteroid(new Asteroid(this.getX(), this.getY(), this.getLife()));
	}

	public int getLife() {
		return this.life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}


}
