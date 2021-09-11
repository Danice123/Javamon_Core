package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class ExpEvent extends Event {

	public ExpEvent(final MonsterInstance monster, int exp) {
		super(EventType.ExpGain);
		this.parameters.put("Monster", monster);
		this.parameters.put("EXP", exp);
	}

}
