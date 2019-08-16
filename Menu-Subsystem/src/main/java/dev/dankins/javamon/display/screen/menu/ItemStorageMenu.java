package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;

public interface ItemStorageMenu extends Menu {

	ItemStorageMenuOptions getMenuChoice();

	public enum ItemStorageMenuOptions {
		Store, Take, Toss, Exit;
	}
}
