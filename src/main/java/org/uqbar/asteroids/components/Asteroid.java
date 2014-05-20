package org.uqbar.asteroids.components;

import java.util.Random;

import org.uqbar.asteroids.scene.levels.Level1;
import org.uqbar.asteroids.utils.AsteroidsSpriteManager;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Appearance;
import com.uqbar.vainilla.utils.Vector2D;

public class Asteroid extends MovableComponent<Level1>{
	private int life;
	

	public Asteroid(int life){
		this.life = life;
		this.setAppearance(getDefaultAppearance());
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(50, 150));
	}
	
	public Asteroid(double x, double y, int life){
		this.life = life;
		this.setAppearance(getDefaultAppearance());
		this.setVector(this.buildVector());
		this.setSpeed(this.obtainRnd(50, 150));
		this.setX(x);
		this.setY(y);
		
	}

	private Appearance getDefaultAppearance() {
		//return new Rectangle(Color.BLUE, 20, 20);
		return AsteroidsSpriteManager.getSprite(this.life);
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
		if(this.getGame()!=null)
		{
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
	}

	@Override
	public void update(DeltaState deltaState){
		double advance = this.getSpeed() * deltaState.getDelta();
		this.move(advance * this.getVector().getX(), advance * this.getVector().getY());
		this.doTeleport();
	}

	private boolean atBottomBorder() {
		if(this.getGame()!=null){
			return  this.getGame().getDisplayHeight() <= this.getY();
		}
		return false;
	}

	private boolean atTopBorder() {
		return this.obtainAbsoluteY() <= 0;
	}
		
	private boolean atRightBorder() {
		if(this.getGame()!=null){
			return this.getGame().getDisplayWidth() <= this.getX();
		}
		return false;
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
