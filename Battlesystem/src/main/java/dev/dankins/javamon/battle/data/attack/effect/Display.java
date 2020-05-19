package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;

public class Display implements Effect {

	private final String text;

	@JsonCreator
	public Display(@JsonProperty("text") final String text) {
		this.text = text;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		String display = text;
		if (display.contains("$user")) {
			display = text.replace("$user", attack.user.getMonster().getName());
		}
		if (display.contains("$target")) {
			display = text.replace("$target", target.getMonster().getName());
		}
		return Lists.newArrayList(new TextEvent(EventType.AttackDisplay, display));
	}

}
