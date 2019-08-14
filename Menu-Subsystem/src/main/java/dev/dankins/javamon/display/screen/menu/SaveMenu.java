package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Player;

public interface SaveMenu extends Menu {

	void setupMenu(Player player);

	boolean shouldSave();

}
