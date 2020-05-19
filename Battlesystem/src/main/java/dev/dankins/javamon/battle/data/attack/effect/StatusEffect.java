package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;
import dev.dankins.javamon.data.monster.Status;

public class StatusEffect implements Effect {

	private final Target target;
	private final Status status;

	@JsonCreator
	public StatusEffect(@JsonProperty("target") final Target target,
			@JsonProperty("status") final Status status) {
		this.target = target;
		this.status = status;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}

		if (status != Status.FAINTED && p.getMonster().getStatus() != Status.NONE) {
			return Lists.newArrayList();
		}
		p.getMonster().setStatus(status);
		switch (status) {
		case BURN:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been burnt!"));
		case FREEZE:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been frozen solid!"));
		case PARALYSIS:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been paralyzed!"));
		case POISON:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been poisoned!"));
		case POISON_TOXIC:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been badly poisoned!"));
		case SLEEP:
			return Lists.newArrayList(new TextEvent(EventType.AttackDisplay,
					p.getMonster().getName() + " has been put to sleep!"));
		case FAINTED:
			p.getMonster().changeHealth(-p.getMonster().getCurrentHealth());
			return Lists
					.newArrayList(new TextEvent(EventType.AttackDisplay, "The attack was a OHKO!"));
		default:
			return Lists.newArrayList();
		}
	}
}
