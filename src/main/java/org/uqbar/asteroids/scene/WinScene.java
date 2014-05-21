package org.uqbar.asteroids.scene;

import org.uqbar.asteroids.components.ScreenMessage;

import com.uqbar.vainilla.GameScene;
import com.uqbar.vainilla.components.PointsCounter;

public class WinScene extends GameScene {
	
	public WinScene(PointsCounter pointCounter) {
		this.addComponent(pointCounter);
	}


	/**
	 * Ventana de ejecución para la inicialización de los componentes.
	 */
	@Override
	public void initializeComponents() {
		this.addComponent(new ScreenMessage("Ganaste! :)"));
	}
}
