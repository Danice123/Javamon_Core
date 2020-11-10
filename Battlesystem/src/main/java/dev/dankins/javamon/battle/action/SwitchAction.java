package dev.dankins.javamon.battle.action;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class SwitchAction implements Action {

	public final MonsterHandler target;

	private Consumer<Void> switchFunction;

	public SwitchAction(MonsterHandler target, Consumer<Void> switchFunction) {
		this.target = target;
		this.switchFunction = switchFunction;
	}

	@Override
	public List<Event> execute(MonsterHandler opponent) throws BattleStateChange {
		switchFunction.accept(null);
		return Lists.newArrayList();
	}

}
