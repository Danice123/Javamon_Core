package dev.dankins.javamon.data.monster.attack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AttackSerialized {

	public final String attack;
	public final int maxUsage;
	public final int currentUsage;

	@JsonCreator
	public AttackSerialized(@JsonProperty("attack") final String attack, @JsonProperty("maxUsage") final int maxUsage,
			@JsonProperty("currentUsage") final int currentUsage) {
		this.attack = attack;
		this.maxUsage = maxUsage;
		this.currentUsage = currentUsage;
	}

	public AttackSerialized(final Attack attack) {
		this.attack = attack.getName();
		maxUsage = attack.getMaxUsage();
		currentUsage = attack.getCurrentUsage();
	}

}
