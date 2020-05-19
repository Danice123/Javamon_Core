package dev.dankins.javamon.battle.data.monster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.data.attack.AttackInstance;

public class AttackSerialized {

	public final String attack;
	public final int maxUsage;
	public final int currentUsage;

	@JsonCreator
	public AttackSerialized(@JsonProperty("attack") final String attack,
		@JsonProperty("maxUsage") final int maxUsage,
		@JsonProperty("currentUsage") final int currentUsage) {
		this.attack = attack;
		this.maxUsage = maxUsage;
		this.currentUsage = currentUsage;
	}

	public AttackSerialized(final AttackInstance attack) {
		this.attack = attack.attack.name;
		maxUsage = attack.maxUsage;
		currentUsage = attack.currentUsage;
	}

}
