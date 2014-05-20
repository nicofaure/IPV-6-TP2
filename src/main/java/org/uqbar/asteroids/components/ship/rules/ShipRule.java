package org.uqbar.asteroids.components.ship.rules;

import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.DeltaState;

public abstract class ShipRule extends Rule {

	abstract public boolean mustApply(Ship ship, DeltaState deltaState);

	abstract public void apply(Ship ship, DeltaState deltaState);

}
