package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Trainer;
import dev.dankins.javamon.logic.battlesystem.Battlesystem;

public interface BattleMenu extends Menu {

	void setupMenu(Battlesystem system, Trainer player, Trainer enemy);

	void setMessageBoxContents(String message);
}
