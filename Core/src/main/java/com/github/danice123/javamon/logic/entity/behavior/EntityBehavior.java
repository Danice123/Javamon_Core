package com.github.danice123.javamon.logic.entity.behavior;

import com.github.danice123.javamon.logic.map.MapHandler;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.logic.Dir;
import dev.dankins.javamon.logic.entity.WalkableHandler;

public interface EntityBehavior {

	public void takeAction(WalkableHandler handler);

	public int getMillisecondsToWait();

	public void setMapHandler(MapHandler map);

	static Dir getRandomDirection() {
		int dir = RandomNumberGenerator.random.nextInt(4);
		switch (dir) {
			case 0:
				return Dir.North;
			case 1:
				return Dir.East;
			case 2:
				return Dir.South;
			case 3:
				return Dir.West;
			default:
				return Dir.North;
		}
	}

}
