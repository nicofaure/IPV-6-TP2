package org.uqbar.asteroids.components.ship.rules;

import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.events.constants.Key;

public class ShootingShipRule extends ShipRule {

	@Override
	public boolean mustApply(Ship ship, DeltaState deltaState) {
		return deltaState.isKeyPressed(Key.SPACE);
	}

	@Override
	public void apply(Ship ship, DeltaState deltaState) {
		ship.shoot();
	}

}
