package dev.dankins.javamon.battle.data.attack;

import dev.dankins.javamon.battle.data.monster.AttackSerialized;

public class AttackInstance implements dev.dankins.javamon.data.monster.attack.Attack {

	public final Attack attack;
	public int maxUsage;
	public int currentUsage;

	public AttackInstance(final Attack baseAttack) {
		attack = baseAttack;
		maxUsage = baseAttack.uses;
		currentUsage = baseAttack.uses;
	}

	public AttackInstance(final Attack baseAttack, final AttackSerialized attack) {
		this.attack = baseAttack;
		maxUsage = attack.maxUsage;
		currentUsage = attack.currentUsage;
	}

	@Override
	public String getName() {
		return attack.name;
	}

	@Override
	public int getCurrentUsage() {
		return currentUsage;
	}

	@Override
	public int getMaxUsage() {
		return maxUsage;
	}

}
