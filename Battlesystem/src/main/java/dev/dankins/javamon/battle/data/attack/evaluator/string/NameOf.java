package dev.dankins.javamon.battle.data.attack.evaluator.string;

import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class NameOf implements Evaluator<String> {

	private final Evaluator<Object> value;

	@JsonCreator
	public NameOf(@JsonProperty("value") final Evaluator<Object> value) {
		this.value = value;
	}

	@Override
	public String evaluate(final AttackAction attack, final MonsterHandler target) {
		final Object obj = value.evaluate(attack, target);
		try {
			return (String) obj.getClass().getMethod("getName").invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return "";
		}
	}

}
