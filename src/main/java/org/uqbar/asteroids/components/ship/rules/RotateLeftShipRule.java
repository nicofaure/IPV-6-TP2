package org.uqbar.asteroids.components.ship.rules;

import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.events.constants.Key;

public class RotateLeftShipRule extends ShipRule {

	@Override
	public boolean mustApply(Ship ship, DeltaState deltaState) {
		return deltaState.isKeyBeingHold(Key.LEFT);
	}

	@Override
	public void apply(Ship ship, DeltaState deltaState) {
		ship.rotateLeft(deltaState.getDelta());
	}

}
