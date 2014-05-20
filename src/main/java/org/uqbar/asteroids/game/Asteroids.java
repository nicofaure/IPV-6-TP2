package org.uqbar.asteroids.game;

import java.awt.Dimension;

import org.uqbar.asteroids.scene.AsteroidsScene;
import org.uqbar.asteroids.utils.BulletPoolSingleton;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.DesktopGameLauncher;
import com.uqbar.vainilla.Game;

public class Asteroids extends Game {
	
	private static int SIZE_WIDTH = ResourceUtil.getResourceInt("Asteroids.SIZE_WIDTH");
	private static int SIZE_HEIGHT = ResourceUtil.getResourceInt("Asteroids.SIZE_HEIGHT");
	
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
		return new Dimension(SIZE_WIDTH, SIZE_HEIGHT);
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
