package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

@JsonTypeInfo(use = Id.MINIMAL_CLASS, include = As.PROPERTY, property = "evaluator")
public interface Evaluator<T> {

	T evaluate(final AttackAction attack, final MonsterHandler target);
}
