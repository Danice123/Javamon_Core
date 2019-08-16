package dev.dankins.javamon.logic.entity.behavior;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.logic.entity.WalkableHandler;
import dev.dankins.javamon.logic.map.MapHandler;

public class LookAroundBehavior implements EntityBehavior {

	@Override
	public void takeAction(final WalkableHandler handler) {
		if (!handler.isBusy()) {
			handler.setFacing(EntityBehavior.getRandomDirection());
		}
	}

	@Override
	public int getMillisecondsToWait() {
		return RandomNumberGenerator.random.nextInt(1000) + 1000;
	}

	@Override
	public void setMapHandler(final MapHandler map) {
	}

}
