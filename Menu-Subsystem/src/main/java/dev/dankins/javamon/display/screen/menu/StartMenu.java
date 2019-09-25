package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.logic.abstraction.Player;

public interface StartMenu extends Menu {

	StartMenuOptions getMenuChoice();

	void setupMenu(Player player);

	public enum StartMenuOptions {
		Pokedex, Pokemon, Bag, Trainer, Save, Options, Exit;
	}
}
