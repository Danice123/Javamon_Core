package dev.dankins.javamon.logic.entity.behavior;

import dev.dankins.javamon.logic.Dir;
import dev.dankins.javamon.logic.entity.WalkableHandler;
import dev.dankins.javamon.logic.map.MapHandler;

public class FaceDirection implements EntityBehavior {

	private final Dir direction;

	public FaceDirection(final Dir direction) {
		this.direction = direction;
	}

	@Override
	public void takeAction(final WalkableHandler handler) {
		if (!handler.isBusy() && handler.getFacing() != direction) {
			handler.setFacing(direction);
		}
	}

	@Override
	public int getMillisecondsToWait() {
		return 100;
	}

	@Override
	public void setMapHandler(final MapHandler map) {
	}

}
