package dev.dankins.javamon.battle.action;

import java.util.List;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.MonsterRan;
import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TargetedEvent;

public class RunAction implements Action {

	public final MonsterHandler runner;
	private int escapeAttempts;

	public RunAction(final MonsterHandler runner, int escapeAttempts) {
		this.runner = runner;
		this.escapeAttempts = escapeAttempts;
	}

	public List<Event> execute(final MonsterHandler opponent) throws BattleStateChange {
		if (runner.getMonster().getSpeed() > opponent.getMonster().getSpeed()) {
			throw new MonsterRan(runner);
		}
		float chance = runner.getMonster().getSpeed() * 128f / opponent.getMonster().getSpeed() + 30f * escapeAttempts;
		if (RandomNumberGenerator.random.nextInt(255) < chance % 256) {
			throw new MonsterRan(runner);
		}
		return Lists.newArrayList(new TargetedEvent(EventType.EscapeFailed, runner.getMonster().getName()));
	}

}
