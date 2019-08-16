package dev.dankins.javamon.logic.entity.behavior;

import dev.dankins.javamon.Coord;
import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.logic.Dir;
import dev.dankins.javamon.logic.entity.WalkableHandler;
import dev.dankins.javamon.logic.map.MapHandler;

public class RandomWander implements EntityBehavior {

	private final Coord center;
	private final int radius;
	private MapHandler mapHandler;

	public RandomWander(final Coord center, final int radius) {
		this.center = center;
		this.radius = radius;
	}

	@Override
	public void takeAction(final WalkableHandler handler) {
		if (!handler.isBusy()) {
			final Dir direction = EntityBehavior.getRandomDirection();
			if (!isOutsideOfBounds(handler, direction)) {
				handler.walk(mapHandler, direction);
			}
		}
	}

	private boolean isOutsideOfBounds(final WalkableHandler handler, final Dir direction) {
		final Coord coord = handler.getCoord().inDirection(direction);
		if (coord.x < center.x - radius || coord.x > center.x + radius) {
			return true;
		}
		if (coord.y < center.y - radius || coord.y > center.y + radius) {
			return true;
		}
		return false;
	}

	@Override
	public int getMillisecondsToWait() {
		return RandomNumberGenerator.random.nextInt(1000) + 1500;
	}

	@Override
	public void setMapHandler(final MapHandler map) {
		mapHandler = map;
	}

}
