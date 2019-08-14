package dev.dankins.javamon.data.monster.attack.require;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "requirement")
public abstract class Require {

	public abstract boolean check(MonsterInstanceImpl user, MonsterInstanceImpl target, AttackBase move);
}
