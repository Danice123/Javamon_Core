package dev.dankins.javamon.battle.action;

import java.util.List;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public interface Action {

	public List<Event> execute(final MonsterHandler opponent) throws BattleStateChange;
}
