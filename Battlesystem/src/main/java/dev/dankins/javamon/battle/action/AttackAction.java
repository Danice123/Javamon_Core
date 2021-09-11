package dev.dankins.javamon.battle.action;

import java.util.List;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.data.BattlesystemHook;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.StoreVariables;
import dev.dankins.javamon.battle.data.attack.AttackFlag;
import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.battle.data.attack.effect.CustomStatus;
import dev.dankins.javamon.battle.data.attack.effect.Effect;
import dev.dankins.javamon.battle.data.attack.require.Requirement;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.attack.AttackEvent;

public class AttackAction implements Action, Comparable<AttackAction> {

	public final MonsterHandler user;
	public final AttackInstance attackInstance;

	public AttackAction(final MonsterHandler user, final AttackInstance attack) {
		this.user = user;
		attackInstance = attack;
	}

	public List<Event> execute(final MonsterHandler target) {
		if (user.getMonster().getCurrentHealth() == 0) {
			return Lists.newArrayList();
		}

		final List<Event> events = Lists.newArrayList(new AttackEvent(user.getKey(), attackInstance));
		for (final CustomStatus status : user.getTemporaryStatuses()) {
			events.addAll(status.apply(BattlesystemHook.onAttackBegin));
		}
		for (final CustomStatus status : target.getTemporaryStatuses()) {
			events.addAll(status.apply(BattlesystemHook.onAttackTargeted));
		}
		if (events.stream().anyMatch(event -> event.type == EventType.Cancel)) {
			return events;
		}

		// TODO: Maybe some edge cases here
		user.setLastUsedMove(attackInstance);

		for (final Requirement requirement : attackInstance.attack.requirements) {
			if (!requirement.check(this, target)) {
				events.add(new Event(EventType.AttackFailed));
				return events;
			}
		}

		if (attackInstance.attack.missable && missCalc(target)) {
			events.add(new Event(EventType.AttackMissed));
			return events;
		}

		target.moveHitByThisTurn = attackInstance;
		final int targetHealth = target.getMonster().getCurrentHealth();
		for (final Effect effect : attackInstance.attack.effects) {
			events.addAll(effect.use(this, target));
		}
		final int damageDone = targetHealth - target.getMonster().getCurrentHealth();

		for (final CustomStatus status : user.getTemporaryStatuses()) {
			events.addAll(status.apply(BattlesystemHook.onAttackEnd));
		}
		for (final CustomStatus status : target.getTemporaryStatuses()) {
			target.setCounter(StoreVariables.DamageTaken.value, damageDone);
			events.addAll(status.apply(BattlesystemHook.onAttackTargetedHit));
			target.setCounter(StoreVariables.DamageTaken.value, null);
		}
		return events;
	}

	private boolean missCalc(final MonsterHandler target) {
		if (target.getFlag(AttackFlag.FLYING)) {
			return true;
		}
		final int chance = Math.round(attackInstance.attack.accuracy * user.getHitModifier(target.getEvasionLevel()));
		if (RandomNumberGenerator.random.nextInt(100) <= chance) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(final Object obj) {
		if (AttackAction.class.isInstance(obj)) {
			return compareTo((AttackAction) obj) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(final AttackAction other) {
		if (attackInstance.attack.priority == other.attackInstance.attack.priority) {
			return Integer.compare(user.getSpeed(), other.user.getSpeed());
		}
		return Integer.compare(attackInstance.attack.priority, other.attackInstance.attack.priority);
	}

}
