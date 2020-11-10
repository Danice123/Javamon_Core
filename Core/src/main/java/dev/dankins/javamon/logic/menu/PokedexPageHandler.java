package dev.dankins.javamon.logic.menu;

import dev.dankins.javamon.battle.data.monster.Monster;
import dev.dankins.javamon.display.screen.Menu;
import dev.dankins.javamon.display.screen.menu.PokedexPageMenu;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.MenuHandler;

public class PokedexPageHandler extends MenuHandler<PokedexPageMenu> {

	static public final Class<? extends Menu> MENU_TYPE = PokedexPageMenu.class;
	static public Class<? extends PokedexPageMenu> Menu_Class;

	public PokedexPageHandler(final Game game, final Monster monster, final boolean isCaught) {
		super(game, Menu_Class);
		menu.setupMenu(monster, isCaught);
		initScreen();
	}

	@Override
	protected boolean handleResponse() {
		return false;
	}

}
