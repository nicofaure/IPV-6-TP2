package org.uqbar.asteroids.scene;


import org.uqbar.asteroids.components.ScreenMessage;
import org.uqbar.asteroids.utils.ResourceUtil;

import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.components.PointsCounter;
import com.uqbar.vainilla.sound.SoundBuilder;

public class GameOverScene extends GameScene {
	
	private static final String GAMEOVER_SOUND = ResourceUtil.getResourceString("GameOverScene.GAMEOVER_SOUND");
	
	public GameOverScene(PointsCounter pointCounter) {
		this.addComponent(pointCounter);
	}


	/**
	 * Ventana de ejecución para la inicialización de los componentes.
	 */
	@Override
	public void initializeComponents() {
		this.addComponent(new ScreenMessage("Game Over :("));
		new SoundBuilder().buildSound(GAMEOVER_SOUND).play(1);
	}
	
}
