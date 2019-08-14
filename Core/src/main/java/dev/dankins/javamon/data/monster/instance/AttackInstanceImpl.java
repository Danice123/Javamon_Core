package dev.dankins.javamon.data.monster.instance;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.data.monster.attack.Attack;
import dev.dankins.javamon.data.monster.attack.AttackBase;

public class AttackInstanceImpl implements Attack {

	public final AttackBase attack;
	public int maxUsage;
	public int currentUsage;

	public AttackInstanceImpl(final AttackBase attack) {
		this.attack = attack;
		maxUsage = attack.uses;
		currentUsage = attack.uses;
	}

	public AttackInstanceImpl(final AssetManager assets, final AttackSerialized attack) {
		this.attack = assets.get(attack.attack, AttackBase.class);
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
