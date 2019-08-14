package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.data.CollectionLibrary;
import dev.dankins.javamon.data.monster.MonsterList;
import dev.dankins.javamon.display.screen.Menu;

public interface PokedexMenu extends Menu {

	void setupMenu(MonsterList pokemonDB, CollectionLibrary pokeData);

	PokedexMenuAction getMenuAction();

	int getPokemonChoice();

	public enum PokedexMenuAction {
		View, Cry, Area, Exit;
	}

}
