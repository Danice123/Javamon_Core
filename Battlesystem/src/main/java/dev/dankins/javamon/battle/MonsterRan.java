package dev.dankins.javamon.battle;

import dev.dankins.javamon.battle.data.MonsterHandler;

public class MonsterRan extends BattleStateChange {

	public final MonsterHandler runner;

	public MonsterRan(MonsterHandler runner) {
		this.runner = runner;
	}

	private static final long serialVersionUID = -1709761735100916285L;

}
