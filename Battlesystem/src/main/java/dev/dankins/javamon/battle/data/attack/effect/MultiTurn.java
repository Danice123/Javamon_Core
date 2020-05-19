package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;

public class MultiTurn implements Effect {

	private final List<Effect> first;
	private final List<Effect> middle;
	private final List<Effect> last;
	private final Evaluator<Integer> length;

	@JsonCreator
	public MultiTurn(@JsonProperty("first") final List<Effect> first,
			@JsonProperty("middle") final List<Effect> middle,
			@JsonProperty("last") final List<Effect> last,
			@JsonProperty("length") final Evaluator<Integer> length) {
		this.first = first == null ? middle : first;
		this.middle = middle;
		this.last = last == null ? middle : last;
		this.length = length;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		if (attack.user.isUsingMultiTurnMove()) {
			attack.user.decrementMultiTurnCount();

			if (!attack.user.isUsingMultiTurnMove()) {
				return callEffects(last, attack, target);
			}
			return callEffects(middle, attack, target);
		}
		attack.user.setMultiTurnCount(length.evaluate(attack, target));
		attack.user.decrementMultiTurnCount();
		return callEffects(first, attack, target);
	}

	private List<Event> callEffects(final List<Effect> effects, final AttackAction attack,
			final MonsterHandler target) {
		final List<Event> results = Lists.newArrayList();
		for (final Effect effect : effects) {
			results.addAll(effect.use(attack, target));
		}
		return results;
	}
}
