package dev.dankins.javamon.battle.display;

import dev.dankins.javamon.battle.display.event.Event;

public interface BattlesystemListener {

	public void sendEvent(Event event);
}
