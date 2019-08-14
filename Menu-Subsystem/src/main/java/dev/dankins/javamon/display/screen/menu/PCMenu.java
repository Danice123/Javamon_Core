package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;

public interface PCMenu extends Menu {

	void setupMenu(boolean knowsStorageGuy, String playerName);

	PCMenuOptions getMenuChoice();

	public enum PCMenuOptions {
		Pokemon, Item, Exit;
	}
}
