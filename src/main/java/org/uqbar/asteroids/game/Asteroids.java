package org.uqbar.asteroids.game;

import java.awt.Dimension;

import org.uqbar.asteroids.scene.AsteroidsScene;
import org.uqbar.asteroids.utils.BulletPoolSingleton;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class Asteroids extends Game {

	
	public static void main(String[] args) throws Exception {
		new DesktopGameLauncher(new Asteroids()).launch();
	}
	
	public Asteroids(){
		super();
		this.initializeBulletPool();
	}
	
	
	@Override
	protected void setUpScenes() {
		AsteroidsScene scene = new AsteroidsScene();		
		this.setCurrentScene(scene);
	}

	@Override
	public Dimension getDisplaySize() {
		return new Dimension(1000, 700);
	}

	@Override
	public String getTitle() {
		return ResourceUtil.getResourceString("Asteroids.getTitle");
	}

	@Override
	protected void initializeResources() {
		
	}
	
	private void initializeBulletPool() {
		for(int i=0; i<25; i++){
			BulletPoolSingleton.getInstance().obtainBullet();
		}
	}


}
