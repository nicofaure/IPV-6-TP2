package org.uqbar.asteroids.components.ship.rules;

import org.uqbar.asteroids.components.Ship;

import com.uqbar.vainilla.DeltaState;

public class OutOfBoundShipRule extends ShipRule {

	@Override
	public boolean mustApply(Ship ship, DeltaState deltaState) {
		return true;
	}

	@Override
	public void apply(Ship ship, DeltaState deltaState) {
		
		if (this.atBottomBorder(ship)) {
			ship.setY(1-ship.getAppearance().getHeight());
		} else if (this.atTopBorder(ship)) {
			ship.alignTopTo(ship.getGame().getDisplayHeight() - 1);
		} else if (this.atLeftBorder(ship)) {
			ship.alignLeftTo(ship.getGame().getDisplayWidth() - 1);
		} else if (this.atRightBorder(ship)) {
			ship.setX(1 - ship.getAppearance().getWidth());
		}
	}
	
	public boolean atBottomBorder(Ship ship) {
		return ship.getGame().getDisplayHeight() <= ship.getY();
	}

	public boolean atTopBorder(Ship ship) {
		return ship.obtainAbsoluteY() <= 0;
	}

	private boolean atRightBorder(Ship ship) {
		return ship.getGame().getDisplayWidth() <= ship.getX();
	}

	private boolean atLeftBorder(Ship ship) {
		return ship.obtainAbsoluteX() <= 0;
	}


}
