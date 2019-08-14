package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.display.screen.Menu;

public interface StartMenu extends Menu {

	StartMenuOptions getMenuChoice();

	void setupMenu(boolean hasPokemon, boolean hasPokedex);

	public enum StartMenuOptions {
		Pokedex, Pokemon, Bag, Trainer, Save, Options, Exit;
	}
}
