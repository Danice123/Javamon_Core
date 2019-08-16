package dev.dankins.javamon.logic.entity.behavior;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.logic.Dir;
import dev.dankins.javamon.logic.entity.WalkableHandler;
import dev.dankins.javamon.logic.map.MapHandler;

public class WalkAxis implements EntityBehavior {

	private final boolean isVertical;
	private MapHandler map;

	public WalkAxis(final boolean isVertical) {
		this.isVertical = isVertical;
	}

	@Override
	public void takeAction(final WalkableHandler handler) {
		if (!handler.isBusy()) {
			final boolean dir = RandomNumberGenerator.random.nextBoolean();
			if (isVertical) {
				if (dir) {
					handler.walk(map, Dir.North);
				} else {
					handler.walk(map, Dir.South);
				}
			} else {
				if (dir) {
					handler.walk(map, Dir.East);
				} else {
					handler.walk(map, Dir.West);
				}
			}
		}
	}

	@Override
	public int getMillisecondsToWait() {
		return RandomNumberGenerator.random.nextInt(1000) + 1500;
	}

	@Override
	public void setMapHandler(final MapHandler map) {
		this.map = map;
	}

}
