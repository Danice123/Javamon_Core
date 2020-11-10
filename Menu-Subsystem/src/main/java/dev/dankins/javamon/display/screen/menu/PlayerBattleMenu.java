package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.battlesystem.BattleAction;

public interface PlayerBattleMenu extends Menu {

	void setupMenu(MonsterInstance pokemon);

	BattleAction getAction();

	Integer getActionChoice();
}
