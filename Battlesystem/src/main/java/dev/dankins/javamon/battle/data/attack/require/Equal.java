package dev.dankins.javamon.battle.data.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class Equal implements Requirement {

	private final Evaluator<?> A;
	private final Evaluator<?> B;

	@JsonCreator
	public Equal(@JsonProperty("A") final Evaluator<?> A, @JsonProperty("B") final Evaluator<?> B) {
		this.A = A;
		this.B = B;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		return A.evaluate(attack, target).equals(B.evaluate(attack, target));
	}
}
