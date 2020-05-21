package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

@JsonTypeInfo(use = Id.MINIMAL_CLASS, include = As.PROPERTY, property = "effect")
public interface Effect {

	List<Event> use(AttackAction attack, MonsterHandler target);

}
