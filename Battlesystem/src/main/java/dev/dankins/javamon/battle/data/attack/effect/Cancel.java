package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.CancelEvent;
import dev.dankins.javamon.battle.display.event.Event;

public class Cancel implements Effect {

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		return Lists.newArrayList(new CancelEvent());
	}

}
