package org.uqbar.asteroids.components;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.MovableComponent;
import com.uqbar.vainilla.appearances.Sprite;
import com.uqbar.vainilla.events.constants.Key;
import com.uqbar.vainilla.utils.Vector2D;

public class Ship extends MovableComponent<AsteroidsScene>{

	private double radialSpeed = 450;
	private Vector2D rotationVector; 
	
	public Ship(){
		super(Sprite.fromImage("images/rocket2.png").scale(0.5),20,20);
		//super(new Rectangle(Color.WHITE, 10 ,10), 10 ,10);
		this.setVector(new Vector2D(1,0));
		this.setRotationVector(new Vector2D(0, -1));
		this.setX(200);
		this.setY(300);
		this.setSpeed(25);
	}

	@Override
	public void render(Graphics2D graphics){
//		Color c = graphics.getColor();
//		graphics.setColor(Color.green);
//		Stroke stroke = graphics.getStroke();
//		Stroke stroke2 = new BasicStroke(10); 
//	    graphics.setStroke(stroke2);
//
//		graphics.setColor(c);
//		graphics.setStroke(stroke);	
		super.render(graphics);
	}
	
	@Override
	public void update(DeltaState deltaState){
		double energy = this.getSpeed() * deltaState.getDelta() * this.getAcceleration()/2 * Math.pow(deltaState.getDelta(), 2);
		this.setSpeed(this.getSpeed() + this.getAcceleration() * deltaState.getDelta());
		
		//double advance = this.getSpeed() * deltaState.getDelta();
		
		double radialSpeed = this.getRadialSpeed() * deltaState.getDelta();

		if(deltaState.isKeyBeingHold(Key.RIGHT)){
			Sprite sprite = Sprite.fromImage("images/rocket2.png");
			
			this.getRotationVector().rotate(radialSpeed/100);
			Sprite rotatedSprite = sprite.rotate(this.getRotationVector().angle() + Math.PI/2 );
			
			this.setAppearance(rotatedSprite.scale(0.5));
		} else if(deltaState.isKeyBeingHold(Key.LEFT)){
			Sprite sprite = Sprite.fromImage("images/rocket2.png");
			this.getRotationVector().rotate(-1 * radialSpeed/100);
			this.setAppearance(sprite.rotate(this.getRotationVector().angle() + Math.PI/2 ).scale(0.5));
		} 
		
		if(deltaState.isKeyBeingHold(Key.UP)) {
			this.increaseAcceleration();
			this.setVector(this.getRotationVector().asVersor().copy());
		} else if(deltaState.isKeyBeingHold(Key.DOWN)) {
			this.decreaseAcceleration();
		} else {
			this.decreaseAcceleration();
		}
		
		if(deltaState.isKeyPressed(Key.SPACE)){
			this.shoot();
		}
		
		this.move(this.getVector().getX() * energy, this.getVector().getY() * energy);
		
		//this.move(advance * this.getVector().getX(), advance * this.getVector().getY());
		this.doTeleport();
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

	private void shoot() {

		System.out.println("X:" + this.getRotationVector().getX());
		System.out.println("Y:" + this.getRotationVector().getY());
		
		double delta = 0;
		if(this.getRotationVector().getY()>0)
		{
			delta = 0;
		}else{
			delta = -2;
		}
		
		Bullet bullet = new Bullet(this.getRotationVector().getX(),
									this.getRotationVector().getY(), 
									this.getX()+(this.getAppearance().getWidth()/2) -2, 
									this.getY() + (this.getAppearance().getHeight()/2)-2d);
		this.getScene().addComponent(bullet);
	}


	private double getRadialSpeed() {
		return this.radialSpeed;
	}


	private void setRadialSpeed(double radialSpeed) {
		this.radialSpeed = radialSpeed;
	}

	public Vector2D getRotationVector() {
		return rotationVector;
	}

	public void setRotationVector(Vector2D rotationVector) {
		this.rotationVector = rotationVector;
	}
	
}
