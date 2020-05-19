package dev.dankins.javamon.battle.data.attack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.data.attack.effect.Effect;
import dev.dankins.javamon.battle.data.attack.require.Requirement;
import dev.dankins.javamon.data.monster.Type;

public class Attack {

	public final String name;
	public final Type type;
	public final Class damageClass;
	public final int uses;
	public final int accuracy;
	public final boolean missable;
	public final int priority;

	public final List<Requirement> requirements;
	public final List<Effect> effects;

	@JsonCreator
	public Attack(@JsonProperty("name") final String name, @JsonProperty("type") final Type type,
			@JsonProperty("damageClass") final Class damageClass,
			@JsonProperty("uses") final int uses, @JsonProperty("accuracy") final int accuracy,
			@JsonProperty("missable") final boolean missable,
			@JsonProperty("priority") final int priority,
			@JsonProperty("requirements") final List<Requirement> requirements,
			@JsonProperty("effects") final List<Effect> effects) {
		this.name = name;
		this.type = type;
		this.damageClass = damageClass;
		this.uses = uses;
		this.accuracy = accuracy;
		this.missable = missable;
		this.priority = priority;

		this.requirements = requirements == null ? Lists.newArrayList() : requirements;
		this.effects = effects == null ? Lists.newArrayList() : effects;
	}

}
