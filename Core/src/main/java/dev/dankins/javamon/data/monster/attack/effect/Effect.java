package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "effect")
public abstract class Effect {

	public abstract void use(MonsterInstanceImpl user, MonsterInstanceImpl target, AttackBase move);

}
