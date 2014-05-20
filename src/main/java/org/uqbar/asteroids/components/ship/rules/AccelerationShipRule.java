package org.uqbar.asteroids.components.ship.rules;

import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.DeltaState;
import com.uqbar.vainilla.events.constants.Key;

public class AccelerationShipRule extends ShipRule{

	@Override
	public boolean mustApply(Ship ship, DeltaState deltaState) {		
		return true;
	}

	@Override
	public void apply(Ship ship, DeltaState deltaState) {
		if (deltaState.isKeyBeingHold(Key.UP)) {
			ship.actAcceleration(deltaState);
			// Actualiza la velocidad.
			ship.actSpeed(deltaState);
		} else {
			ship.decreaseAcceleration();
		}
	}

}
