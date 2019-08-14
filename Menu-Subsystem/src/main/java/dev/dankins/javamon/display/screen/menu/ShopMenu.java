package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.data.abstraction.Inventory;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Player;

public interface ShopMenu extends Menu {

	void setupMenu(Player player, Inventory shop);

	ShopMenuOptions getMenuChoice();

	int getMenuIndex();

	int getMenuAmount();

	void updateMenu();

	public enum ShopMenuOptions {
		Buy, Sell, Exit;
	}

}
