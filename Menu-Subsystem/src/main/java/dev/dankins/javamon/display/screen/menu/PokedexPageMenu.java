package dev.dankins.javamon.display.screen.menu;

import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.display.screen.Menu;

public interface PokedexPageMenu extends Menu {

	void setupMenu(Monster monster, boolean caught);
}
