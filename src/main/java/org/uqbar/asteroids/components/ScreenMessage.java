package org.uqbar.asteroids.components;

import java.awt.Color;
import java.awt.Font;

import org.uqbar.asteroids.scene.levels.Level1;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.GameComponent;
import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.appearances.Label;
import com.uqbar.vainilla.events.constants.Key;

public class ScreenMessage extends GameComponent<GameScene> {
	
	private String message;
	private final int fontSize = 30;
	
	public ScreenMessage(String message) {
		this.setMessage(message);
		this.determineAppearance();
	}

	@Override
	public void update(DeltaState deltaState) {
		this.alignHorizontalCenterTo(this.getGame().getDisplayWidth()/2);
		this.alignVerticalCenterTo(this.getGame().getDisplayHeight()/2);
		this.determineAppearance();
		
		if(deltaState.isKeyPressed(Key.ENTER))
		{
			this.getGame().setCurrentScene(new Level1());
		}
		
		super.update(deltaState);
	}

	private void determineAppearance() {
		this.setAppearance(new Label(new Font(Font.SANS_SERIF, Font.BOLD, this.fontSize ), Color.BLACK,this.getMessage(), "(ENTER para continuar)"));
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
