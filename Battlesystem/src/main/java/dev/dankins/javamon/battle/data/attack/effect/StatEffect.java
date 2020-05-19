package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackFlag;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;
import dev.dankins.javamon.data.monster.Stat;

public class StatEffect implements Effect {

	private final Target target;
	private final Stat stat;
	private final int level;

	@JsonCreator
	public StatEffect(@JsonProperty("target") final Target target,
			@JsonProperty("stat") final Stat stat, @JsonProperty("level") final int level) {
		this.target = target;
		this.stat = stat;
		this.level = level;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		if (this.target == Target.TARGET) {
			if (target.getFlag(AttackFlag.BLOCKED_STAT_CHANGES) && level < 0) {
				return Lists.newArrayList();
			}
			target.modify(stat, level);
			if (level > 0) {
				return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
						target.getMonster().getName() + "'s " + stat.name() + " has been raised!"));
			} else {
				return Lists.newArrayList(
						new TextEvent(EventType.AttackDisplay, target.getMonster().getName() + "'s "
								+ stat.name() + " has been lowered..."));
			}

		} else {
			if (attack.user.getFlag(AttackFlag.BLOCKED_STAT_CHANGES) && level < 0) {
				return Lists.newArrayList();
			}
			attack.user.modify(stat, level);
			if (level > 0) {
				return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
						attack.user.getMonster().getName() + " raised it's " + stat.name()));
			} else {
				return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
						attack.user.getMonster().getName() + "'s lowered it's " + stat.name()));
			}
		}
	}
}
