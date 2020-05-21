package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;

public class Display implements Effect {

	private final String text;
	private final Map<String, Evaluator<String>> variables;

	@JsonCreator
	public Display(@JsonProperty("text") final String text,
			@JsonProperty("variables") final Map<String, Evaluator<String>> variables) {
		this.text = text;
		this.variables = variables;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		String display = text;
		if (display.contains("$user")) {
			display = display.replace("$user", attack.user.getMonster().getName());
		}
		if (display.contains("$target")) {
			display = display.replace("$target", target.getMonster().getName());
		}

		if (variables != null) {
			for (final String key : variables.keySet()) {
				display = display.replace(key, variables.get(key).evaluate(attack, target));
			}
		}

		return Lists.newArrayList(new TextEvent(EventType.AttackDisplay, display));
	}

}
