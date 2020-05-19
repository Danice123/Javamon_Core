package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackFlag;
import dev.dankins.javamon.battle.display.event.Event;

public class SetFlag implements Effect {

	private final Target target;
	private final AttackFlag flag;
	private final boolean value;

	@JsonCreator
	public SetFlag(@JsonProperty("target") final Target target,
			@JsonProperty("flag") final AttackFlag flag,
			@JsonProperty("value") final boolean value) {
		this.target = target;
		this.flag = flag;
		this.value = value;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}
		p.setFlag(flag, value);
		return Lists.newArrayList();
	}
}
