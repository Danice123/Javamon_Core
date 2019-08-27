package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class SetCounter extends Effect {

	private final Target target;
	private final String counter;
	private String text;

	private final type type;
	private final int n;

	private final int max;
	private final int min;

	@JsonCreator
	public SetCounter(@JsonProperty("target") final Target target,
			@JsonProperty("counter") final String counter, @JsonProperty("text") final String text,
			@JsonProperty("type") final type type, @JsonProperty("n") final int n,
			@JsonProperty("max") final int max, @JsonProperty("min") final int min) {
		this.target = target;
		this.counter = counter;
		this.text = text;
		this.type = type;
		this.n = n;
		this.max = max;
		this.min = min;
	}

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		MonsterInstanceImpl p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = user;
		}
		switch (type) {
		case MODIFY:
			p.battleStatus.setCounter(counter, p.battleStatus.getCounter(counter) + n);
			break;
		case RANDOM:
			final int r = RandomNumberGenerator.random.nextInt(max - min) + min;
			p.battleStatus.setCounter(counter, r);
			break;
		case SET:
			p.battleStatus.setCounter(counter, n);
			break;
		}
		if (text != null) {
			text = text.replace("$target", p.getName());
			text = text.replace("$lastMove", p.attacks.get(p.battleStatus.lastMove).attack.name);
			effectHandler.print(text);
		}
	}

	private enum type {
		SET, MODIFY, RANDOM;
	}
}
