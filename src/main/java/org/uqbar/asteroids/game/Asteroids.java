package org.uqbar.asteroids.game;

import java.awt.Dimension;

import org.uqbar.asteroids.scene.AsteroidsScene;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class Asteroids extends Game {

	public static void main(String[] args) throws Exception {
		new DesktopGameLauncher(new Asteroids()).launch();
	}
	
	@Override
	protected void setUpScenes() {
		AsteroidsScene scene = new AsteroidsScene();		
		this.setCurrentScene(scene);
	}

	@Override
	public Dimension getDisplaySize() {
		return new Dimension(1200, 700);
	}

	@Override
	public String getTitle() {
		return "Arkanoid";
	}

	@Override
	protected void initializeResources() {
	}

}
