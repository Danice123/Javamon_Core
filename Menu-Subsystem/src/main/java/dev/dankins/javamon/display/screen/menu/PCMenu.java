package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Player;

public interface PCMenu extends Menu {

	void setupMenu(Player player);

	PCMenuOptions getMenuChoice();

	public enum PCMenuOptions {
		Pokemon, Item, Exit;
	}
}
