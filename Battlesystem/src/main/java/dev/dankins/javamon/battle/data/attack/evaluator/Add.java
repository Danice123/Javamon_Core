package dev.dankins.javamon.battle.data.attack.evaluator;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Add implements Evaluator<Integer> {

	private final List<Evaluator<Integer>> args;

	@JsonCreator
	public Add(@JsonProperty("args") final List<Evaluator<Integer>> args) {
		this.args = args;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return args.stream().map(arg -> arg.evaluate(attack, target)).reduce(Integer::sum)
				.orElse(0);
	}

}
