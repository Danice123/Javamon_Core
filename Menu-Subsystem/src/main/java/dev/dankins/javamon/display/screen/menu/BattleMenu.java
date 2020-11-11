package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.battle.display.BattlesystemListener;
import dev.dankins.javamon.data.monster.instance.Trainer;
import dev.dankins.javamon.display.screen.Menu;

public interface BattleMenu extends Menu, BattlesystemListener {

	void setupMenu(Trainer player, Trainer enemy);

}
