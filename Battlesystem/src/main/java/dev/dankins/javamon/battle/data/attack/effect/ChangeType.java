package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.attack.TypeChangeEvent;
import dev.dankins.javamon.data.monster.MultiType;

public class ChangeType extends Targeted implements Effect {

	private final Evaluator<MultiType> type;

	@JsonCreator
	public ChangeType(@JsonProperty("target") final Target target,
			@JsonProperty("type") final Evaluator<MultiType> type) {
		super(target);
		this.type = type;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final MultiType typeToChangeTo = type.evaluate(attack, target);
		getTarget(attack, target).setType(typeToChangeTo);
		return Lists.newArrayList(new TypeChangeEvent(getTarget(attack, target), typeToChangeTo));
	}

}
